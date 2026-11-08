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
package ai.individual;

import ai.group_template.L2AttackableAIScript;

import com.l2jfree.gameserver.datatables.SkillTable;
import com.l2jfree.gameserver.gameobjects.L2Attackable;
import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.gameobjects.ai.CtrlIntention;
import com.l2jfree.gameserver.model.items.L2ItemInstance;
import com.l2jfree.gameserver.model.skills.L2Skill;

/**
 * @author Psycho(killer1888) / L2jFree
 */

public class GatekeeperZombie extends L2AttackableAIScript
{
	private static final int[] GATEKEEPER = { 18343 };
	private static final int[] ITEMS = { 8064, 8065, 8067 };
	
	public GatekeeperZombie(int questId, String name, String descr)
	{
		super(questId, name, descr);
		registerMobs(GATEKEEPER);
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2Player player, boolean isPet)
	{
		boolean isAuthorized = false;
		L2ItemInstance item;
		
		if (!npc.isInCombat() && !isPet)
		{
			for (int itemId : ITEMS)
			{
				item = player.getInventory().getItemByItemId(itemId);
				if (item != null)
				{
					isAuthorized = true;
					break;
				}
			}
			if (!isAuthorized)
			{
				npc.setTarget(player);
				((L2Attackable)npc).addDamageHate(player, 0, 999);
				L2Skill skill = SkillTable.getInstance().getInfo(5043, 9);
				npc.doCast(skill);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
			}
			else
			{
				L2Attackable actor = (L2Attackable)npc;
				int hate = actor.getHating(player);
				if (hate == 1)
					actor.reduceHate(player, hate);
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new GatekeeperZombie(-1, "GatekeeperZombie", "ai");
	}
}
