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
package transformations;

import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.instancemanager.TransformationManager;
import com.l2jfree.gameserver.model.L2Transformation;

public class DivineEnchanter extends L2Transformation
{
	public DivineEnchanter()
	{
		// id, colRadius, colHeight
		super(257, 8, 18.25);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 704, 1); // Divine Enchanter Water Spirit
		addSkill(player, 705, 1); // Divine Enchanter Fire Spirit
		addSkill(player, 706, 1); // Divine Enchanter Wind Spirit
		addSkill(player, 707, 1); // Divine Enchanter Hero Spirit
		addSkill(player, 708, 1); // Divine Enchanter Mass Binding
		addSkill(player, 709, 1); // Sacrifice Enchanter
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 704); // Divine Enchanter Water Spirit
		removeSkill(player, 705); // Divine Enchanter Fire Spirit
		removeSkill(player, 706); // Divine Enchanter Wind Spirit
		removeSkill(player, 707); // Divine Enchanter Hero Spirit
		removeSkill(player, 708); // Divine Enchanter Mass Binding
		removeSkill(player, 709); // Sacrifice Enchanter
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineEnchanter());
	}
}
