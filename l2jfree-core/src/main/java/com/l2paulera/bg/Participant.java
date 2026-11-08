package com.l2paulera.bg;

import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.Location;
import com.l2jfree.gameserver.model.world.L2World;

/**
 * Um jogador dentro de uma partida.
 */
public final class Participant
{
	private final int _objectId;
	private final String _name;
	private final Location _returnLoc;
	private int _team;
	private int _kills;
	private int _deaths;
	private int _score;
	private boolean _eliminated;
	private boolean _left;
	
	Participant(L2Player player, int team)
	{
		_objectId = player.getObjectId();
		_name = player.getName();
		_returnLoc = new Location(player.getX(), player.getY(), player.getZ(), player.getHeading());
		_team = team;
	}
	
	public int getObjectId()
	{
		return _objectId;
	}
	
	public String getName()
	{
		return _name;
	}
	
	/** @return o jogador online, ou null */
	public L2Player getPlayer()
	{
		return L2World.getInstance().getPlayer(_objectId);
	}
	
	Location getReturnLoc()
	{
		return _returnLoc;
	}
	
	/** @return o time (0, 1, ...) ou -1 em modos sem time */
	public int getTeam()
	{
		return _team;
	}
	
	void setTeam(int team)
	{
		_team = team;
	}
	
	public int getKills()
	{
		return _kills;
	}
	
	public int getDeaths()
	{
		return _deaths;
	}
	
	public int getScore()
	{
		return _score;
	}
	
	public void addKill()
	{
		_kills++;
	}
	
	public void addDeath()
	{
		_deaths++;
	}
	
	public void addScore(int value)
	{
		_score += value;
	}
	
	public boolean isEliminated()
	{
		return _eliminated;
	}
	
	public void setEliminated(boolean eliminated)
	{
		_eliminated = eliminated;
	}
	
	/** saiu ou desconectou durante a partida */
	public boolean hasLeft()
	{
		return _left;
	}
	
	void setLeft()
	{
		_left = true;
	}
}
