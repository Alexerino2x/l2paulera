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
public final class Asterios extends QuestJython
{
	private static final String ASTERIOS_OCCUPATION = "30154_asterios_occupation_change";
	
	//Quest NPCs
	private static final int ASTERIOS = 30154;
	
	public Asterios(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(ASTERIOS);
		addTalkId(ASTERIOS);
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
		if (talker.getRace() != Race.Elf)
			return "30154-11.htm";
		switch (talker.getClassId())
		{
			case ElvenFighter:
				return "30154-01.htm";
			case ElvenMystic:
				return "30154-02.htm";
			case ElvenWizard:
			case ElvenOracle:
			case ElvenKnight:
			case ElvenScout:
				return "30154-12.htm";
			default:
				return "30154-13.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new Asterios(-1, ASTERIOS_OCCUPATION, "village_master");
	}
}
