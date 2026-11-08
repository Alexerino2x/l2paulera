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
package com.l2jfree.loginserver.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.l2jfree.Config;

/**
 * @author KenM
 */
public final class GameServerListener extends FloodProtectedListener
{
	private static final class SingletonHolder
	{
		private static final GameServerListener INSTANCE = new GameServerListener();
	}
	
	public static GameServerListener getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private final List<GameServerThread> _gameServers = new CopyOnWriteArrayList<GameServerThread>();
	
	private GameServerListener()
	{
		super(Config.LOGIN_HOSTNAME, Config.LOGIN_PORT);
		start();
		_log.info("GameServerListener: Initialized.");
	}
	
	@Override
	public void addClient(Socket s)
	{
		if (_log.isDebugEnabled())
			_log.info("Received gameserver connection from: " + s.getInetAddress().getHostAddress());
		
		try
		{
			_gameServers.add(new GameServerThread(s));
		}
		catch (Exception e)
		{
			_log.warn("", e);
			
			try
			{
				s.close();
			}
			catch (IOException e1)
			{
				_log.warn("", e1);
			}
		}
	}
	
	public void removeGameServer(GameServerThread gst)
	{
		_gameServers.remove(gst);
	}
	
	public void playerSelectedServer(int id, String ip)
	{
		for (GameServerThread gst : _gameServers)
		{
			if (gst.getServerId() == id)
			{
				gst.playerSelectedServer(ip);
				break;
			}
		}
	}
}
