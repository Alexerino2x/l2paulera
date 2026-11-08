package com.l2paulera.bg.mode;

import java.util.ArrayList;
import java.util.List;

import com.l2jfree.gameserver.model.Location;
import com.l2jfree.tools.random.Rnd;
import com.l2paulera.bg.BgConfig;
import com.l2paulera.bg.Match;
import com.l2paulera.bg.Participant;

/**
 * Base comum: spawns vindos da config, limite de pontos, vencedor por placar.
 */
public abstract class AbstractMode implements BattlegroundMode
{
	private final String _id;
	private final String _displayName;
	private final String _defaultSpawns;
	private final int _defaultScoreLimit;
	private List<Location> _spawns;
	private int _scoreLimit;
	
	protected AbstractMode(String id, String displayName, String defaultSpawns, int defaultScoreLimit)
	{
		_id = id;
		_displayName = displayName;
		_defaultSpawns = defaultSpawns;
		_defaultScoreLimit = defaultScoreLimit;
		reload();
	}
	
	public void reload()
	{
		_spawns = BgConfig.getLocations(_id, "Spawns", _defaultSpawns);
		_scoreLimit = BgConfig.getInt(_id, "ScoreLimit", _defaultScoreLimit);
	}
	
	@Override
	public final String getId()
	{
		return _id;
	}
	
	@Override
	public final String getDisplayName()
	{
		return _displayName;
	}
	
	protected final int getScoreLimit()
	{
		return _scoreLimit;
	}
	
	protected final List<Location> getSpawns()
	{
		return _spawns;
	}
	
	/** time n nasce no spawn n; sem time, spawn aleatorio */
	@Override
	public Location getSpawn(Match match, Participant participant)
	{
		if (participant.getTeam() >= 0 && participant.getTeam() < _spawns.size())
			return _spawns.get(participant.getTeam());
		return _spawns.get(Rnd.get(_spawns.size()));
	}
	
	/** vencedor(es) em modo de time: o time com mais pontos; empate = vazio */
	protected static List<Participant> teamWinners(Match match)
	{
		final int teams = match.getMode().getTeamCount();
		int best = -1;
		boolean tie = false;
		for (int t = 0; t < teams; t++)
		{
			if (match.getTeam(t).isEmpty())
				continue;
			if (best < 0 || match.getTeamScore(t) > match.getTeamScore(best))
			{
				best = t;
				tie = false;
			}
			else if (match.getTeamScore(t) == match.getTeamScore(best))
				tie = true;
		}
		if (best < 0 || tie)
			return new ArrayList<>();
		return match.getTeam(best);
	}
	
	/** vencedor individual: maior score; empate = vazio */
	protected static List<Participant> soloWinner(Match match)
	{
		Participant best = null;
		boolean tie = false;
		for (Participant p : match.getActive())
		{
			if (best == null || p.getScore() > best.getScore())
			{
				best = p;
				tie = false;
			}
			else if (p.getScore() == best.getScore())
				tie = true;
		}
		final List<Participant> list = new ArrayList<>();
		if (best != null && !tie)
			list.add(best);
		return list;
	}
}
