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
public final class Thifiell extends QuestJython
{
	private static final String THIFIELL_OCCUPATION = "30358_thifiell_occupation_change";
	
	//Quest NPCs
	private static final int THIFIELL = 30358;
	
	public Thifiell(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(THIFIELL);
		addTalkId(THIFIELL);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (event.contains("-11") || event.contains("-12") || event.contains("-13"))
			return null;
		else
			return event;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		if (talker.getRace() != Race.Darkelf)
			return "30358-11.htm";
		switch (talker.getClassId())
		{
			case DarkFighter:
				return "30358-01.htm";
			case DarkMystic:
				return "30358-02.htm";
			case DarkWizard:
			case ShillienOracle:
			case PalusKnight:
			case Assassin:
				return "30358-12.htm";
			default:
				return "30358-13.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new Thifiell(-1, THIFIELL_OCCUPATION, "village_master");
	}
}
