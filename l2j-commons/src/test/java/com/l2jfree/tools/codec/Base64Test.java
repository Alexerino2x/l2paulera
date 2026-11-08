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
package com.l2jfree.tools.codec;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

/**
 * Class for Base64 testing
 * 
 */
public class Base64Test extends TestCase
{
	
	/**
	 * Test that decode an encoded string give the string in entry
	 */
	public final void testDecodeString()
	{
		String entrance = "This Server is running L2JFree";
		
		String entranceEncoded = Base64.encodeBytes(entrance.getBytes());
		
		String result = null;
		
		try
		{
			result = new String(Base64.decode(entranceEncoded), "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			fail(e.getMessage());
		}
		
		assertEquals(entrance, result);
	}
	
	/**
	 * Test that decode an encoded object give the same object
	 */
	public final void testDecodeObject()
	{
		String entrance = "This Server is running L2JFree";
		
		String entranceEncoded = Base64.encodeObject(entrance);
		
		String result = null;
		
		result = (String)Base64.decodeToObject(entranceEncoded);
		
		assertEquals(entrance, result);
	}
	
}
