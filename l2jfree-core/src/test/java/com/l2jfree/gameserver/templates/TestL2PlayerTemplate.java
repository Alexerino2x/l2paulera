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

import com.l2jfree.gameserver.gameobjects.templates.L2PlayerTemplate;

public class TestL2PlayerTemplate extends TestCase
{
	
	/**
	 * fastidious test but necessary to test all setters
	 *
	 */
	public void testCreationWithStatSet()
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
		
		set.set("classId", 60);
		set.set("raceId", 4);
		set.set("className", "classname");
		/*
		set.set("spawnX",62);
		set.set("spawnY",63);
		set.set("spawnZ",64);
		*/
		set.set("classBaseLevel", 65);
		set.set("lvlHpAdd", 66.f);
		set.set("lvlHpMod", 67.f);
		set.set("lvlCpAdd", 68.f);
		set.set("lvlCpMod", 69.f);
		set.set("lvlMpAdd", 70.f);
		set.set("lvlMpMod", 71.f);
		
		L2PlayerTemplate template = new L2PlayerTemplate(set);
		assertNotNull(template);
		
		assertEquals("dummyEntry3", template.getClassId().name());
		assertEquals("Dwarf", template.getRace().name());
		assertEquals("classname", template.getClassName());
		/*
		assertEquals(62,template.getSpawnX());
		assertEquals(63,template.getSpawnY());
		assertEquals(64,template.getSpawnZ());
		*/
		assertEquals(65, template.getClassBaseLevel());
		assertEquals(66.f, template.getLvlHpAdd());
		assertEquals(67.f, template.getLvlHpMod());
		assertEquals(68.f, template.getLvlCpAdd());
		assertEquals(69.f, template.getLvlCpMod());
		assertEquals(70.f, template.getLvlMpAdd());
		assertEquals(71.f, template.getLvlMpMod());
	}
}
