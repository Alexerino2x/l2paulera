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
package com.l2jfree;

/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mchange.v2.c3p0.PooledDataSource;

/**
 * 
 * Object registry for L2 LS.
 * 
 * The registry store singleton and is able to act as a factory.
 * All singleton and factory are declared in spring.xml
 * 
 * There is no risk to call the load method more than one time.
 * The first call initialize all singleton by IoC mechanism.
 * 
 */
public class L2Registry
{
	private static final Log _log = LogFactory.getLog(L2Registry.class);
	
	private static ApplicationContext __ctx = null;
	
	/**
	 * Load registry from spring
	 * The registry is a facade behind ApplicationContext from spring.
	 */
	public static void loadRegistry(String[] paths)
	{
		try
		{
			// Load the context if it is not already loaded
			if (__ctx == null)
			{
				// init properties for spring
				__ctx = new ClassPathXmlApplicationContext(paths);
			}
		}
		catch (RuntimeException e)
		{
			throw new Error("Unable to load registry, check that you update xml file in config folder !", e);
		}
	}
	
	public static void loadRegistry(String path)
	{
		loadRegistry(new String[] { path });
	}
	
	/**
	 * Retrieve a bean from registry
	 * @param bean - the bean name
	 * @return the Object
	 */
	public static Object getBean(String bean)
	{
		if (__ctx == null)
		{
			_log.fatal("Registry was not initialized.");
			return null;
		}
		try
		{
			Object o = __ctx.getBean(bean);
			return o;
		}
		catch (NoSuchBeanDefinitionException e)
		{
			_log.fatal("No such bean (" + bean + ") in context." + e.getMessage(), e);
			return null;
		}
		catch (BeansException e)
		{
			_log.fatal("Unable to load bean : " + bean + " = " + e.getMessage(), e);
			return null;
		}
		
	}
	
	// =========================================================
	// Data Field
	private static L2Registry _instance;
	
	// =========================================================
	// Constructor
	private L2Registry()
	{
	}
	
	// =========================================================
	// Method - Public
	public final String prepQuerySelect(String[] fields, String tableName, String whereClause,
			boolean returnOnlyTopRecord)
	{
		String msSqlTop1 = "";
		String mySqlTop1 = "";
		if (returnOnlyTopRecord)
		{
			mySqlTop1 = " Limit 1 ";
		}
		String query =
				"SELECT " + msSqlTop1 + safetyString(fields) + " FROM " + tableName + " WHERE " + whereClause
						+ mySqlTop1;
		return query;
	}
	
	public final String safetyString(String[] whatToCheck)
	{
		// NOTE: Use brace as a safty percaution just incase name is a reserved word
		String braceLeft = "`";
		String braceRight = "`";
		
		String result = "";
		for (String word : whatToCheck)
		{
			if (result != "")
				result += ", ";
			result += braceLeft + word + braceRight;
		}
		return result;
	}
	
	// =========================================================
	// Property - Public
	public static L2Registry getInstance()
	{
		if (_instance == null)
		{
			_instance = new L2Registry();
		}
		return _instance;
	}
	
	/**
	 * if con is not null, return the same connection
	 * dev have to close it !
	 * @param con
	 * @return
	 */
	public static Connection getConnection(Connection con)
	{
		if (con == null)
		{
			try
			{
				con = ((DataSource)__ctx.getBean("dataSource")).getConnection();
			}
			catch (BeansException e)
			{
				_log.fatal("Unable to retrieve connection : " + e.getMessage(), e);
			}
			catch (SQLException e)
			{
				_log.fatal("Unable to retrieve connection : " + e.getMessage(), e);
			}
		}
		return con;
	}
	
	public static ApplicationContext getApplicationContext()
	{
		return __ctx;
	}
	
	/**
	 * Give ability to overload application context (for test purpose)
	 * @param ctx
	 */
	public static void setApplicationContext(ApplicationContext ctx)
	{
		__ctx = ctx;
	}
	
	public int getBusyConnectionCount() throws SQLException
	{
		return ((PooledDataSource)__ctx.getBean("dataSource")).getNumBusyConnectionsDefaultUser();
	}
	
	public int getIdleConnectionCount() throws SQLException
	{
		return ((PooledDataSource)__ctx.getBean("dataSource")).getNumIdleConnectionsDefaultUser();
	}
	
}
