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
package com.l2jfree.tools.geometry;

import junit.framework.TestCase;

/**
 * Class for Point3D testing
 * 
 */
public class Point3DTest extends TestCase
{
	
	/**
	 * Test that two points is equal subtility : pointDefaultZ is construct
	 * without z, z is 0 by default
	 */
	public final void testEquality()
	{
		Point3D pointDefaultz = new Point3D(1, 1);
		
		Point3D point = new Point3D(1, 1, 0);
		
		assertEquals(point, pointDefaultz);
		
	}
	
	/**
	 * Test squared functions
	 * 
	 */
	public final void testSquaredFunctions()
	{
		Point3D pointA = new Point3D(1, 1, 1);
		Point3D pointB = new Point3D(2, 2, 1);
		Point3D pointC = new Point3D(5, 10, 1);
		
		assertEquals(Point3D.distanceSquared(pointA, pointB), 2);
		
		// Static and non static function should return the same
		assertEquals(Point3D.distanceSquared(pointA, pointC), 97);
		assertEquals(pointA.distanceSquaredTo(pointC), Point3D.distanceSquared(pointA, pointC));
		
	}
	
	/**
	 * Test distance functions
	 * 
	 */
	public final void testDistanceLess()
	{
		Point3D pointA = new Point3D(1, 1, 1);
		Point3D pointB = new Point3D(6, 6, 1);
		
		assertEquals(Point3D.distanceSquared(pointA, pointB), 50);
		assertTrue(Point3D.distanceLessThan(pointA, pointB, 60));
	}
	
}
