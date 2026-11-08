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
public final class Alliance extends VillageMaster
{
	private static final String ALLIANCE = "9001_alliance";
	
	public Alliance(int questId, String name, String descr)
	{
		super(questId, name, descr);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (player.getClan() == null)
			return "9001-no.htm";
		else
			return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		return "9001-01.htm";
	}
	
	public static void main(String[] args)
	{
		new Alliance(-1, ALLIANCE, "village_master");
	}
}
