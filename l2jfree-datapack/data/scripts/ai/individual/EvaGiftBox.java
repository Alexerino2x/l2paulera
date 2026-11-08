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

import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.tools.random.Rnd;

/**
 * @author savormix
 *
 */
public class EvaGiftBox extends ItemDropper
{
	private static final String THIS = "EvaGiftBox";
	
	// Quest items
	private static final int RED_CORAL = 9692;
	private static final int CRYSTAL_FRAGMENT = 9693;
	
	// Quest monsters
	private static final int EVAS_GIFT_BOX = 32342;
	
	private static final int[] KISS_OF_EVA_EFFECT = { 1073, 3143 };
	private static final int KISS_OF_EVA_SKILL = 3252;
	
	public EvaGiftBox(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addKillId(EVAS_GIFT_BOX);
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		boolean reward = killer.getSkillLevel(KISS_OF_EVA_SKILL) > 0;
		if (!reward)
		{
			for (int i : KISS_OF_EVA_EFFECT)
			{
				if (killer.getEffects().hasEffect(i))
				{
					reward = true;
					break;
				}
			}
		}
		if (reward)
		{
			if (Rnd.nextBoolean())
				dropItem(npc, RED_CORAL, 1);
			else
				dropItem(npc, CRYSTAL_FRAGMENT, 1);
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new EvaGiftBox(-1, THIS, "ai");
	}
}
