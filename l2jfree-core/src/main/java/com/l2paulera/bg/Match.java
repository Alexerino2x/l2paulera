package com.l2paulera.bg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.gameserver.ThreadPoolManager;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.instancemanager.InstanceManager;
import com.l2jfree.gameserver.model.Location;
import com.l2jfree.gameserver.model.entity.Instance;
import com.l2jfree.gameserver.network.SystemChatChannelId;
import com.l2jfree.gameserver.network.packets.server.CreatureSay;
import com.l2jfree.gameserver.network.packets.server.ExShowScreenMessage;
import com.l2paulera.bg.mode.BattlegroundMode;

/**
 * Uma partida em andamento: uma instancia propria, participantes, times, tempo e placar.
 * Todo metodo publico e chamado de threads diferentes (pacotes, timers), por isso o estado
 * e alterado sob {@code synchronized}.
 */
public final class Match
{
	private static final Log _log = LogFactory.getLog(Match.class);
	private static final AtomicInteger NEXT_ID = new AtomicInteger();
	
	/** cores dos times, em BGR (formato do cliente) */
	private static final int[] TEAM_COLORS = { 0xFF7F3F, 0x3F3FFF, 0x3FFF3F, 0x3FFFFF };
	private static final String[] TEAM_NAMES = { "Azul", "Vermelho", "Verde", "Amarelo" };
	
	public enum State
	{
		PREPARING,
		RUNNING,
		ENDED
	}
	
	private final int _id = NEXT_ID.incrementAndGet();
	private final BattlegroundMode _mode;
	private final int _instanceId;
	private final Map<Integer, Participant> _participants = new ConcurrentHashMap<>();
	private final int[] _teamScores;
	private final int _durationSeconds;
	private volatile State _state = State.PREPARING;
	private volatile long _combatStart;
	private ScheduledFuture<?> _tickTask;
	
	Match(BattlegroundMode mode, List<L2Player> players)
	{
		_mode = mode;
		_durationSeconds = BgConfig.getDurationSeconds(mode.getId(), 10);
		_teamScores = new int[Math.max(mode.getTeamCount(), 1)];
		
		_instanceId = InstanceManager.getInstance().createDynamicInstance("Battleground.xml");
		final Instance inst = InstanceManager.getInstance().getInstance(_instanceId);
		inst.setName("Battleground " + mode.getDisplayName() + " #" + _id);
		inst.setPvPInstance(true);
		inst.setAllowSummon(false);
		
		// times equilibrados: ordena por nivel e distribui em "serpente" (0,1,1,0,0,1...)
		final List<L2Player> sorted = new ArrayList<>(players);
		sorted.sort(Comparator.comparingInt(L2Player::getLevel).reversed());
		final int teams = mode.getTeamCount();
		for (int i = 0; i < sorted.size(); i++)
		{
			int team = -1;
			if (teams > 0)
			{
				final int round = i / teams;
				final int pos = i % teams;
				team = (round % 2 == 0) ? pos : teams - 1 - pos;
			}
			final L2Player p = sorted.get(i);
			_participants.put(p.getObjectId(), new Participant(p, team));
		}
	}
	
	// ---- getters ---------------------------------------------------------------------------
	
	public int getId()
	{
		return _id;
	}
	
	public BattlegroundMode getMode()
	{
		return _mode;
	}
	
	public State getState()
	{
		return _state;
	}
	
	public int getInstanceId()
	{
		return _instanceId;
	}
	
	public Participant getParticipant(int objectId)
	{
		return _participants.get(objectId);
	}
	
	public Collection<Participant> getParticipants()
	{
		return _participants.values();
	}
	
	/** participantes que ainda estao na partida */
	public List<Participant> getActive()
	{
		final List<Participant> list = new ArrayList<>();
		for (Participant p : _participants.values())
			if (!p.hasLeft())
				list.add(p);
		return list;
	}
	
	public List<Participant> getTeam(int team)
	{
		final List<Participant> list = new ArrayList<>();
		for (Participant p : _participants.values())
			if (p.getTeam() == team && !p.hasLeft())
				list.add(p);
		return list;
	}
	
	public int getTeamScore(int team)
	{
		return _teamScores[team];
	}
	
