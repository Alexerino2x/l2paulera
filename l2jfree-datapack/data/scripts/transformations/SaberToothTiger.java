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

public class SaberToothTiger extends L2Transformation
{
	public SaberToothTiger()
	{
		// id, colRadius, colHeight
		super(5, 34, 28);
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
		
		addSkill(player, 746, level); // Saber Tooth Tiger Bite
		addSkill(player, 747, level); // Saber Tooth Tiger Fear
		addSkill(player, 748, 1); // Saber Tooth Tiger Sprint
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 746); // Saber Tooth Tiger Bite
		removeSkill(player, 747); // Saber Tooth Tiger Fear
		removeSkill(player, 748); // Saber Tooth Tiger Sprint
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new SaberToothTiger());
	}
}
