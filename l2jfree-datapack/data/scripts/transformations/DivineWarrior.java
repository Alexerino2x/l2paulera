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

public class DivineWarrior extends L2Transformation
{
	public DivineWarrior()
	{
		// id, colRadius, colHeight
		super(253, 14.5, 29);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 675, 1); // Cross Slash
		addSkill(player, 676, 1); // Sonic Blaster
		addSkill(player, 677, 1); // Transfixition of Earth
		addSkill(player, 678, 1); // Divine Warrior War Cry
		addSkill(player, 679, 1); // Sacrifice Warrior
		addSkill(player, 798, 1); // Divine Warrior Assault Attack
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 675); // Cross Slash
		removeSkill(player, 676); // Sonic Blaster
		removeSkill(player, 677); // Transfixition of Earth
		removeSkill(player, 678); // Divine Warrior War Cry
		removeSkill(player, 679); // Sacrifice Warrior
		removeSkill(player, 798); // Divine Warrior Assault Attack
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineWarrior());
	}
}