	public synchronized void addTeamScore(int team, int value)
	{
		_teamScores[team] += value;
	}
	
	public static String getTeamName(int team)
	{
		return team >= 0 && team < TEAM_NAMES.length ? TEAM_NAMES[team] : "Time " + (team + 1);
	}
	
	public int getNameColor(Participant p)
	{
		if (p.getTeam() < 0)
			return 0x00A5FF; // laranja: cada um por si
		return TEAM_COLORS[p.getTeam() % TEAM_COLORS.length];
	}
	
	public int getRemainingSeconds()
	{
		if (_state != State.RUNNING)
			return _durationSeconds;
		final long elapsed = (System.currentTimeMillis() - _combatStart) / 1000;
		return (int)Math.max(0, _durationSeconds - elapsed);
	}
	
	public boolean isCombatOpen()
	{
		return _state == State.RUNNING;
	}
	
	// ---- ciclo de vida ----------------------------------------------------------------------
	
	void start()
	{
		for (Participant p : _participants.values())
		{
			final L2Player player = p.getPlayer();
			if (player == null)
			{
				p.setLeft();
				continue;
			}
			preparePlayer(player);
			player.setInstanceId(_instanceId);
			player.teleToLocation(_mode.getSpawn(this, p), true);
			player.broadcastUserInfo();
			
			if (p.getTeam() >= 0)
				player.sendMessage("Battleground: voce esta no time " + getTeamName(p.getTeam()) + ".");
		}
		
		_mode.onStart(this);
		broadcastScreen(_mode.getDisplayName() + ": a luta comeca em " + BgConfig.PREPARE_SECONDS + "s", 5000);
		
		ThreadPoolManager.getInstance().scheduleGeneral(this::openCombat, BgConfig.PREPARE_SECONDS * 1000L);
	}
	
	private void openCombat()
	{
		synchronized (this)
		{
			if (_state != State.PREPARING)
				return;
			_state = State.RUNNING;
			_combatStart = System.currentTimeMillis();
		}
		for (Participant p : getActive())
		{
			final L2Player player = p.getPlayer();
			if (player != null)
				player.broadcastUserInfo(); // atualiza flag de zona (paz -> pvp)
		}
		broadcastScreen("Valendo! " + _mode.getDisplayName() + " - " + (_durationSeconds / 60) + " minutos", 4000);
		_tickTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(this::tick, 1000, 1000);
	}
	
	private void tick()
	{
		try
		{
			if (_state != State.RUNNING)
				return;
			
			_mode.onTick(this);
			
			final int remaining = getRemainingSeconds();
			if (remaining == 60 || remaining == 30 || remaining == 10)
				broadcastMessage(remaining + " segundos restantes. " + _mode.getScoreLine(this));
			
			if (remaining <= 0 || _mode.isFinished(this) || !hasEnoughPlayers())
				finish();
		}
		catch (Exception e)
		{
			_log.error("Battleground #" + _id + ": erro no tick", e);
		}
	}
	
	private boolean hasEnoughPlayers()
	{
		if (_mode.getTeamCount() <= 0)
			return getActive().size() >= 2;
		
		int teamsWithPlayers = 0;
		for (int t = 0; t < _mode.getTeamCount(); t++)
			if (!getTeam(t).isEmpty())
				teamsWithPlayers++;
		return teamsWithPlayers >= 2;
	}
	
	/** encerra a partida: calcula vencedores, da os pontos e devolve todos depois de alguns segundos */
	public void finish()
	{
		synchronized (this)
		{
			if (_state == State.ENDED)
				return;
			_state = State.ENDED;
		}
		if (_tickTask != null)
			_tickTask.cancel(false);
		
		final List<Participant> winners = _mode.getWinners(this);
		final String result;
		if (winners.isEmpty())
			result = "Empate!";
		else if (_mode.getTeamCount() > 0)
			result = "Vitoria do time " + getTeamName(winners.get(0).getTeam()) + "!";
		else
			result = "Vencedor: " + winners.get(0).getName() + "!";
		
		broadcastScreen(_mode.getDisplayName() + " - " + result, 6000);
		broadcastMessage("Fim! " + result + " " + _mode.getScoreLine(this));
		
		try
		{
			RankService.getInstance().recordMatch(this, winners);
		}
		catch (Exception e)
		{
			_log.error("Battleground #" + _id + ": erro ao gravar o rank", e);
		}
		
		ThreadPoolManager.getInstance().scheduleGeneral(this::cleanup, BgConfig.END_RETURN_SECONDS * 1000L);
	}
	
