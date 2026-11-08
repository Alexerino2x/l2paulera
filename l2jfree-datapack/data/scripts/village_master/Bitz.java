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
public final class Bitz extends QuestJython
{
	private static final String BITZ_OCCUPATION = "30026_bitz_occupation_change";
	
	//Quest NPCs
	private static final int BITZ = 30026;
	
	public Bitz(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(BITZ);
		addTalkId(BITZ);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (event.contains("-01") || event.contains("-02") || event.contains("-03") || event.contains("-04")
				|| event.contains("-05") || event.contains("-06") || event.contains("-07"))
			return event;
		else
			return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		switch (talker.getClassId())
		{
			case HumanFighter:
				return "30026-01.htm";
			case Warrior:
			case HumanKnight:
			case Rogue:
				return "30026-08.htm";
			case Warlord:
			case Paladin:
			case TreasureHunter:
			case Adventurer:
			case HellKnight:
			case Dreadnought:
				return "30026-09.htm";
			case Gladiator:
			case DarkAvenger:
			case Hawkeye:
			case Sagittarius:
			case PhoenixKnight:
			case Duelist:
				return "30026-09.htm";
			default:
				return "30026-10.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new Bitz(-1, BITZ_OCCUPATION, "village_master");
	}
}
