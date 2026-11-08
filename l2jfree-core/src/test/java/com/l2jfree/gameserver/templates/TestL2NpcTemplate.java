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
package com.l2jfree.gameserver.templates;

import junit.framework.TestCase;

import com.l2jfree.gameserver.gameobjects.templates.L2NpcTemplate;
import com.l2jfree.gameserver.gameobjects.templates.L2NpcTemplate.Race;

public class TestL2NpcTemplate extends TestCase
{
	
	/**
	 * fastidious test but necessary to test all setters
	 * @throws ClassNotFoundException
	 *
	 */
	public void testCreationWithStatSet() throws ClassNotFoundException
	{
		StatsSet set = new StatsSet();
		set.set("baseSTR", 1);
		set.set("baseCON", 2);
		set.set("baseDEX", 3);
		set.set("baseINT", 4);
		set.set("baseWIT", 5);
		set.set("baseMEN", 6);
		set.set("baseHpMax", 7);
		set.set("baseCpMax", 8);
		set.set("baseMpMax", 9);
		set.set("baseHpReg", 10);
		set.set("baseMpReg", 11);
		set.set("basePAtk", 12);
		set.set("baseMAtk", 13);
		set.set("basePDef", 14);
		set.set("baseMDef", 15);
		set.set("basePAtkSpd", 16);
		set.set("baseMAtkSpd", 17);
		set.set("baseShldDef", 19);
		set.set("baseAtkRange", 20);
		set.set("baseShldRate", 21);
		set.set("baseCritRate", 22);
		set.set("baseRunSpd", 23);
		set.set("baseWalkSpd", 23);
		
		// Geometry
		set.set("collision_radius", 56.0);
		set.set("collision_height", 57.0);
		set.set("fcollision_radius", 56.0);
		set.set("fcollision_height", 57.0);
		
		set.set("npcId", 60);
		set.set("idTemplate", 61);
		set.set("type", "npc");
		set.set("name", "npc1");
		set.set("serverSideName", "true");
		set.set("title", "title");
		set.set("serverSideTitle", "true");
		set.set("sex", 1);
		set.set("level", 63);
		set.set("rewardExp", 64);
		set.set("rewardSp", 65);
		set.set("aggroRange", 66);
		set.set("rhand", 67);
		set.set("lhand", 68);
		set.set("armor", 69);
		set.set("factionId", "faction id");
		set.set("factionRange", 70);
		set.set("absorb_level", 71);
		set.set("absorb_type", "LAST_HIT");
		set.set("NPCFaction", 72);
		set.set("NPCFactionName", "faction name");
		set.set("jClass", "class");
		
		L2NpcTemplate template = new L2NpcTemplate(set);
		assertNotNull(template);
		
		assertEquals(60, template.getNpcId());
		assertEquals(61, template.getIdTemplate());
		assertEquals("npc", template.getType());
		assertEquals("npc1", template.getName());
		assertEquals(true, template.isServerSideName());
		assertEquals("title", template.getTitle());
		assertEquals(true, template.isServerSideTitle());
		assertEquals("1", template.getSex());
		assertEquals(63, template.getLevel());
		assertEquals(64, template.getRewardExp());
		assertEquals(65, template.getRewardSp());
		assertEquals(66, template.getAggroRange());
		assertEquals(67, template.getRhand());
		assertEquals(68, template.getLhand());
		assertEquals(69, template.getArmor());
		assertEquals("faction id", template.getFactionId());
		assertEquals(70, template.getFactionRange());
		assertEquals(71, template.getAbsorbLevel());
		assertEquals(72, template.getNpcFaction());
		assertEquals("faction name", template.getNpcFactionName());
		assertEquals("class", template.getJClass());
		assertEquals(Race.NONE, template.getRace());
		
	}
	
	/**
	 * fastidious test but necessary to test all setters
	 * @throws ClassNotFoundException
	 *
	 */
	public void testCreationWithDefaultValueForSomeStats() throws ClassNotFoundException
	{
		StatsSet set = new StatsSet();
		set.set("baseSTR", 1);
		set.set("baseCON", 2);
		set.set("baseDEX", 3);
		set.set("baseINT", 4);
		set.set("baseWIT", 5);
		set.set("baseMEN", 6);
		set.set("baseHpMax", 7);
		set.set("baseCpMax", 8);
		set.set("baseMpMax", 9);
		set.set("baseHpReg", 10);
		set.set("baseMpReg", 11);
		set.set("basePAtk", 12);
		set.set("baseMAtk", 13);
		set.set("basePDef", 14);
		set.set("baseMDef", 15);
		set.set("basePAtkSpd", 16);
		set.set("baseMAtkSpd", 17);
		set.set("baseShldDef", 19);
		set.set("baseAtkRange", 20);
		set.set("baseShldRate", 21);
		set.set("baseCritRate", 22);
		set.set("baseRunSpd", 23);
		set.set("baseWalkSpd", 23);
		// Geometry
		set.set("collision_radius", 56.0);
		set.set("collision_height", 57.0);
		set.set("fcollision_radius", 56.0);
		set.set("fcollision_height", 57.0);
		
		set.set("npcId", 60);
		set.set("idTemplate", 61);
		set.set("type", "npc");
		set.set("name", "npc1");
		set.set("serverSideName", "true");
		set.set("title", "title");
		set.set("serverSideTitle", "true");
		set.set("sex", 1);
		set.set("level", 63);
		set.set("rewardExp", 64);
		set.set("rewardSp", 65);
		set.set("aggroRange", 66);
		set.set("rhand", 67);
		set.set("lhand", 68);
		set.set("armor", 69);
		set.set("factionRange", 70);
		set.set("absorb_type", "LAST_HIT");
		set.set("jClass", "class");
		
		L2NpcTemplate template = new L2NpcTemplate(set);
		assertNotNull(template);
		
		assertEquals(null, template.getFactionId());
		assertEquals(0, template.getAbsorbLevel());
		assertEquals("Devine Clan", template.getNpcFactionName());
		assertEquals(Race.NONE, template.getRace());
	}
}
