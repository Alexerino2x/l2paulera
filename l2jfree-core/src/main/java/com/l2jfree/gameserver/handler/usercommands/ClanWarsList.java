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
package com.l2jfree.gameserver.handler.usercommands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.l2jfree.L2DatabaseFactory;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.handler.IUserCommandHandler;
import com.l2jfree.gameserver.model.clan.L2Clan;
import com.l2jfree.gameserver.network.SystemMessageId;
import com.l2jfree.gameserver.network.packets.server.SystemMessage;

/**
 * Support for /ClanWarsList command
 * @author Tempy - 28 Jul 05
 */
public class ClanWarsList implements IUserCommandHandler
{
	private static final int[] COMMAND_IDS = { 88, 89, 90 };
	
	/* (non-Javadoc)
	 * @see com.l2jfree.gameserver.handler.IUserCommandHandler#useUserCommand(int, com.l2jfree.gameserver.model.L2Player)
	 */
	@Override
	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if (id != COMMAND_IDS[0] && id != COMMAND_IDS[1] && id != COMMAND_IDS[2])
			return false;
		
		L2Clan clan = activeChar.getClan();
		if (clan == null)
		{
			activeChar.sendPacket(SystemMessageId.YOU_ARE_NOT_A_CLAN_MEMBER);
			return false;
		}
		
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection(con);
			PreparedStatement statement;
			if (id == 88)
			{
				// Attack list
				activeChar.sendPacket(SystemMessageId.CLANS_YOU_DECLARED_WAR_ON);
				statement =
						con.prepareStatement("SELECT clan_name,clan_id,ally_id,ally_name FROM clan_data,clan_wars WHERE clan1=? and clan_id=clan2 AND clan2 NOT IN (SELECT clan1 FROM clan_wars WHERE clan2=?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, clan.getClanId());
			}
			else if (id == 89)
			{
				// Under attack list
				activeChar.sendPacket(SystemMessageId.CLANS_THAT_HAVE_DECLARED_WAR_ON_YOU);
				statement =
						con.prepareStatement("SELECT clan_name,clan_id,ally_id,ally_name FROM clan_data,clan_wars WHERE clan2=? AND clan_id=clan1 AND clan1 NOT IN (SELECT clan2 FROM clan_wars WHERE clan1=?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, clan.getClanId());
			}
			else
			// id = 90
			{
				// War list
				activeChar.sendPacket(SystemMessageId.WAR_LIST);
				statement =
						con.prepareStatement("SELECT clan_name,clan_id,ally_id,ally_name FROM clan_data,clan_wars WHERE clan1=? AND clan_id=clan2 AND clan2 IN (SELECT clan1 FROM clan_wars WHERE clan2=?)");
				statement.setInt(1, clan.getClanId());
				statement.setInt(2, clan.getClanId());
			}
			ResultSet rset = statement.executeQuery();
			while (rset.next())
			{
				SystemMessage sm = null;
				String clanName = rset.getString("clan_name");
				int ally_id = rset.getInt("ally_id");
				if (ally_id > 0)
				{
					//target with ally
					sm = new SystemMessage(SystemMessageId.S1_S2_ALLIANCE);
					sm.addString(clanName);
					sm.addString(rset.getString("ally_name"));
				}
				else
				{
					//target without ally
					sm = new SystemMessage(SystemMessageId.S1_NO_ALLI_EXISTS);
					sm.addString(clanName);
				}
				activeChar.sendPacket(sm);
			}
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warn("", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.l2jfree.gameserver.handler.IUserCommandHandler#getUserCommandList()
	 */
	@Override
	public int[] getUserCommandList()
	{
		return COMMAND_IDS;
	}
}
