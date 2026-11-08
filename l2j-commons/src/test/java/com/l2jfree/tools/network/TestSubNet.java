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
package com.l2jfree.tools.network;

import junit.framework.TestCase;

public class TestSubNet extends TestCase
{
	
	public void testCreateWithIP()
	{
		SubNet net = new SubNet("127.0.0.1");
		assertNotNull(net);
	}
	
	public void testCreateWithIPAndMask()
	{
		SubNet net = new SubNet("192.168.0.0/16");
		assertNotNull(net);
	}
	
	public void testEquals()
	{
		SubNet net1 = new SubNet("192.168.0.0/16");
		assertNotNull(net1);
		
		SubNet net2 = new SubNet("192.168.0.0/255.255.0.0");
		assertNotNull(net2);
		
		assertTrue(net1.equals(net2));
	}
	
	public void testIsInNet()
	{
		SubNet net = new SubNet("192.168.0.0/16");
		assertNotNull(net);
		assertTrue(net.isInSubnet("192.168.0.6"));
		assertFalse(net.isInSubnet("10.1.0.1"));
		assertFalse(net.isInSubnet("192.0.0.1"));
	}
}
