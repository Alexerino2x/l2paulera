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
package com.l2jfree.loginserver.status.commands;

import com.l2jfree.loginserver.manager.BanManager;
import com.l2jfree.status.StatusCommand;

/**
 * @author NB4L1
 */
public final class UnblockIP extends StatusCommand
{
	@Override
	protected void useCommand(String command, String params)
	{
		if (BanManager.getInstance().removeBanForAddress(params))
		{
			final String message = "The IP " + params + " has been removed from ban list till restart";
			
			println(message + "!");
			
			_log.warn(message + " via telnet by host: " + getHostAddress());
		}
		else
		{
			println("IP not found in ban list...");
		}
	}
	
	private static final String[] COMMANDS = { "unblock" };
	
	@Override
	protected String[] getCommands()
	{
		return COMMANDS;
	}
	
	@Override
	protected String getDescription()
	{
		return "removes ip from ban list till restart";
	}
	
	@Override
	protected String getParameterUsage()
	{
		return "ip";
	}
	
}
