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
package com.l2jfree.loginserver.network.gameserverpackets;

import com.l2jfree.loginserver.beans.GameServerInfo;
import com.l2jfree.loginserver.manager.GameServerManager;
import com.l2jfree.network.ServerStatusAttributes;

/**
 * @author -Wooden-, savormix
 */
public final class ServerStatus extends GameToLoginPacket
{
	public ServerStatus(byte[] decrypt, int serverID)
	{
		super(decrypt);
		
		final GameServerInfo gsi = GameServerManager.getInstance().getRegisteredGameServerById(serverID);
		
		int size = readD();
		for (int i = 0; i < size; i++)
		{
			ServerStatusAttributes type = ServerStatusAttributes.valueOf(readD());
			int value = readD();
			
			if (gsi == null)
				continue;
			
			switch (type)
			{
				case SERVER_LIST_STATUS:
					gsi.setStatus(value);
					break;
				case SERVER_LIST_CLOCK:
					gsi.setShowingClock(value != 0);
					break;
				case SERVER_LIST_BRACKETS:
					gsi.setShowingBrackets(value != 0);
					break;
				case SERVER_LIST_MAX_PLAYERS:
					gsi.setMaxPlayers(value);
					break;
				case TEST_SERVER:
					gsi.setTestServer(value != 0);
					break;
				case SERVER_LIST_PVP:
					gsi.setPvp(value != 0);
					break;
				case SERVER_LIST_UNK:
					gsi.setUnk1(value != 0);
					break;
				case SERVER_LIST_HIDE_NAME:
					gsi.setHideName(value != 0);
					break;
				case SERVER_AGE_LIMITATION:
					gsi.setAgeLimitation(value);
					break;
				case NONE:
					break;
			}
		}
	}
}
