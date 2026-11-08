package com.l2paulera.bg.mode;

import java.util.List;

import com.l2jfree.gameserver.model.Location;
import com.l2paulera.bg.Match;
import com.l2paulera.bg.Participant;

/**
 * Um modo de battleground (TvT, Deathmatch, ...). Cada modo e um modulo: o motor cuida de fila,
 * teleporte, instancia, tempo, placar e pontos; o modo decide como se pontua e quem vence.
 */
public interface BattlegroundMode
{
	/** id curto usado em comandos, config e banco (ex.: "tvt") */
	String getId();
	
	/** nome mostrado aos jogadores */
	String getDisplayName();
	
	/** numero de times; 0 = cada um por si */
	int getTeamCount();
	
	/** se o jogador volta depois de morrer */
	boolean isRespawnEnabled();
	
	/** ponto de nascimento de um participante */
	Location getSpawn(Match match, Participant participant);
	
	/** chamado depois do teleporte, antes do combate liberar */
	default void onStart(Match match)
	{
	}
	
	/** uma morte entre dois participantes (killer pode ser null) */
	void onKill(Match match, Participant killer, Participant victim);
	
	/** chamado a cada tick do motor durante o combate */
	default void onTick(Match match)
	{
	}
	
	/** @return true se a partida ja tem resultado antes do tempo acabar */
	boolean isFinished(Match match);
	
	/**
	 * @return os vencedores. Vazio = empate. Em modo de time, todos do time vencedor.
	 */
	List<Participant> getWinners(Match match);
	
	/** linha de placar curta para anuncios */
	String getScoreLine(Match match);
}
