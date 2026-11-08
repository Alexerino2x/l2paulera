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

public class FortressCaptain extends L2Transformation
{
	public FortressCaptain()
	{
		// id, colRadius, colHeight
		super(21, 14, 27);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 891, 1); // Fortress Captain Twister
		addSkill(player, 892, 1); // Fortress Captain Blaze
		addSkill(player, 893, 1); // Fortress Captain Seal of Binding
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 891); // Fortress Captain Twister
		removeSkill(player, 892); // Fortress Captain Blaze
		removeSkill(player, 893); // Fortress Captain Seal of Binding
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new FortressCaptain());
	}
}
