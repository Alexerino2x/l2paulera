package com.l2paulera.bg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.L2DatabaseFactory;
import com.l2jfree.gameserver.ThreadPoolManager;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.Location;
import com.l2jfree.gameserver.model.restriction.global.GlobalRestrictions;
import com.l2jfree.gameserver.model.world.L2World;
import com.l2jfree.gameserver.model.zone.L2Zone;
import com.l2jfree.gameserver.network.packets.server.NpcHtmlMessage;
import com.l2paulera.bg.mode.AbstractMode;
import com.l2paulera.bg.mode.BattlegroundMode;
import com.l2paulera.bg.mode.CaptureTheFlagMode;
import com.l2paulera.bg.mode.DeathmatchMode;
import com.l2paulera.bg.mode.LastManStandingMode;
import com.l2paulera.bg.mode.TeamVsTeamMode;

/**
 * Motor de battlegrounds no estilo WoW: o jogador entra na fila de um modo de dentro da cidade
 * ({@code .bg} ou NPC), a partida abre quando a fila tem gente suficiente, todos vao para uma
 * instancia propria e voltam para onde estavam no fim.
 */
public final class BattlegroundManager
{
	private static final Log _log = LogFactory.getLog(BattlegroundManager.class);
	
	private static final class SingletonHolder
	{
		private static final BattlegroundManager INSTANCE = new BattlegroundManager();
	}
	
	public static BattlegroundManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	/** modos registrados, na ordem do menu */
	private final Map<String, BattlegroundMode> _modes = new LinkedHashMap<>();
	/** fila por modo (objectId, ordem de chegada) */
	private final Map<String, Set<Integer>> _queues = new ConcurrentHashMap<>();
	private final Map<String, ScheduledFuture<?>> _countdowns = new ConcurrentHashMap<>();
	/** jogador -> partida em que esta */
	private final Map<Integer, Match> _playerMatch = new ConcurrentHashMap<>();
	private final Set<Match> _matches = ConcurrentHashMap.newKeySet();
	
	private BattlegroundManager()
	{
		BgConfig.load();
		register(new TeamVsTeamMode());
		register(new DeathmatchMode());
		register(new LastManStandingMode());
		register(new CaptureTheFlagMode());
		
		if (BgConfig.ENABLED)
		{
			BattlegroundRestriction.getInstance().activate();
			_log.info("Battleground: " + _modes.size() + " modos carregados " + _modes.keySet());
		}
		else
			_log.info("Battleground: desligado (Enabled = False)");
	}
	
	/** registra um modo; modos desligados na config ficam de fora */
	public void register(BattlegroundMode mode)
	{
		if (!BgConfig.isModeEnabled(mode.getId()))
			return;
		_modes.put(mode.getId(), mode);
		_queues.put(mode.getId(), new LinkedHashSet<>());
	}
	
	public Collection<BattlegroundMode> getModes()
	{
		return _modes.values();
	}
	
	public BattlegroundMode getMode(String id)
	{
		return id == null ? null : _modes.get(id.toLowerCase());
	}
	
	public void reloadConfig()
	{
		BgConfig.load();
		for (BattlegroundMode m : _modes.values())
			if (m instanceof AbstractMode am)
				am.reload();
	}
	
	// ---- consulta ----------------------------------------------------------------------------
	
	public Match getMatch(L2Player player)
	{
		return player == null ? null : _playerMatch.get(player.getObjectId());
	}
	
	public boolean isInMatch(L2Player player)
	{
		return getMatch(player) != null;
	}
	
	public String getQueuedMode(L2Player player)
	{
		for (Map.Entry<String, Set<Integer>> e : _queues.entrySet())
		{
			synchronized (e.getValue())
			{
				if (e.getValue().contains(player.getObjectId()))
					return e.getKey();
			}
		}
		return null;
	}
	
	public int getQueueSize(String mode)
	{
		final Set<Integer> q = _queues.get(mode);
		if (q == null)
			return 0;
		synchronized (q)
		{
			return q.size();
		}
	}
	
	// ---- fila --------------------------------------------------------------------------------
	
