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

public class RoyalGuardCaptain extends L2Transformation
{
	public RoyalGuardCaptain()
	{
		// id, colRadius, colHeight
		super(16, 12, 24);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 870, 1); // Royal Guard Captain Power Strike
		addSkill(player, 871, 1); // Royal Guard Captain Might
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 870); // Royal Guard Captain Power Strike
		removeSkill(player, 871); // Royal Guard Captain Might
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new RoyalGuardCaptain());
	}
}
