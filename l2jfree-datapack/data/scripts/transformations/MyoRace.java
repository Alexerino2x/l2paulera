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

public class MyoRace extends L2Transformation
{
	public MyoRace()
	{
		// id, colRadius, colHeight
		super(219, 10, 23);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 896, 4); // Rolling Step
		addSkill(player, 897, 4); // Double Blast
		addSkill(player, 898, 4); // Tornado Slash
		addSkill(player, 899, 4); // Cat Roar
		addSkill(player, 900, 4); // Energy Blast
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 896); // Rolling Step
		removeSkill(player, 897); // Double Blast
		removeSkill(player, 898); // Tornado Slash
		removeSkill(player, 899); // Cat Roar
		removeSkill(player, 900); // Energy Blast
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new MyoRace());
	}
}
