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
package com.l2jfree.tools.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * 
 * Test multisort class Is there really a need to test a class that we don't
 * use... ?
 * 
 */
public class TestMultiSort extends TestCase
{
	
	public void testCreateWithIntArray()
	{
		MultiSort ms = new MultiSort(new int[] { 5, 7, 1, 4, 10, 55, 32 });
		assertEquals(7, ms.getValues().size());
	}
	
	public void testCreateWithList()
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(5);
		list.add(7);
		list.add(1);
		list.add(4);
		list.add(10);
		list.add(55);
		list.add(32);
		MultiSort ms = new MultiSort(list);
		assertEquals(7, ms.getValues().size());
		
		assertEquals(0, ms.getKeys().size());
	}
	
	public void testCreateWithMap()
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("5", 5);
		map.put("7", 7);
		map.put("1", 1);
		map.put("4", 4);
		map.put("10", 10);
		map.put("55", 55);
		map.put("32", 32);
		MultiSort ms = new MultiSort(map);
		assertEquals(7, ms.getCount());
		// Here we should have keys
		assertEquals(7, ms.getKeys().size());
	}
	
	public void testHarmonicMean()
	{
		double harmonicMean = 7.0 / (1.0 / 5.0 + 1.0 / 7.0 + 1.0 + 1.0 / 4.0 + 1.0 / 10.0 + 1.0 / 55.0 + 1.0 / 32.0);
		MultiSort ms = new MultiSort(new int[] { 5, 7, 1, 4, 10, 55, 32 });
		assertEquals(harmonicMean, ms.getHarmonicMean());
	}
	
	public void testFrequency()
	{
		MultiSort ms = new MultiSort(new int[] { 5, 5, 1, 4, 10, 55, 32 });
		assertEquals(2, ms.getFrequency(5));
	}
	
	public void testMaxValue()
	{
		MultiSort ms = new MultiSort(new int[] { 5, 5, 1, 4, 10, 55, 32 });
		assertEquals(55, ms.getMaxValue());
	}
	
	public void testMinValue()
	{
		MultiSort ms = new MultiSort(new int[] { 5, 5, 1, 4, 10, 55, 32 });
		assertEquals(1, ms.getMinValue());
	}
	
	public void testMean()
	{
		MultiSort ms = new MultiSort(new int[] { 0, 5 });
		assertEquals(2.5, ms.getMean());
	}
	
	public void testSumOfValue()
	{
		MultiSort ms = new MultiSort(new int[] { 0, 5, 10, 100 });
		assertEquals(115, ms.getTotalValue());
	}
	
	public void testSortDescending()
	{
		MultiSort ms = new MultiSort(new int[] { 0, 5, 10, 100 });
		ms.setSortDescending(true);
		assertTrue(ms.isSortDescending());
		assertTrue(!ms.isSorted());
		assertTrue(ms.sort());
		assertTrue(ms.isSorted());
		
		List<Integer> list = ms.getValues();
		int previousValue = -1;
		for (int value : list)
		{
			assertTrue(value > previousValue);
		}
	}
	
	public void testSortAscending()
	{
		MultiSort ms = new MultiSort(new int[] { 0, 5, 10, 100 });
		ms.setSortDescending(false);
		assertTrue(!ms.isSortDescending());
		assertTrue(!ms.isSorted());
		assertTrue(ms.sort());
		assertTrue(ms.isSorted());
		
		List<Integer> list = ms.getValues();
		int previousValue = 101;
		for (int value : list)
		{
			assertTrue(value < previousValue);
		}
	}
}
