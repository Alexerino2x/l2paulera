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
import com.l2jfree.gameserver.gameobjects.base.Race;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;

/**
 * @author savormix
 *
 */
public final class Reed extends QuestJython
{
	private static final String REED_OCCUPATION = "30520_reed_occupation_change";
	
	//Quest NPCs
	private static final int REED = 30520;
	
	public Reed(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(REED);
		addTalkId(REED);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (event.contains("-01") || event.contains("-02") || event.contains("-03") || event.contains("-04"))
			return event;
		else
			return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		if (talker.getRace() != Race.Dwarf)
			return "30520-07.htm";
		switch (talker.getClassId())
		{
			case DwarvenFighter:
				return "30520-01.htm";
			case Artisan:
			case Scavenger:
				return "30520-05.htm";
			default:
				return "30520-06.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new Reed(-1, REED_OCCUPATION, "village_master");
	}
}
