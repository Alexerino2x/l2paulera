package com.l2paulera.bg.mode;

import java.util.List;

import com.l2paulera.bg.Match;
import com.l2paulera.bg.Participant;

/**
 * Deathmatch: cada um por si, renasce em ponto aleatorio. Vence quem chegar ao limite de abates
 * ou tiver mais abates no fim do tempo.
 */
public final class DeathmatchMode extends AbstractMode
{
	public DeathmatchMode()
	{
		super("dm", "Deathmatch", "148600,45800,-3410;150400,45800,-3410;149500,45500,-3410;149500,46300,-3410", 20);
	}
	
	@Override
	public int getTeamCount()
	{
		return 0;
	}
	
	@Override
	public boolean isRespawnEnabled()
	{
		return true;
	}
	
	@Override
	public void onKill(Match match, Participant killer, Participant victim)
	{
		if (killer == null || killer == victim)
			return;
		killer.addKill();
		killer.addScore(1);
	}
	
	@Override
	public boolean isFinished(Match match)
	{
		for (Participant p : match.getActive())
			if (p.getScore() >= getScoreLimit())
				return true;
		return false;
	}
	
	@Override
	public List<Participant> getWinners(Match match)
	{
		return soloWinner(match);
	}
	
	@Override
	public String getScoreLine(Match match)
	{
		Participant best = null;
		for (Participant p : match.getActive())
			if (best == null || p.getScore() > best.getScore())
				best = p;
		return best == null ? "" : "Lider: " + best.getName() + " (" + best.getScore() + ")";
	}
}
