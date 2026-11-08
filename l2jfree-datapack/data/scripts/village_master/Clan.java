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
package village_master;

import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;

/**
 * @author savormix
 *
 */
public final class Clan extends VillageMaster
{
	private static final String CLAN = "9000_clan";
	
	public Clan(int questId, String name, String descr)
	{
		super(questId, name, descr);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (event.endsWith("03.htm") || event.endsWith("04.htm") || event.endsWith("05.htm"))
		{
			if (!player.isClanLeader())
				return event.replace(".", "-no.");
		}
		else if (event.endsWith("07.htm") || event.endsWith("06a.htm") || event.endsWith("12a.htm")
				|| event.endsWith("13a.htm") || event.endsWith("13b.htm") || event.endsWith("14a.htm")
				|| event.endsWith("15.htm"))
		{
			if (!player.isClanLeader())
				return "9000-07-no.htm";
		}
		return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		return "9000-01.htm";
	}
	
	public static void main(String[] args)
	{
		new Clan(-1, CLAN, "village_master");
	}
}
