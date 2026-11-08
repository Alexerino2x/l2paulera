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
package com.l2jfree.loginserver.dao;

import java.util.Collection;
import java.util.List;

import com.l2jfree.loginserver.beans.Gameservers;

/**
 * Data access object interface for gameservers
 * The interface define access functions to gameservers
 */
public interface GameserversDAO
{
	/**
	 * Return all objects related to the implementation of this DAO with no filter.
	 */
	public List<Gameservers> getAllGameservers();
	
	/**
	 * Persist the given transient instance, first assigning a generated identifier.
	 * (Or using the current value of the identifier property if the assigned generator is used.)
	 */
	public int createGameserver(Gameservers obj);
	
	/**
	 * Either save() or update() the given instance, depending upon the value of its
	 * identifier property.
	 */
	public void createOrUpdate(Gameservers obj);
	
	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param obj a transient instance containing updated state
	 */
	public void update(Object obj);
	
	/**
	 * Delete an object.
	 */
	public void removeGameserver(Gameservers obj);
	
	/**
	 * Search by id
	 * @param id the id
	 * @return the gameserver
	 */
	public Gameservers getGameserverByServerId(int id);
	
	/**
	 * Delete an object by id
	 */
	public void removeGameserverByServerId(int id);
	
	/**
	 * Delete a collection of object
	 */
	public void removeAll(Collection<?> entities);
	
	/**
	 * Delete all gameserver
	 */
	public void removeAll();
	
	/**
	 * Persist an entire collection
	 */
	public void createOrUpdateAll(Collection<?> entities);
	
}
