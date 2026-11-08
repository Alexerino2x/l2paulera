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
package com.l2jfree.tools.math;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

public class TestScrambledKeyPair extends TestCase
{
	
	public void testSCrambleKeyPair()
	{
		KeyPairGenerator _keyGen = null;
		try
		{
			_keyGen = KeyPairGenerator.getInstance("RSA");
		}
		catch (NoSuchAlgorithmException e)
		{
			fail(e.getMessage());
			return;
		}
		KeyPair kp = _keyGen.generateKeyPair();
		ScrambledKeyPair skp1 = new ScrambledKeyPair(kp);
		ScrambledKeyPair skp2 = new ScrambledKeyPair(kp);
		byte[] bySkp1 = skp1.getScrambledModulus();
		byte[] bySkp2 = skp2.getScrambledModulus();
		
		assertEquals(bySkp1.length, bySkp2.length);
		// check that two byte array generated with the same key are the same
		for (int i = 0; i < bySkp1.length; i++)
		{
			assertEquals(bySkp1[i], bySkp2[i]);
		}
		
	}
}
