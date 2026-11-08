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
package com.l2jfree.tools.random;

import junit.framework.TestCase;

public class TestRnd extends TestCase
{
	public void testNextGaussian()
	{
		for (int i = 0; i < 50; i++)
		{
			double value = Rnd.nextGaussian();
			assertTrue("Value was " + value, value <= 10.0 && value >= -10.0);
		}
	}
	
	public void testInteger()
	{
		assertTrue(Rnd.nextInt(0) == 0);
		assertTrue(Rnd.nextInt(-600) <= 0);
		assertTrue(Rnd.get(-60, 50) <= 50 && Rnd.get(-60, 50) >= -60);
	}
}
