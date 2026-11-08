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

public class DivineWizard extends L2Transformation
{
	public DivineWizard()
	{
		// id, colRadius, colHeight
		super(256, 10, 26);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 692, 1); // Divine Wizard Holy Flare
		addSkill(player, 693, 1); // Divine Wizard Holy Strike
		addSkill(player, 694, 1); // Divine Wizard Holy Curtain
		addSkill(player, 695, 1); // Divine Wizard Holy Cloud
		addSkill(player, 696, 1); // Divine Wizard Surrender to Holy
		addSkill(player, 697, 1); // Sacrifice Wizard
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 692); // Divine Wizard Holy Flare
		removeSkill(player, 693); // Divine Wizard Holy Strike
		removeSkill(player, 694); // Divine Wizard Holy Curtain
		removeSkill(player, 695); // Divine Wizard Holy Cloud
		removeSkill(player, 696); // Divine Wizard Surrender to Holy
		removeSkill(player, 697); // Sacrifice Wizard
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineWizard());
	}
}
