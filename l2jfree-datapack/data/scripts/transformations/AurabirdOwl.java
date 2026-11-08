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

public class AurabirdOwl extends L2Transformation
{
	public AurabirdOwl()
	{
		// id, colRadius, colHeight
		super(9, 40, 18.57);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		// Air Blink
		if (player.getLevel() >= 75)
			addSkill(player, 885, 1);
		
		// Exhilarate
		if (player.getLevel() >= 83)
			addSkill(player, 895, 1);
		
		int level = player.getLevel() - 74;
		if (level > 0)
		{
			addSkill(player, 884, level); // Air Assault
			addSkill(player, 887, level); // Sky Clutch
			addSkill(player, 889, level); // Energy Storm
			addSkill(player, 892, level); // Energy Shot
			addSkill(player, 893, level); // Concentrated Energy Shot
			addSkill(player, 911, level); // Energy Burst
		}
		
		player.addTransformAllowedSkill(new int[] { 932 });
		
		player.setIsFlyingMounted(true);
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 885); // Air Blink
		
		removeSkill(player, 895); // Exhilarate
		
		removeSkill(player, 884); // Air Assault
		removeSkill(player, 887); // Sky Clutch
		removeSkill(player, 889); // Energy Storm
		removeSkill(player, 892); // Energy Shot
		removeSkill(player, 893); // Concentrated Energy Shot
		removeSkill(player, 911); // Energy Burst
		
		player.setIsFlyingMounted(false);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new AurabirdOwl());
	}
}
