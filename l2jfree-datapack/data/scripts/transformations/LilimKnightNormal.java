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

public class LilimKnightNormal extends L2Transformation
{
	public LilimKnightNormal()
	{
		// id, colRadius, colHeight
		super(208, 12, 25.5);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 568, 3); // Attack Buster
		addSkill(player, 569, 3); // Attack Storm
		addSkill(player, 570, 3); // Attack Rage
		addSkill(player, 571, 3); // Poison Dust
		
		player.clearCharges();
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 568); // Attack Buster
		removeSkill(player, 569); // Attack Storm
		removeSkill(player, 570); // Attack Rage
		removeSkill(player, 571); // Poison Dust
		
		player.clearCharges();
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new LilimKnightNormal());
	}
}
