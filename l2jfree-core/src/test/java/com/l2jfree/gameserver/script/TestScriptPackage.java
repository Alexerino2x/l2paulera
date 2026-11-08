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

import java.io.IOException;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

public class TestScriptPackage extends TestCase
{
	public void testOpenScriptValid() throws IOException
	{
		ScriptPackage sp = null;
		String zipFile = this.getClass().getResource("Valentines.zip").getFile().replace("%20", " ");
		sp = new ScriptPackage(new ZipFile(zipFile));
		assertEquals(sp.getScriptFiles().size(), 1);
	}
	
	public void testOpenNotExistentScript()
	{
		try
		{
			new ScriptPackage(new ZipFile("unknownfile"));
			fail("File is found !");
		}
		catch (IOException e)
		{
			assertNotNull(e);
		}
	}
	
	public void testEmptyPackage() throws IOException
	{
		ScriptPackage sp = null;
		String zipFile = this.getClass().getResource("emptydata.zip").getFile().replace("%20", " ");
		sp = new ScriptPackage(new ZipFile(zipFile));
		assertEquals(sp.getScriptFiles().size(), 0);
	}
	
	public void testZipPackageWithInvalidScript() throws IOException
	{
		ScriptPackage sp = null;
		String zipFile = this.getClass().getResource("ValentinesNotValid.zip").getFile().replace("%20", " ");
		sp = new ScriptPackage(new ZipFile(zipFile));
		// We found 1 document. But the ScriptDocument does not contain any document
		assertEquals(sp.getScriptFiles().size(), 1);
		assertEquals(sp.getScriptFiles().get(0).getDocument(), null);
	}
}
