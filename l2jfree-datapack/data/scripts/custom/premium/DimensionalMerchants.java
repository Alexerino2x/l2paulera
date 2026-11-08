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
package custom.premium;

import com.l2jfree.Config;
import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.quest.QuestState;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;

/**
 * @author savormix
 *
 */
public final class DimensionalMerchants extends QuestJython
{
	private static final String THIS = "1003_DimensionalMerchants";
	
	// Quest NPCs
	private static final int VITAMIN_MANAGER = 32478;
	
	// Quest items
	private static final int COUPON_NORMAL = 13273;
	private static final int COUPON_EVENT = 13383;
	private static final int COUPON_HQ = 14065;
	
	public DimensionalMerchants(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(VITAMIN_MANAGER);
		addFirstTalkId(VITAMIN_MANAGER);
		addTalkId(VITAMIN_MANAGER);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		QuestState qs = player.getQuestState(THIS);
		if (qs == null)
			return "32478-na.htm";
		if (event.startsWith("130"))
		{
			if (qs.getQuestItemsCount(COUPON_NORMAL) > 0)
			{
				qs.takeItems(COUPON_NORMAL, 1);
				qs.giveItems(Integer.parseInt(event), 1);
				qs.exitQuest(true);
				return null;
			}
			else if (qs.getQuestItemsCount(COUPON_EVENT) > 0)
			{
				qs.takeItems(COUPON_EVENT, 1);
				qs.giveItems(Integer.parseInt(event) + 286, 1);
				qs.exitQuest(true);
				return null;
			}
			else
				return "32478-11.htm";
		}
		else if (event.startsWith("135"))
		{
			if (qs.getQuestItemsCount(COUPON_HQ) > 0)
			{
				qs.takeItems(COUPON_HQ, 1);
				qs.giveItems(Integer.parseInt(event), 1);
				qs.exitQuest(true);
				return null;
			}
			else
				return "32478-11.htm";
		}
		return event;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2Player player)
	{
		QuestState qs = player.getQuestState(THIS);
		if (qs == null)
			qs = newQuestState(player);
		if (Config.ALT_ENABLE_DIMENSIONAL_MERCHANTS)
			return "32478.htm";
		else
			return "32478-na.htm";
	}
	
	public static void main(String[] args)
	{
		new DimensionalMerchants(-1, THIS, "custom");
	}
}
