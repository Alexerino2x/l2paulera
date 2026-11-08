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
package com.l2jfree.gameserver.handler.irccommands;

import com.l2jfree.gameserver.handler.IIrcCommandHandler;
import com.l2jfree.gameserver.instancemanager.IrcManager;
import com.l2jfree.gameserver.network.L2IrcClient;

/**
 * 
 * @author nBd
 */
public class IrcHelp implements IIrcCommandHandler
{
	private static final String[] IRC_COMMANDS = { "!help" };
	
	/**
	 * @see net.sf.l2j.gameserver.handler.IIrcCommandHandler#getIrcCommandList()
	 */
	@Override
	public String[] getIrcCommandList()
	{
		return IRC_COMMANDS;
	}
	
	/**
	 * @see net.sf.l2j.gameserver.handler.IIrcCommandHandler#useIrcCommand(java.lang.String,
	 *      java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean useIrcCommand(String command, String gm, String target, boolean authed)
	{
		if (command.startsWith("!help"))
		{
			L2IrcClient irc = IrcManager.getInstance().getConnection();
			
			if (authed)
			{
				if (irc != null)
				{
					irc.send(gm, "Avaiable Commands:");
					irc.send(gm, "!announce <Text> - Announces given Text InGame");
					irc.send(gm, "!kick <Nick> - Kicks given Player");
					irc.send(gm, "!kick_nongm - Kicks all NonGM Player");
					irc.send(gm, "!status - Shows Server Status");
				}
				return true;
			}
			else
			{
				if (irc != null)
				{
					irc.send(gm, "Avaiable Commands:");
					irc.send(gm, "!online");
				}
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}
}
