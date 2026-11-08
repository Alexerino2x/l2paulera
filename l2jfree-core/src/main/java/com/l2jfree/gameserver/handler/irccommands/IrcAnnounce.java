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

import com.l2jfree.gameserver.Announcements;
import com.l2jfree.gameserver.handler.IIrcCommandHandler;

/**
 * @author nBd
 */
public class IrcAnnounce implements IIrcCommandHandler
{
	private static final String[] IRC_COMMANDS = { "!announce" };
	
	@Override
	public boolean useIrcCommand(String command, String gm, String target, boolean authed)
	{
		if (!authed)
			return false;
		
		if (command.startsWith("!announce "))
		{
			Announcements.getInstance().handleAnnounce(command, 10);
		}
		return true;
	}
	
	@Override
	public String[] getIrcCommandList()
	{
		return IRC_COMMANDS;
	}
}
