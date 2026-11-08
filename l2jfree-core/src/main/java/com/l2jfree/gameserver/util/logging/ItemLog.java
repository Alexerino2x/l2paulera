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
package com.l2jfree.gameserver.util.logging;

import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;

import javolution.text.TextBuilder;

import com.l2jfree.gameserver.model.items.L2ItemInstance;
import com.l2jfree.gameserver.model.items.templates.AbstractL2ItemType;
import com.l2jfree.gameserver.model.items.templates.L2EtcItemType;
import com.l2jfree.util.logging.L2LogFilter;
import com.l2jfree.util.logging.L2LogFormatter;

/**
 * @author NB4L1
 */
public final class ItemLog
{
	private ItemLog()
	{
	}
	
	public static final class Handler extends FileHandler
	{
		static
		{
			new File("log/item").mkdirs();
		}
		
		public Handler() throws IOException, SecurityException
		{
			super();
		}
	}
	
	public static final class Filter extends L2LogFilter
	{
		@Override
		protected String getLoggerName()
		{
			return "item";
		}
		
		private static final String EXCLUDED_PROCESSES = "Consume";
		
		private static final EnumSet<L2EtcItemType> EXCLUDED_ITEM_TYPES = EnumSet.of(L2EtcItemType.SHOT,
				L2EtcItemType.ARROW, L2EtcItemType.BOLT, L2EtcItemType.HERB);
		
		@Override
		public boolean isLoggable(LogRecord record)
		{
			if (!super.isLoggable(record))
				return false;
			
			if (record.getParameters() != null)
			{
				AbstractL2ItemType type = ((L2ItemInstance)record.getParameters()[0]).getItemType();
				if (EXCLUDED_ITEM_TYPES.contains(type))
					return false;
			}
			
			String[] messageList = record.getMessage().split(":");
			if (messageList.length >= 2 && EXCLUDED_PROCESSES.contains(messageList[1]))
				return false;
			
			return true;
		}
	}
	
	public static final class Formatter extends L2LogFormatter
	{
		@Override
		protected void format0(LogRecord record, TextBuilder tb)
		{
			appendDate(record, tb);
			appendMessage(record, tb);
			appendParameters(record, tb, ", ", true);
		}
	}
}
