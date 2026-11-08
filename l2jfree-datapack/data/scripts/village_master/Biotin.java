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
import com.l2jfree.gameserver.model.quest.jython.QuestJython;

/**
 * @author savormix
 *
 */
public final class Biotin extends QuestJython
{
	private static final String BIOTIN_OCCUPATION = "30031_biotin_occupation_change";
	
	//Quest NPCs
	private static final int BIOTIN = 30031;
	
	public Biotin(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(BIOTIN);
		addTalkId(BIOTIN);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (event.contains("-01") || event.contains("-02") || event.contains("-03") || event.contains("-04")
				|| event.contains("-05"))
			return event;
		else
			return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		switch (talker.getClassId())
		{
			case HumanWizard:
			case Cleric:
				return "30031-06.htm";
			case Sorceror:
			case Necromancer:
			case Warlock:
			case Bishop:
			case Prophet:
				return "30031-07.htm";
			default:
				return "30031-08.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new Biotin(-1, BIOTIN_OCCUPATION, "village_master");
	}
}
