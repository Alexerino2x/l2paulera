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

public class Gordon extends L2Transformation
{
	public Gordon()
	{
		// id, colRadius, colHeight
		super(308, 43, 46.6);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 728, 1); // Gordon Beast Attack
		addSkill(player, 729, 1); // Gordon Sword Stab
		addSkill(player, 730, 1); // Gordon Press
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 728); // Gordon Beast Attack
		removeSkill(player, 729); // Gordon Sword Stab
		removeSkill(player, 730); // Gordon Press
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Gordon());
	}
}
