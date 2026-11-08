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

import com.l2jfree.gameserver.datatables.ItemTable;
import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.model.items.L2ItemInstance;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;

/**
 * @author savormix
 *
 */
public abstract class ItemDropper extends QuestJython
{
	public ItemDropper(int questId, String name, String descr)
	{
		super(questId, name, descr);
	}
	
	protected void dropItem(L2Npc dropper, int itemId, long count)
	{
		L2ItemInstance item = ItemTable.getInstance().createItem(getName(), itemId, count, null, dropper);
		item.dropMe(dropper, dropper.getX(), dropper.getY(), dropper.getZ());
	}
}
