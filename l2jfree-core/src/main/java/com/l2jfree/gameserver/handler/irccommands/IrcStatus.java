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

import com.l2jfree.gameserver.GameServer;
import com.l2jfree.gameserver.datatables.GmListTable;
import com.l2jfree.gameserver.handler.IIrcCommandHandler;
import com.l2jfree.gameserver.instancemanager.IrcManager;

public class IrcStatus implements IIrcCommandHandler
{
	private static final String[] IRC_COMMANDS = { "!status" };
	
	@Override
	public boolean useIrcCommand(String command, String gm, String target, boolean authed)
	{
		if (!authed)
			return false;
		
		if (command.equalsIgnoreCase("!status"))
		{
			IrcManager.getInstance().getConnection().send(gm, "Server Status: ");
			IrcManager.getInstance().getConnection()
					.send(gm, "  ---> Server Uptime: " + getUptime(GameServer.getStartedTime().getTimeInMillis()));
			IrcManager.getInstance().getConnection().send(gm, "  --->      GM Count: " + getOnlineGMS());
			IrcManager.getInstance().getConnection().send(gm, "  --->       Threads: " + Thread.activeCount());
			IrcManager
					.getInstance()
					.getConnection()
					.send(gm,
							"  RAM Used: "
									+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576));
		}
		return true;
	}
	
	private String getUptime(long time)
	{
		long uptime = (System.currentTimeMillis() - time);
		uptime = uptime / 1000;
		long h = uptime / 3600;
		long m = (uptime - (h * 3600)) / 60;
		long s = ((uptime - (h * 3600)) - (m * 60));
		return h + "hrs " + m + "mins " + s + "secs";
	}
	
	private int getOnlineGMS()
	{
		return GmListTable.getAllGms(true).size();
	}
	
	@Override
	public String[] getIrcCommandList()
	{
		return IRC_COMMANDS;
	}
}
