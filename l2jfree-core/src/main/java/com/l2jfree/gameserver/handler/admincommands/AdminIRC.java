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
package com.l2jfree.gameserver.handler.admincommands;

import java.util.StringTokenizer;

import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.handler.IAdminCommandHandler;
import com.l2jfree.gameserver.instancemanager.IrcManager;

/**
 * This class handles following admin commands:
 * admin_ircc admin_ircm
 */
public class AdminIRC implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS = { "admin_ircc", "admin_ircm" };
	
	@Override
	public boolean useAdminCommand(String command, L2Player activeChar)
	{
		String text = command.substring(10);
		
		if (command.startsWith("admin_ircc"))
		{
			IrcManager.getInstance().getConnection().send(text);
		}
		else if (command.startsWith("admin_ircm"))
		{
			StringTokenizer st = new StringTokenizer(text);
			String name = st.nextToken();
			String message = text.substring(name.length() + 1);
			IrcManager.getInstance().getConnection().send(name, message);
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
