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

import java.io.InputStream;

import junit.framework.TestCase;

public class TestScriptDocument extends TestCase
{
	public void testParseValidDocument()
	{
		InputStream ip = getClass().getResourceAsStream("Valentines.xml");
		
		ScriptDocument sd = new ScriptDocument("test", ip);
		
		assertNotNull(sd.getDocument());
		assertEquals("test", sd.getName());
	}
	
	public void testParseInexistantDocument()
	{
		InputStream ip = getClass().getResourceAsStream("notexistant.xml");
		
		ScriptDocument sd = new ScriptDocument("test", ip);
		
		assertNull(sd.getDocument());
		assertNull(sd.getName());
	}
	
	public void testParseNotValidDocument()
	{
		InputStream ip = getClass().getResourceAsStream("ValentinesNotValid.xml");
		
		ScriptDocument sd = new ScriptDocument("test", ip);
		
		assertNull(sd.getDocument());
		assertEquals("test", sd.toString());
	}
	
}
