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

public class FlyingFinalForm extends L2Transformation
{
	public FlyingFinalForm()
	{
		// id, colRadius, colHeight
		super(260, 9, 38);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 953, 1); // Life to Soul
		addSkill(player, 1545, 1); // Soul Sucking
		
		int level = player.getLevel() - 78;
		if (level > 0)
		{
			addSkill(player, 950, level); // Nail Attack
			addSkill(player, 951, level); // Wing Assault
			addSkill(player, 1544, level); // Death Beam
		}
		
		player.addTransformAllowedSkill(new int[] { 932 });
		
		player.setIsFlyingMounted(true);
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 953); // Life to Soul
		removeSkill(player, 1545);// Soul Sucking
		
		removeSkill(player, 950); // Nail Attack
		removeSkill(player, 951); // Wing Assault
		removeSkill(player, 1544); // Death Beam
		
		player.setIsFlyingMounted(false);
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new FlyingFinalForm());
	}
}
