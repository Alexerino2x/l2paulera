package com.l2paulera.bg.mode;

import java.util.ArrayList;
import java.util.List;

import com.l2paulera.bg.Match;
import com.l2paulera.bg.Participant;

/**
 * Last Man Standing: cada um por si, sem renascer. Quem morre fica de fora ate o fim.
 * Vence o ultimo vivo; se o tempo acabar, vence quem estiver vivo com mais abates.
 */
public final class LastManStandingMode extends AbstractMode
{
	public LastManStandingMode()
	{
		super("lms", "Last Man Standing",
				"148600,45800,-3410;150400,45800,-3410;149500,45500,-3410;149500,46300,-3410;149000,46050,-3410;150000,46050,-3410",
				0);
	}
	
	@Override
	public int getTeamCount()
	{
		return 0;
	}
	
	@Override
	public boolean isRespawnEnabled()
	{
		return false;
	}
	
	@Override
	public void onKill(Match match, Participant killer, Participant victim)
	{
		victim.setEliminated(true);
		if (killer != null && killer != victim)
		{
			killer.addKill();
			killer.addScore(1);
		}
		final int alive = alive(match).size();
		if (alive > 1)
			match.broadcastMessage(victim.getName() + " foi eliminado. Restam " + alive + ".");
	}
	
	@Override
	public void onLeave(Match match, Participant participant)
	{
		participant.setEliminated(true);
	}
	
	private static List<Participant> alive(Match match)
	{
		final List<Participant> list = new ArrayList<>();
		for (Participant p : match.getActive())
			if (!p.isEliminated())
				list.add(p);
		return list;
	}
	
	@Override
	public boolean isFinished(Match match)
	{
		return alive(match).size() <= 1;
	}
	
	@Override
	public List<Participant> getWinners(Match match)
	{
		final List<Participant> alive = alive(match);
		final List<Participant> result = new ArrayList<>();
		if (alive.size() == 1)
		{
			result.add(alive.get(0));
			return result;
		}
		// tempo acabou: entre os vivos, quem tem mais abates
		Participant best = null;
		boolean tie = false;
		for (Participant p : alive)
		{
			if (best == null || p.getKills() > best.getKills())
			{
				best = p;
				tie = false;
			}
			else if (p.getKills() == best.getKills())
				tie = true;
		}
		if (best != null && !tie)
			result.add(best);
		return result;
	}
	
	@Override
	public String getScoreLine(Match match)
	{
		return "Vivos: " + alive(match).size();
	}
}
