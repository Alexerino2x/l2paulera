package com.l2paulera.bg.mode;

import java.util.List;

import com.l2paulera.bg.Match;
import com.l2paulera.bg.Participant;

/**
 * TvT: dois times, cada abate vale 1 ponto para o time. Vence quem chegar ao limite ou tiver
 * mais pontos no fim do tempo.
 */
public final class TeamVsTeamMode extends AbstractMode
{
	public TeamVsTeamMode()
	{
		// Coliseu (Aden): lado oeste e lado leste da arena superior
		super("tvt", "Team vs Team", "148600,45800,-3410;150400,45800,-3410", 30);
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
	
	@Override
	public void onKill(Match match, Participant killer, Participant victim)
	{
		if (killer == null || killer == victim || killer.getTeam() == victim.getTeam())
			return;
		killer.addKill();
		killer.addScore(1);
		match.addTeamScore(killer.getTeam(), 1);
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
				+ Match.getTeamName(1);
	}
}
