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
package com.l2jfree.tools.security;

import java.io.IOException;

import junit.framework.TestCase;

public class TestBlowFishEngine extends TestCase
{
	public void testAlgorithmName()
	{
		BlowfishEngine bf = new BlowfishEngine();
		assertEquals("Blowfish", bf.getAlgorithmName());
	}
	
	public void testBlockSize()
	{
		BlowfishEngine bf = new BlowfishEngine();
		assertEquals(8, bf.getBlockSize());
	}
	
	public void testProcessBlockWithTooSmallInputBuffer()
	{
		BlowfishEngine bf = new BlowfishEngine();
		byte[] byArray = new byte[] { (byte)0x12, (byte)0x0F, (byte)0xF0 };
		byte[] result = new byte[byArray.length];
		try
		{
			bf.init(true, byArray);
			bf.processBlock(byArray, 0, result, 0);
			fail("input buffer should be too small");
		}
		catch (IOException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().startsWith("input buffer"));
		}
	}
	
	public void testProcessBlockWithTooSmallOutputBuffer()
	{
		BlowfishEngine bf = new BlowfishEngine();
		byte[] byArray =
				new byte[] { (byte)0x12, (byte)0x0F, (byte)0xF0, (byte)0xF1, (byte)0x13, (byte)0x1F, (byte)0x12,
						(byte)0x00 };
		byte[] result = new byte[byArray.length];
		try
		{
			bf.init(true, byArray);
			bf.processBlock(byArray, 0, result, 3);
			fail("output buffer should be too small");
		}
		catch (IOException e)
		{
			assertNotNull(e);
			assertTrue(e.getMessage().startsWith("output buffer"));
		}
	}
	
	public void testProcessBlockEncrypt()
	{
		BlowfishEngine bf = new BlowfishEngine();
		byte[] byArray =
				new byte[] { (byte)0x12, (byte)0x0F, (byte)0xF0, (byte)0xF1, (byte)0x13, (byte)0x1F, (byte)0x12,
						(byte)0x00 };
		byte[] result = new byte[byArray.length];
		try
		{
			bf.init(true, byArray);
			bf.processBlock(byArray, 0, result, 0);
		}
		catch (IOException e)
		{
			fail("decryption should work");
		}
	}
	
	public void testProcessBlockDecrypt()
	{
		BlowfishEngine bf = new BlowfishEngine();
		byte[] byArray =
				new byte[] { (byte)0x12, (byte)0x0F, (byte)0xF0, (byte)0xF1, (byte)0x13, (byte)0x1F, (byte)0x12,
						(byte)0x00 };
		byte[] result = new byte[byArray.length];
		try
		{
			bf.init(false, byArray);
			bf.processBlock(byArray, 0, result, 0);
		}
		catch (IOException e)
		{
			fail("decryption should work");
		}
	}
	
	public void testProcessBlockNotInitialized()
	{
		BlowfishEngine bf = new BlowfishEngine();
		byte[] byArray =
				new byte[] { (byte)0x12, (byte)0x0F, (byte)0xF0, (byte)0xF1, (byte)0x13, (byte)0x1F, (byte)0x12,
						(byte)0x00 };
		byte[] result = new byte[byArray.length];
		try
		{
			bf.processBlock(byArray, 0, result, 0);
		}
		catch (IllegalStateException e)
		{
			assertNotNull(e);
		}
		catch (IOException e)
		{
			fail(e.getMessage());
		}
	}
	
}
