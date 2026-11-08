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

public class ArcherCaptain extends L2Transformation
{
	public ArcherCaptain()
	{
		// id, colRadius, colHeight
		super(17, 10, 23.5);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 872, 1); // Archer Captain Power Shot
		addSkill(player, 873, 1); // Archer Captain Rapid Shot
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 872); // Archer Captain Power Shot
		removeSkill(player, 873); // Archer Captain Rapid Shot
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new ArcherCaptain());
	}
}
