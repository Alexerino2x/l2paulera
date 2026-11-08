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

public class DollBlader extends L2Transformation
{
	public DollBlader()
	{
		// id, colRadius, colHeight
		super(7, 6, 12);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		int level = -1;
		if (player.getLevel() >= 76)
			level = 3;
		else if (player.getLevel() >= 73)
			level = 2;
		else if (player.getLevel() >= 70)
			level = 1;
		
		addSkill(player, 752, level); // Doll Blader Sting
		addSkill(player, 753, level); // Doll Blader Throwing Knife
		addSkill(player, 754, 1); // Doll Blader Clairvoyance
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 752); // Doll Blader Sting
		removeSkill(player, 753); // Doll Blader Throwing Knife
		removeSkill(player, 754); // Doll Blader Clairvoyance
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DollBlader());
	}
}
