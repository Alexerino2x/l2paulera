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
public final class BaylorTreasureChest extends ItemDropper
{
	private static final String THIS = "BaylorTreasureChest";
	
	// Quest items
	private static final int DYNASTY_EARRING = 9470;
	private static final int BLESSED_ENCHANT_SCROLL = 6578;
	private static final int SEALED_BOOTS_DESIGN = 6704;
	
	// Quest monsters
	private static final int[] TREASURE_CHEST = { 29116, 29117 };
	
	public BaylorTreasureChest(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int id : TREASURE_CHEST)
			addKillId(id);
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		int rnd = Rnd.get(100);
		if (rnd < 2)
			dropItem(npc, DYNASTY_EARRING, 1);
		else if (rnd < 33)
			dropItem(npc, BLESSED_ENCHANT_SCROLL, 2);
		else
			dropItem(npc, SEALED_BOOTS_DESIGN, 10);
		return null;
	}
	
	public static void main(String[] args)
	{
		new BaylorTreasureChest(-1, THIS, "ai");
	}
}
