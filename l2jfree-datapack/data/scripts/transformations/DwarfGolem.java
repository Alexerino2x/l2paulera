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

public class DwarfGolem extends L2Transformation
{
	public DwarfGolem()
	{
		// id, colRadius, colHeight
		super(259, 31, 51.8);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 806, 1); // Magic Obstacle
		addSkill(player, 807, 1); // Over-hit
		addSkill(player, 808, 1); // Golem Punch
		addSkill(player, 809, 1); // Golem Tornado Swing
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 806); // Magic Obstacle
		removeSkill(player, 807); // Over-hit
		removeSkill(player, 808); // Golem Punch
		removeSkill(player, 809); // Golem Tornado Swing
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DwarfGolem());
	}
}
