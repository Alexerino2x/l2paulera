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

public class ValeMaster extends L2Transformation
{
	public ValeMaster()
	{
		// id, colRadius, colHeight
		super(4, 12, 40);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		int level = -1;
		if (player.getLevel() >= 76)
		{
			level = 3;
		}
		else if (player.getLevel() >= 73)
		{
			level = 2;
		}
		else if (player.getLevel() >= 70)
		{
			level = 1;
		}
		
		addSkill(player, 742, level); // Vale Master Bursting Flame
		addSkill(player, 743, level); // Vale Master Dark Explosion
		addSkill(player, 744, level); // Vale Master Dark Flare
		addSkill(player, 745, level); // Vale Master Dark Cure
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 742); // Vale Master Bursting Flame
		removeSkill(player, 743); // Vale Master Dark Explosion
		removeSkill(player, 744); // Vale Master Dark Flare
		removeSkill(player, 745); // Vale Master Dark Cure
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new ValeMaster());
	}
}
