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

public class Zaken extends L2Transformation
{
	public Zaken()
	{
		// id, colRadius, colHeight
		super(305, 16, 32);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 715, 4); // Zaken Energy Drain
		addSkill(player, 716, 4); // Zaken Hold
		addSkill(player, 717, 4); // Zaken Concentrated Attack
		addSkill(player, 718, 4); // Zaken Dancing Sword
		addSkill(player, 719, 1); // Zaken Vampiric Rage
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 715); // Zaken Energy Drain
		removeSkill(player, 716); // Zaken Hold
		removeSkill(player, 717); // Zaken Concentrated Attack
		removeSkill(player, 718); // Zaken Dancing Sword
		removeSkill(player, 719); // Zaken Vampiric Rage
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Zaken());
	}
}