	/** @return motivo para nao poder entrar, ou null se pode */
	private String checkCanJoin(L2Player player)
	{
		if (!BgConfig.ENABLED)
			return "As battlegrounds estao desligadas.";
		if (isInMatch(player))
			return "Voce ja esta em uma partida.";
		if (player.getLevel() < BgConfig.MIN_LEVEL)
			return "Nivel minimo: " + BgConfig.MIN_LEVEL + ".";
		if (BgConfig.REQUIRE_TOWN && !player.isInsideZone(L2Zone.FLAG_PEACE))
			return "Entre na fila de dentro de uma cidade.";
		if (player.isDead() || player.isInOlympiadMode() || player.isInDuel() || player.isInJail()
				|| player.isCursedWeaponEquipped() || player.isInStoreMode() || player.getKarma() > 0
				|| player.isMounted() || player.isFlyingMounted() || player.isInCombat())
			return "Voce nao pode entrar na fila agora.";
		if (GlobalRestrictions.isRestricted(player, BattlegroundRestriction.class))
			return "Voce ja esta em outro evento.";
		return null;
	}
	
	public void join(L2Player player, String modeId)
	{
		final BattlegroundMode mode = getMode(modeId);
		if (mode == null)
		{
			player.sendMessage("Battleground: modo desconhecido. Use .bg para ver a lista.");
			return;
		}
		final String error = checkCanJoin(player);
		if (error != null)
		{
			player.sendMessage("Battleground: " + error);
			return;
		}
		
		final String current = getQueuedMode(player);
		if (current != null)
			removeFromQueue(player.getObjectId(), current);
		
		final Set<Integer> q = _queues.get(mode.getId());
		final int size;
		synchronized (q)
		{
			q.add(player.getObjectId());
			size = q.size();
		}
		final int min = BgConfig.getMinPlayers(mode.getId(), 4);
		final int max = BgConfig.getMaxPlayers(mode.getId(), 20);
		player.sendMessage("Battleground: voce entrou na fila de " + mode.getDisplayName() + " (" + size + "/" + min
				+ " para comecar). Use .bg sair para sair.");
		
		if (size >= max)
			tryStart(mode);
		else if (size >= min)
			scheduleCountdown(mode);
	}
	
	public void leave(L2Player player)
	{
		final Match match = getMatch(player);
		if (match != null)
		{
			match.leave(player);
			return;
		}
		final String mode = getQueuedMode(player);
		if (mode != null)
		{
			removeFromQueue(player.getObjectId(), mode);
			player.sendMessage("Battleground: voce saiu da fila.");
		}
		else
			player.sendMessage("Battleground: voce nao esta em nenhuma fila.");
	}
	
	private void removeFromQueue(int objectId, String mode)
	{
		final Set<Integer> q = _queues.get(mode);
		if (q != null)
			synchronized (q)
			{
				q.remove(objectId);
			}
	}
	
	private void scheduleCountdown(BattlegroundMode mode)
	{
		_countdowns.computeIfAbsent(mode.getId(), id -> {
			broadcastQueue(mode, "a partida comeca em " + BgConfig.QUEUE_COUNTDOWN_SECONDS + "s. Fique na cidade!");
			return ThreadPoolManager.getInstance().scheduleGeneral(() -> {
				_countdowns.remove(id);
				tryStart(mode);
			}, BgConfig.QUEUE_COUNTDOWN_SECONDS * 1000L);
		});
	}
	
	/** tira da fila quem nao pode mais jogar e abre uma partida se ainda houver gente suficiente */
	private void tryStart(BattlegroundMode mode)
	{
		final int min = BgConfig.getMinPlayers(mode.getId(), 4);
		final int max = BgConfig.getMaxPlayers(mode.getId(), 20);
		final Set<Integer> q = _queues.get(mode.getId());
		final List<L2Player> players = new ArrayList<>();
		
		synchronized (q)
		{
			for (Iterator<Integer> it = q.iterator(); it.hasNext();)
			{
				final L2Player p = L2World.getInstance().getPlayer(it.next());
				if (p == null)
				{
					it.remove();
					continue;
				}
				final String error = checkCanJoin(p);
				if (error != null)
				{
					it.remove();
					p.sendMessage("Battleground: voce saiu da fila. " + error);
					continue;
				}
				if (players.size() < max)
					players.add(p);
			}
			if (players.size() < min)
			{
				broadcastQueue(mode, "faltam jogadores (" + players.size() + "/" + min + "). Continuem na fila.");
				return;
			}
			for (L2Player p : players)
				q.remove(p.getObjectId());
		}
		
		final ScheduledFuture<?> pending = _countdowns.remove(mode.getId());
		if (pending != null)
			pending.cancel(false);
		
		try
		{
			final Match match = new Match(mode, players);
			_matches.add(match);
			for (L2Player p : players)
				_playerMatch.put(p.getObjectId(), match);
			_log.info("Battleground: partida #" + match.getId() + " de " + mode.getId() + " com " + players.size()
					+ " jogadores");
			match.start();
		}
		catch (Exception e)
		{
			_log.error("Battleground: falha ao abrir partida de " + mode.getId(), e);
			for (L2Player p : players)
			{
				_playerMatch.remove(p.getObjectId());
				p.sendMessage("Battleground: erro ao abrir a partida, tente de novo.");
			}
		}
		
		// sobrou gente suficiente para outra partida?
		if (getQueueSize(mode.getId()) >= min)
			scheduleCountdown(mode);
	}
	
