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

public class Anakim extends L2Transformation
{
	public Anakim()
	{
		// id, colRadius, colHeight
		super(306, 15.5, 29);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 720, 2); // Anakim Holy Light Burst
		addSkill(player, 721, 2); // Anakim Energy Attack
		addSkill(player, 722, 2); // Anakim Holy Beam
		addSkill(player, 723, 1); // Anakim Sunshine
		addSkill(player, 724, 1); // Anakim Cleanse
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 720); // Anakim Holy Light Burst
		removeSkill(player, 721); // Anakim Energy Attack
		removeSkill(player, 722); // Anakim Holy Beam
		removeSkill(player, 723); // Anakim Sunshine
		removeSkill(player, 724); // Anakim Cleanse
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new Anakim());
	}
}
