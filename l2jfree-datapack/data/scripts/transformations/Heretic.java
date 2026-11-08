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

public class Heretic extends L2Transformation
{
	public Heretic()
	{
		// id, colRadius, colHeight
		super(3, 7.7, 28.4);
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
		
		addSkill(player, 738, level); // Heretic Heal
		addSkill(player, 739, level); // Heretic Battle Heal
		addSkill(player, 740, level); // Heretic Resurrection
		addSkill(player, 741, level); // Heretic Heal Side Effect
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 738); // Heretic Heal
		removeSkill(player, 739); // Heretic Battle Heal
		removeSkill(player, 740); // Heretic Resurrection
		removeSkill(player, 741); // Heretic Heal Side Effect
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Heretic());
	}
}