	private void broadcastQueue(BattlegroundMode mode, String text)
	{
		final Set<Integer> q = _queues.get(mode.getId());
		final List<Integer> ids;
		synchronized (q)
		{
			ids = new ArrayList<>(q);
		}
		for (int id : ids)
		{
			final L2Player p = L2World.getInstance().getPlayer(id);
			if (p != null)
				p.sendMessage("Battleground " + mode.getDisplayName() + ": " + text);
		}
	}
	
	// ---- chamados pela partida e pela restricao --------------------------------------------
	
	void untrack(int objectId, Match match)
	{
		_playerMatch.remove(objectId, match);
	}
	
	void onMatchClosed(Match match)
	{
		_matches.remove(match);
		_playerMatch.values().removeIf(m -> m == match);
	}
	
	void onDisconnect(L2Player player)
	{
		final String mode = getQueuedMode(player);
		if (mode != null)
			removeFromQueue(player.getObjectId(), mode);
		
		final Match match = _playerMatch.remove(player.getObjectId());
		if (match == null)
			return;
		final Participant p = match.onDisconnect(player);
		if (p == null)
			return;
		// o personagem e salvo com a posicao da arena; depois que salvar, corrige para onde ele estava
		final Location loc = p.getReturnLoc() != null ? p.getReturnLoc() : BgConfig.DEFAULT_RETURN;
		final int objectId = player.getObjectId();
		ThreadPoolManager.getInstance().scheduleGeneral(() -> fixOfflinePosition(objectId, loc), 5000);
	}
	