	private void cleanup()
	{
		for (Participant p : _participants.values())
		{
			if (p.hasLeft())
				continue;
			final L2Player player = p.getPlayer();
			if (player != null)
				returnPlayer(player, p);
		}
		InstanceManager.getInstance().destroyInstance(_instanceId);
		BattlegroundManager.getInstance().onMatchClosed(this);
	}
	
	// ---- eventos vindos da restricao ---------------------------------------------------------
	
	void onKill(L2Player killer, L2Player victim)
	{
		final Participant v = _participants.get(victim.getObjectId());
		if (v == null)
			return;
		final Participant k = killer != null ? _participants.get(killer.getObjectId()) : null;
		
		v.addDeath();
		if (_state == State.RUNNING)
		{
			synchronized (this)
			{
				_mode.onKill(this, k, v);
			}
		}
		
		if (_mode.isRespawnEnabled() && _state != State.ENDED)
		{
			victim.sendMessage("Battleground: voce volta em " + BgConfig.RESPAWN_SECONDS + "s.");
			ThreadPoolManager.getInstance().scheduleGeneral(() -> respawn(v), BgConfig.RESPAWN_SECONDS * 1000L);
		}
		
		if (_state == State.RUNNING && _mode.isFinished(this))
			finish();
	}
	
	private void respawn(Participant p)
	{
		if (_state == State.ENDED || p.hasLeft() || p.isEliminated())
			return;
		final L2Player player = p.getPlayer();
		if (player == null || !player.isDead() || player.getInstanceId() != _instanceId)
			return;
		player.doRevive();
		player.teleToLocation(_mode.getSpawn(this, p), true);
		heal(player);
	}
	
	/** o jogador saiu por comando; volta para onde estava */
	void leave(L2Player player)
	{
		final Participant p = _participants.get(player.getObjectId());
		if (p == null || p.hasLeft())
			return;
		p.setLeft();
		returnPlayer(player, p);
		broadcastMessage(player.getName() + " saiu da partida.");
	}
	
	/** desconectou: so marca; a posicao no banco e corrigida pelo manager */
	Participant onDisconnect(L2Player player)
	{
		final Participant p = _participants.get(player.getObjectId());
		if (p == null || p.hasLeft())
			return null;
		p.setLeft();
		broadcastMessage(player.getName() + " desconectou.");
		return p;
	}
	
	// ---- utilitarios ------------------------------------------------------------------------
	
	private static void preparePlayer(L2Player player)
	{
		player.abortAttack();
		player.abortCast();
		player.setTarget(null);
		if (player.getPet() != null)
			player.getPet().unSummon(player);
		if (player.isInParty())
			player.leaveParty();
		if (player.isDead())
			player.doRevive();
		heal(player);
	}
	
	private static void heal(L2Player player)
	{
		player.getStatus().setCurrentCp(player.getMaxCp());
		player.getStatus().setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
	}
	
	private void returnPlayer(L2Player player, Participant p)
	{
		BattlegroundManager.getInstance().untrack(player.getObjectId(), this);
		if (player.isDead())
			player.doRevive();
		heal(player);
		player.setInstanceId(0);
		final Location loc = p.getReturnLoc() != null ? p.getReturnLoc() : BgConfig.DEFAULT_RETURN;
		player.teleToLocation(loc, true);
		player.broadcastUserInfo();
	}
	
	public void broadcastMessage(String text)
	{
		final CreatureSay cs = new CreatureSay(0, SystemChatChannelId.Chat_Critical_Announce, _mode.getDisplayName(), text);
		for (Participant p : getActive())
		{
			final L2Player player = p.getPlayer();
			if (player != null)
				player.sendPacket(cs);
		}
	}
	
	public void broadcastScreen(String text, int millis)
	{
		final ExShowScreenMessage msg = new ExShowScreenMessage(text, millis);
		for (Participant p : getActive())
		{
			final L2Player player = p.getPlayer();
			if (player != null)
				player.sendPacket(msg);
		}
	}
}
