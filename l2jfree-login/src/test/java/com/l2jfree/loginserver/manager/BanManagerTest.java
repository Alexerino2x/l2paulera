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
package com.l2jfree.loginserver.manager;

import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import junit.framework.TestCase;

/**
 * This class test ban management
 * 
 */
public class BanManagerTest extends TestCase
{
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		BanManager.BAN_LIST = getClass().getResource("banlist.cfg").getFile().replace("%20", " ");
		
		// intialize a file for the test
		FileWriter fw = new FileWriter(BanManager.BAN_LIST);
		fw.write("#comment\n");
		fw.write("\n127.0.0.1 " + System.currentTimeMillis() + 10000);
		fw.write("\n192.168.0.1 " + System.currentTimeMillis() + 10000);
		fw.write("\n176.12.12.12 " + System.currentTimeMillis() + 10000);
		fw.write("\n132.12.12.12 ");
		fw.close();
		
		BanManager.getInstance();
	}
	
	public void testLoadBanList() throws UnknownHostException
	{
		BanManager bm = BanManager.getInstance();
		assertEquals(4, bm.getEternalBanCount() + bm.getTempBanCount());
		InetAddress netAddress = InetAddress.getByName("127.0.0.1");
		assertTrue(bm.isBannedAddress(netAddress));
	}
	
	public void testUnBan() throws UnknownHostException
	{
		BanManager bm = BanManager.getInstance();
		InetAddress netAddress = InetAddress.getByName("127.0.0.1");
		assertTrue(bm.isBannedAddress(netAddress));
		
		bm.removeBanForAddress("127.0.0.1");
		assertTrue(!bm.isBannedAddress(netAddress));
	}
	
	public void testEternalBan() throws UnknownHostException
	{
		BanManager bm = BanManager.getInstance();
		InetAddress netAddress = InetAddress.getByName("132.12.12.12");
		assertTrue(bm.isBannedAddress(netAddress));
	}
	
	public void testBanIp() throws Exception
	{
		BanManager bm = BanManager.getInstance();
		InetAddress netAddress = InetAddress.getByName("127.0.0.1");
		if (bm.isBannedAddress(netAddress))
		{
			bm.removeBanForAddress("127.0.0.1");
		}
		
		bm.addBanForAddress(netAddress, 1000);
		assertTrue(bm.isBannedAddress(netAddress));
		Thread.sleep(2000);
		// check that account is unban
		assertTrue(!bm.isBannedAddress(netAddress));
	}
}