	private static void fixOfflinePosition(int objectId, Location loc)
	{
		if (L2World.getInstance().getPlayer(objectId) != null)
			return; // ja voltou online
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			try (PreparedStatement ps = con.prepareStatement("UPDATE characters SET x=?, y=?, z=? WHERE charId=?"))
			{
				ps.setInt(1, loc.getX());
				ps.setInt(2, loc.getY());
				ps.setInt(3, loc.getZ());
				ps.setInt(4, objectId);
				ps.executeUpdate();
			}
		}
		catch (Exception e)
		{
			_log.error("Battleground: nao consegui corrigir a posicao de " + objectId, e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	/** quem loga fora de partida mas dentro de uma instancia de battleground e mandado de volta */
	void onLogin(L2Player player)
	{
		if (!isInMatch(player) && player.getInstanceId() != 0)
		{
			for (Match m : _matches)
			{
				if (m.getInstanceId() == player.getInstanceId())
				{
					player.setInstanceId(0);
					player.teleToLocation(BgConfig.DEFAULT_RETURN, true);
					return;
				}
			}
		}
	}
	
	// ---- comandos ----------------------------------------------------------------------------
	
	/**
	 * {@code .bg}, {@code .bg entrar tvt}, {@code .bg sair}, {@code .bg rank [modo]}, {@code .bg placar}
	 */
	public void onCommand(L2Player player, String params)
	{
		final String[] args = params == null ? new String[0] : params.trim().toLowerCase().split("\\s+");
		final String cmd = args.length > 0 ? args[0] : "";
		final String arg = args.length > 1 ? args[1] : null;
		
		switch (cmd)
		{
			case "entrar":
			case "join":
				if (arg == null)
					showMenu(player);
				else
					join(player, arg);
				break;
			case "sair":
			case "leave":
				leave(player);
				break;
			case "rank":
				showRank(player, arg == null ? RankService.OVERALL : arg);
				break;
			case "placar":
			case "score":
				showScore(player);
				break;
			default:
				showMenu(player);
		}
	}
	
	public void showMenu(L2Player player)
	{
		final StringBuilder sb = new StringBuilder(1024);
		sb.append("<html><title>Battlegrounds</title><body><center>");
		sb.append("<font color=LEVEL>Battlegrounds</font><br>");
		final Match match = getMatch(player);
		final String queued = getQueuedMode(player);
		if (match != null)
			sb.append("Voce esta em: ").append(match.getMode().getDisplayName()).append("<br>")
					.append("<a action=\"bypass -h bg_placar\">Placar</a> | <a action=\"bypass -h bg_sair\">Sair da partida</a><br>");
		else if (queued != null)
			sb.append("Na fila de: ").append(getMode(queued).getDisplayName()).append("<br>")
					.append("<a action=\"bypass -h bg_sair\">Sair da fila</a><br>");
		sb.append("<br><table width=270>");
		for (BattlegroundMode m : _modes.values())
		{
			final String id = m.getId();
			sb.append("<tr><td width=120>").append(m.getDisplayName()).append("</td><td width=70>")
					.append(getQueueSize(id)).append("/").append(BgConfig.getMinPlayers(id, 4))
					.append("</td><td width=80><a action=\"bypass -h bg_entrar ").append(id)
					.append("\">Entrar</a></td></tr>");
		}
		sb.append("</table><br>");
		sb.append("<a action=\"bypass -h bg_rank geral\">Rank geral</a>");
		for (BattlegroundMode m : _modes.values())
			sb.append(" | <a action=\"bypass -h bg_rank ").append(m.getId()).append("\">").append(m.getId().toUpperCase())
					.append("</a>");
		sb.append("<br><br>Seus pontos: ").append(RankService.getInstance().getBalance(player.getObjectId(), RankService.OVERALL));
		sb.append("</center></body></html>");
		player.sendPacket(new NpcHtmlMessage(0, sb.toString()));
	}
	
	private void showRank(L2Player player, String modeId)
	{
		final boolean overall = RankService.OVERALL.equals(modeId);
		final BattlegroundMode mode = overall ? null : getMode(modeId);
		if (!overall && mode == null)
		{
			player.sendMessage("Battleground: modo desconhecido.");
			return;
		}
		final StringBuilder sb = new StringBuilder(2048);
		sb.append("<html><title>Rank</title><body><center><font color=LEVEL>Rank ")
				.append(overall ? "geral" : mode.getDisplayName()).append("</font><br><table width=270>");
		sb.append("<tr><td>#</td><td>Nome</td><td>Pts</td><td>V/P</td><td>K/D</td></tr>");
		int pos = 1;
		for (RankService.Entry e : RankService.getInstance().getTop(overall ? RankService.OVERALL : mode.getId(), 15))
		{
			sb.append("<tr><td>").append(pos++).append("</td><td>").append(e.name()).append("</td><td>")
					.append(e.points()).append("</td><td>").append(e.wins()).append("/").append(e.matches())
					.append("</td><td>").append(e.kills()).append("/").append(e.deaths()).append("</td></tr>");
		}
		sb.append("</table><br><a action=\"bypass -h bg_menu\">Voltar</a></center></body></html>");
		player.sendPacket(new NpcHtmlMessage(0, sb.toString()));
	}
	
	private void showScore(L2Player player)
	{
		final Match match = getMatch(player);
		if (match == null)
		{
			player.sendMessage("Battleground: voce nao esta em uma partida.");
			return;
		}
		final StringBuilder sb = new StringBuilder(2048);
		sb.append("<html><title>Placar</title><body><center><font color=LEVEL>")
				.append(match.getMode().getDisplayName()).append("</font><br>").append(match.getMode().getScoreLine(match))
				.append("<br>Tempo: ").append(match.getRemainingSeconds() / 60).append(":")
				.append(String.format("%02d", match.getRemainingSeconds() % 60)).append("<br><table width=270>");
		sb.append("<tr><td>Nome</td><td>Time</td><td>Abates</td><td>Mortes</td></tr>");
		final List<Participant> list = match.getActive();
		list.sort((a, b) -> b.getScore() - a.getScore());
		for (Participant p : list)
			sb.append("<tr><td>").append(p.getName()).append("</td><td>")
					.append(p.getTeam() >= 0 ? Match.getTeamName(p.getTeam()) : "-").append("</td><td>")
					.append(p.getKills()).append("</td><td>").append(p.getDeaths()).append("</td></tr>");
		sb.append("</table></center></body></html>");
		player.sendPacket(new NpcHtmlMessage(0, sb.toString()));
	}
	
	/** links "bypass -h bg_..." das janelas */
	public void onBypass(L2Player player, String command)
	{
		// bg_entrar tvt -> "entrar tvt"
		onCommand(player, command.replace('_', ' '));
	}
}
