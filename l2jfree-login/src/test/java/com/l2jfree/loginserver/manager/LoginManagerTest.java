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

import java.io.IOException;
import java.net.InetAddress;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.l2jfree.Config;
import com.l2jfree.L2Registry;
import com.l2jfree.loginserver.beans.SessionKey;
import com.l2jfree.loginserver.services.exception.AccountBannedException;
import com.l2jfree.loginserver.services.exception.AccountWrongPasswordException;

/**
 * This class test ban management
 * 
 */
public class LoginManagerTest extends TestCase
{
	private ClassPathXmlApplicationContext context = null;
	private LoginManager loginManager;
	
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		context = new ClassPathXmlApplicationContext("classpath*:/**/**/applicationContext-TestMock.xml");
		L2Registry.setApplicationContext(context);
		
		loginManager = LoginManager.getInstance();
	}
	
	/**
	 *
	 */
	public void testAssignSessionKey()
	{
		SessionKey sk = loginManager.assignSessionKeyToLogin("player1", null);
		assertNotNull(sk);
		
		assertTrue(loginManager.isAccountInLoginServer("player1"));
	}
	
	/**
	 *
	 */
	public void testRemoveAccount()
	{
		loginManager.assignSessionKeyToLogin("player1", null);
		
		loginManager.removeAuthedLoginClient("player1");
		
		assertTrue(!loginManager.isAccountInLoginServer("player1"));
		assertNull(loginManager.getKeyForAccount("player1"));
		
	}
	
	public void testAccountBanned() throws Exception
	{
		Config.LOGIN_TRY_BEFORE_BAN = 3;
		
		InetAddress netAddress = InetAddress.getByName("123.123.123.123");
		try
		{
			loginManager.loginValid("player2", "testpwd", netAddress);
			fail("the user is banned should fail");
		}
		catch (AccountBannedException e)
		{
			assertNotNull(e.getMessage(), e);
		}
	}
	
	public void testConnection() throws Exception
	{
		Config.LOGIN_TRY_BEFORE_BAN = 3;
		
		InetAddress netAddress = InetAddress.getByName("123.123.123.123");
		try
		{
			loginManager.loginValid("player1", "testpwd", netAddress);
			fail("the password should fail");
		}
		catch (AccountWrongPasswordException e)
		{
			assertNotNull(e.getMessage(), e);
		}
		
		try
		{
			assertTrue(loginManager.loginValid("player1", "testpwd1", netAddress));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
	
	public void testHackingAttempt() throws IOException
	{
		Config.LOGIN_TRY_BEFORE_BAN = 3;
		InetAddress netAddress = InetAddress.getByName("123.123.123.123");
		
		try
		{
			// First try, failed connect = 1
			assertFalse(loginManager.loginValid("player1", "testpwd", netAddress));
			assertFalse(BanManager.getInstance().isBannedAddress(netAddress));
			// 2nd try, failed connect = 2
			assertFalse(loginManager.loginValid("player1", "testpwd2", netAddress));
			assertFalse(BanManager.getInstance().isBannedAddress(netAddress));
			// 3rd try, failed connect = 3 => ban ip
			assertFalse(loginManager.loginValid("player1", "testpwd3", netAddress));
			assertTrue(BanManager.getInstance().isBannedAddress(netAddress));
		}
		catch (Exception e)
		{
			assertNotNull(e);
		}
		// don't forget to unban client to avoid perturbation on other tests
		BanManager.getInstance().removeBanForAddress(netAddress.getHostAddress());
	}
	
	public void testLoginWithNullAdress() throws Exception
	{
		InetAddress address = null;
		Config.AUTO_CREATE_ACCOUNTS = false;
		assertFalse(loginManager.loginValid("unknownplayer", "pwdforplayer", address));
		Config.AUTO_CREATE_ACCOUNTS = true;
		assertTrue(loginManager.loginValid("unknownplayer", "pwdforplayer", address));
	}
	
	public void testAutoCreateAccount() throws IOException
	{
		Config.AUTO_CREATE_ACCOUNTS = true;
		InetAddress netAddress = InetAddress.getByName("123.123.123.123");
		
		try
		{
			assertTrue(loginManager.loginValid("unknownplayer", "pwdforplayer", netAddress));
		}
		catch (Exception e)
		{
			fail(e.getMessage());
		}
	}
	
}
