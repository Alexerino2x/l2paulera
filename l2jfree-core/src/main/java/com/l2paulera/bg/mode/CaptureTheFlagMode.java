package com.l2paulera.bg.mode;

import java.util.List;

import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.Location;
import com.l2paulera.bg.BgConfig;
import com.l2paulera.bg.Match;
import com.l2paulera.bg.Participant;

/**
 * Capture the Flag: cada time tem uma bandeira (NPC) na base. Clique na bandeira inimiga para
 * pega-la e clique na sua propria bandeira (que precisa estar na base) para pontuar.
 * Se o portador morrer ou sair, a bandeira volta para a base.
 */
public final class CaptureTheFlagMode extends AbstractMode
{
	/** NPC usado como bandeira (Headquarters, visual de estandarte) */
	private static final int DEFAULT_FLAG_NPC = 35062;
	private static final String CARRIER_TITLE = "*BANDEIRA*";
	
	private List<Location> _flags;
	private int _flagNpcId;
	
	/** estado por partida, guardado no Match */
	private static final class FlagState
	{
		final int[] npcObjectIds = new int[2];
		/** objectId do portador da bandeira do time i, ou 0 se esta na base */
		final int[] carriers = new int[2];
	}
	
	public CaptureTheFlagMode()
	{
		super("ctf", "Capture the Flag", "148400,45800,-3410;150600,45800,-3410", 3);
	}
	
	@Override
	public void reload()
	{
		super.reload();
		_flags = BgConfig.getLocations(getId(), "Flags", "148250,45800,-3410;150750,45800,-3410");
		_flagNpcId = BgConfig.getInt(getId(), "FlagNpcId", DEFAULT_FLAG_NPC);
	}
	
	@Override
	public int getTeamCount()
	{
		return 2;
	}
	
	@Override
	public boolean isRespawnEnabled()
	{
		return true;
	}
	
	private static FlagState state(Match match)
	{
		return match.getData("ctf");
	}
	
	@Override
	public void onStart(Match match)
	{
		final FlagState st = new FlagState();
		for (int t = 0; t < 2; t++)
		{
			final L2Npc npc = match.spawnNpc(_flagNpcId, _flags.get(t), "Bandeira " + Match.getTeamName(t));
			st.npcObjectIds[t] = npc != null ? npc.getObjectId() : 0;
		}
		match.setData("ctf", st);
	}
	
	@Override
	public boolean onNpcAction(Match match, Participant p, L2Npc npc)
	{
		final FlagState st = state(match);
		if (st == null)
			return false;
		
		final int flagTeam = npc.getObjectId() == st.npcObjectIds[0] ? 0 : npc.getObjectId() == st.npcObjectIds[1] ? 1 : -1;
		if (flagTeam < 0)
			return false;
		
		final L2Player player = p.getPlayer();
		if (player == null || player.isDead())
			return true;
		
		final int myTeam = p.getTeam();
		final int enemyTeam = 1 - myTeam;
		
		if (flagTeam == enemyTeam)
		{
			// pegar a bandeira inimiga
			if (st.carriers[enemyTeam] != 0)
			{
				player.sendMessage("A bandeira ja foi pega.");
				return true;
			}
			st.carriers[enemyTeam] = p.getObjectId();
			player.getAppearance().setVisibleTitle(CARRIER_TITLE);
			player.broadcastTitleInfo();
			match.broadcastScreen(player.getName() + " pegou a bandeira " + Match.getTeamName(enemyTeam) + "!", 4000);
			return true;
		}
		
		// clicou na propria bandeira: se esta carregando a inimiga, pontua
		if (st.carriers[enemyTeam] != p.getObjectId())
		{
			player.sendMessage("Traga a bandeira inimiga ate aqui para pontuar.");
			return true;
		}
		if (st.carriers[myTeam] != 0)
		{
			player.sendMessage("Recupere a sua bandeira antes de pontuar.");
			return true;
		}
		st.carriers[enemyTeam] = 0;
		player.getAppearance().setVisibleTitle(null);
		player.broadcastTitleInfo();
		p.addScore(3);
		match.addTeamScore(myTeam, 1);
		match.broadcastScreen(player.getName() + " capturou a bandeira! " + getScoreLine(match), 5000);
		return true;
	}
	
	private void dropFlag(Match match, Participant p)
	{
		final FlagState st = state(match);
		if (st == null)
			return;
		for (int t = 0; t < 2; t++)
		{
			if (st.carriers[t] == p.getObjectId())
			{
				st.carriers[t] = 0;
				final L2Player player = p.getPlayer();
				if (player != null)
				{
					player.getAppearance().setVisibleTitle(null);
					player.broadcastTitleInfo();
				}
				match.broadcastMessage("A bandeira " + Match.getTeamName(t) + " voltou para a base.");
			}
		}
	}
	
	@Override
	public void onKill(Match match, Participant killer, Participant victim)
	{
		final FlagState st = state(match);
		final boolean wasCarrier = st != null && (st.carriers[0] == victim.getObjectId() || st.carriers[1] == victim.getObjectId());
		dropFlag(match, victim);
		if (killer == null || killer == victim || killer.getTeam() == victim.getTeam())
			return;
		killer.addKill();
		killer.addScore(wasCarrier ? 2 : 1);
	}
	
	@Override
	public void onLeave(Match match, Participant participant)
	{
		dropFlag(match, participant);
	}
	
	@Override
	public boolean isFinished(Match match)
	{
		return match.getTeamScore(0) >= getScoreLimit() || match.getTeamScore(1) >= getScoreLimit();
	}
	
	@Override
	public List<Participant> getWinners(Match match)
	{
		return teamWinners(match);
	}
	
	@Override
	public String getScoreLine(Match match)
	{
		return Match.getTeamName(0) + " " + match.getTeamScore(0) + " x " + match.getTeamScore(1) + " "
				+ Match.getTeamName(1) + " (capturas)";
	}
}
