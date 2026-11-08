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
package com.l2jfree.gameserver.script;

import junit.framework.TestCase;

public class TestIntList extends TestCase
{
	public void testGetIntListWithDash()
	{
		int[] arrayInt = IntList.parse("4209-4125");
		assertNotNull(arrayInt);
		assertEquals(4209, arrayInt[0]);
		assertEquals(4125, arrayInt[1]);
	}
	
	public void testGetIntListWithComa()
	{
		int[] arrayInt = IntList.parse("4209,4125");
		assertNotNull(arrayInt);
		assertEquals(4209, arrayInt[0]);
		assertEquals(4125, arrayInt[1]);
	}
	
	public void testGetIntWithWrongFormat()
	{
		try
		{
			IntList.parse("4209 4125");
			fail("You should have an error of format");
		}
		catch (NumberFormatException e)
		{
			assertNotNull(e);
		}
	}
}
