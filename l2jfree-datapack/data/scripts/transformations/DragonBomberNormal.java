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

public class DragonBomberNormal extends L2Transformation
{
	public DragonBomberNormal()
	{
		// id, colRadius, colHeight
		super(217, 16, 24);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 580, 3); // Death Blow
		addSkill(player, 581, 3); // Sand Cloud
		addSkill(player, 582, 3); // Scope Bleed
		addSkill(player, 583, 3); // Assimilation
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 580); // Death Blow
		removeSkill(player, 581); // Sand Cloud
		removeSkill(player, 582); // Scope Bleed
		removeSkill(player, 583); // Assimilation
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DragonBomberNormal());
	}
}
