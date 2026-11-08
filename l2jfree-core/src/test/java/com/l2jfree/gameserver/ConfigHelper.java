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
package com.l2jfree.gameserver;

import com.l2jfree.Config;
import com.l2jfree.Config.IdFactoryType;

/**
 * This class is used to help creating a basic configuration by setting some default value
 *
 */
public class ConfigHelper
{
	/**
	 * Default configuration
	 */
	public static void configure()
	{
		// Set a increment id factory for test purpose
		Config.IDFACTORY_TYPE = IdFactoryType.Increment;
		Config.THREAD_POOL_SIZE = 50;
	}
}
