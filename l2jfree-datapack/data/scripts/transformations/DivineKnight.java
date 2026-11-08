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

public class DivineKnight extends L2Transformation
{
	public DivineKnight()
	{
		// id, colRadius, colHeight
		super(252, 16, 30);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 680, 1); // Divine Knight Hate
		addSkill(player, 681, 1); // Divine Knight Hate Aura
		addSkill(player, 682, 1); // Divine Knight Stun Attack
		addSkill(player, 683, 1); // Divine Knight Thunder Storm
		addSkill(player, 684, 1); // Divine Knight Ultimate Defense
		addSkill(player, 685, 1); // Sacrifice Knight
		addSkill(player, 795, 1); // Divine Knight Brandish
		addSkill(player, 796, 1); // Divine Knight Explosion
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 680); // Divine Knight Hate
		removeSkill(player, 681); // Divine Knight Hate Aura
		removeSkill(player, 682); // Divine Knight Stun Attack
		removeSkill(player, 683); // Divine Knight Thunder Storm
		removeSkill(player, 684); // Divine Knight Ultimate Defense
		removeSkill(player, 685); // Sacrifice Knight
		removeSkill(player, 795); // Divine Knight Brandish
		removeSkill(player, 796); // Divine Knight Explosion
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineKnight());
	}
}
