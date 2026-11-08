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

public class DivineSummoner extends L2Transformation
{
	public DivineSummoner()
	{
		// id, colRadius, colHeight
		super(258, 10, 25);
	}
	
	@Override
	public void transformedSkills(L2Player player)
	{
		addSkill(player, 710, 1); // Divine Summoner Summon Divine Beast
		addSkill(player, 711, 1); // Divine Summoner Transfer Pain
		addSkill(player, 712, 1); // Divine Summoner Final Servitor
		addSkill(player, 713, 1); // Divine Summoner Servitor Hill
		addSkill(player, 714, 1); // Sacrifice Summoner
	}
	
	@Override
	public void removeSkills(L2Player player)
	{
		removeSkill(player, 710); // Divine Summoner Summon Divine Beast
		removeSkill(player, 711); // Divine Summoner Transfer Pain
		removeSkill(player, 712); // Divine Summoner Final Servitor
		removeSkill(player, 713); // Divine Summoner Servitor Hill
		removeSkill(player, 714); // Sacrifice Summoner
	}
	
	public static void main(String[] args)
	{
		TransformationManager.getInstance().registerTransformation(new DivineSummoner());
	}
}
