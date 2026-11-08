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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

public class TestDateRange extends TestCase
{
	public void testParse()
	{
		DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		String firstDate = "14 Feb 2005-16 Feb 2005";
		
		DateRange dr = DateRange.parse(firstDate, format);
		
		assertNotNull(dr.getEndDate());
		assertNotNull(dr.getStartDate());
		
		assertTrue(dr.isValid());
	}
	
	public void testBadParse()
	{
		DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		String firstDate = "14 F 2005-16 Fe  b 2005";
		
		DateRange dr = DateRange.parse(firstDate, format);
		
		assertTrue(!dr.isValid());
	}
	
	public void testWithinRange()
	{
		DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		String firstDate = "14 Feb 2005-16 Feb 2005";
		
		DateRange dr = DateRange.parse(firstDate, format);
		
		assertNotNull(dr.getEndDate());
		assertNotNull(dr.getStartDate());
		
		Date within = null;
		try
		{
			within = format.parse("15 Feb 2005");
		}
		catch (ParseException e)
		{
			fail(e.getMessage());
		}
		
		assertTrue(dr.isWithinRange(within));
	}
	
	public void testNotWithinRange()
	{
		DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		String firstDate = "14 Feb 2005-16 Feb 2005";
		
		DateRange dr = DateRange.parse(firstDate, format);
		
		assertNotNull(dr.getEndDate());
		assertNotNull(dr.getStartDate());
		
		Date within = null;
		try
		{
			within = format.parse("18 Feb 2005");
		}
		catch (ParseException e)
		{
			fail(e.getMessage());
		}
		
		assertTrue(!dr.isWithinRange(within));
	}
	
	public void testNotWithinRange2()
	{
		DateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		String firstDate = "14 Feb 2005-16 Feb 2005";
		
		DateRange dr = DateRange.parse(firstDate, format);
		
		assertNotNull(dr.getEndDate());
		assertNotNull(dr.getStartDate());
		
		Date within = null;
		try
		{
			within = format.parse("16 Feb 2005");
		}
		catch (ParseException e)
		{
			fail(e.getMessage());
		}
		
		assertTrue(!dr.isWithinRange(within));
	}
}
