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

public class TestSubNetHost extends TestCase
{
	public void testIsInNet()
	{
		SubNet net = new SubNet("170.0.0.0/32,10.0.0.0/8");
		assertNotNull(net);
		
		SubNetHost nethost = new SubNetHost("10.1.1.1");
		
		nethost.addSubNet(net);
		nethost.addSubNet("192.168.0.0", "16");
		assertTrue(nethost.isInSubnet("192.168.0.6"));
		assertFalse(nethost.isInSubnet("10.1.0.1"));
	}
}
