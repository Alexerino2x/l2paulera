/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree.loginserver.beans;

import com.l2jfree.loginserver.thread.GameServerThread;
import com.l2jfree.network.ServerStatus;

/**
 * 
 */
public class GameServerInfo
{
	// auth
	private int _id;
	private final byte[] _hexId;
	private boolean _isAuthed;
	
	// status
	private GameServerThread _gst;
	private ServerStatus _status;
	
	// network
	private String _ip;
	private int _port;
	
	// config
	private boolean _isPvp;
	private boolean _clock;
	private boolean _hideName;
	private boolean _unk1;
	private boolean _testServer;
	private boolean _brackets;
	private int _maxPlayers;
	private int _age;
	
	public GameServerInfo(int id, byte[] hexId, GameServerThread gst)
	{
		_id = id;
		_hexId = hexId;
		_gst = gst;
		_status = ServerStatus.STATUS_DOWN;
		// these values are not necessarily sent by the game server
		_isPvp = true;
		_hideName = false;
		_unk1 = false;
		_age = 0;
	}
	
	public GameServerInfo(int id, byte[] hexId)
	{
		this(id, hexId, null);
	}
	
	public void setId(int id)
	{
		_id = id;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public byte[] getHexId()
	{
		return _hexId;
	}
	
	public void setAuthed(boolean isAuthed)
	{
		_isAuthed = isAuthed;
	}
	
	public boolean isAuthed()
	{
		return _isAuthed;
	}
	
	public void setGameServerThread(GameServerThread gst)
	{
		_gst = gst;
	}
	
	public GameServerThread getGameServerThread()
	{
		return _gst;
	}
	
	public void setStatus(int status)
	{
		setStatus(ServerStatus.valueOf(status));
	}
	
	public void setStatus(ServerStatus status)
	{
		_status = status;
	}
	
	public ServerStatus getStatus()
	{
		return _status;
	}
	
	public int getCurrentPlayerCount()
	{
		if (_gst == null)
			return 0;
		return _gst.getPlayerCount();
	}
	
	public void setIp(String ip)
	{
		_ip = ip;
	}
	
	public String getIp()
	{
		return _ip;
	}
	
	public int getPort()
	{
		return _port;
	}
	
	public void setPort(int port)
	{
		_port = port;
	}
	
	public void setMaxPlayers(int maxPlayers)
	{
		_maxPlayers = maxPlayers;
	}
	
	public int getMaxPlayers()
	{
		return _maxPlayers;
	}
	
	public void setPvp(boolean val)
	{
		_isPvp = val;
	}
	
	public boolean isPvp()
	{
		return _isPvp;
	}
	
	public boolean isOnline()
	{
		return getStatus() != ServerStatus.STATUS_DOWN;
	}
	
	public void setAgeLimitation(int age)
	{
		_age = age;
	}
	
	public int getAgeLimitation()
	{
		if (isOnline())
			return 0;
		return _age;
	}
	
	public void setShowingClock(boolean clock)
	{
		_clock = clock;
	}
	
	public boolean showClock()
	{
		return _clock;
	}
	
	public void setHideName(boolean hide)
	{
		_hideName = hide;
	}
	
	public boolean hideName()
	{
		return _hideName;
	}
	
	public void setUnk1(boolean _unk1)
	{
		this._unk1 = _unk1;
	}
	
	public boolean isUnk1()
	{
		return _unk1;
	}
	
	public void setTestServer(boolean val)
	{
		_testServer = val;
	}
	
	public boolean testServer()
	{
		return _testServer;
	}
	
	public void setShowingBrackets(boolean val)
	{
		_brackets = val;
	}
	
	public boolean showBrackets()
	{
		return _brackets;
	}
	
	public void setDown()
	{
		setAuthed(false);
		setPort(0);
		setGameServerThread(null);
		setStatus(ServerStatus.STATUS_DOWN);
	}
}
