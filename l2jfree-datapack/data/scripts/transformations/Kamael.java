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

public class Kamael extends L2Transformation
{
	public Kamael()
	{
		// id, colRadius, colHeight
		super(251, 10, 32.76);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 539, 1); // Nail Attack
		addSkill(player, 540, 1); // Wing Assault
		addSkill(player, 1471, 1); // Soul Sucking
		addSkill(player, 1472, 1); // Death Beam
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 539); // Nail Attack
		removeSkill(player, 540); // Wing Assault
		removeSkill(player, 1471); // Soul Sucking
		removeSkill(player, 1472); // Death Beam
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Kamael());
	}
}
