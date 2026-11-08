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
package com.l2jfree.loginserver.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.l2jfree.loginserver.beans.Accounts;
import com.l2jfree.loginserver.dao.AccountsDAO;

/**
 * DAO object for domain model class Accounts.
 * @see com.l2jfree.loginserver.beans.Accounts
 */
public class AccountsDAOMock implements AccountsDAO
{
	private final Map<String, Accounts> referential = new HashMap<String, Accounts>();
	
	public AccountsDAOMock()
	{
		referential.put("player1", new Accounts("player1", "UqW5IPUACYelC13kW52+69qJwxQ=", new BigDecimal(0),
				new Integer(100), 0, 1900, 1, 1, "127.0.0.1"));
		referential.put("player2", new Accounts("player2", "UqW5IPUACYelC13kW52+69qJwxQ=", new BigDecimal(0),
				new Integer(-1), 0, 1900, 1, 1, "127.0.0.2"));
	}
	
	/**
	 * Search by id
	 * @param id
	 * @return
	 */
	@Override
	public Accounts getAccountById(String id)
	{
		if (!referential.containsKey(id))
		{
			throw new ObjectRetrievalFailureException("Accounts", id);
		}
		return referential.get(id);
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#createAccount(java.lang.Object)
	 */
	@Override
	public String createAccount(Object obj)
	{
		Accounts acc = (Accounts)obj;
		referential.put(acc.getLogin(), acc);
		return acc.getLogin();
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#createOrUpdate(java.lang.Object)
	 */
	@Override
	public void createOrUpdate(Object obj)
	{
		createAccount(obj);
		
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#createOrUpdateAll(java.util.Collection)
	 */
	@Override
	public void createOrUpdateAll(Collection<?> entities)
	{
		Iterator<?> it = entities.iterator();
		while (it.hasNext())
		{
			createAccount(it.next());
		}
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#getAllAccounts()
	 */
	@Override
	public List<Accounts> getAllAccounts()
	{
		return new ArrayList<Accounts>(referential.values());
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#removeAccount(java.lang.Object)
	 */
	@Override
	public void removeAccount(Object obj)
	{
		referential.remove(((Accounts)obj).getLogin());
		
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#removeAccountById(java.io.Serializable)
	 */
	@Override
	public void removeAccountById(String login)
	{
		referential.remove(login);
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#removeAll(java.util.Collection)
	 */
	@Override
	public void removeAll(Collection<?> entities)
	{
		Iterator<?> it = entities.iterator();
		while (it.hasNext())
		{
			removeAccount(it.next());
		}
	}
	
	/**
	 * @see com.l2jfree.loginserver.dao.AccountsDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Object obj)
	{
		Accounts acc = (Accounts)obj;
		removeAccountById(acc.getLogin());
		createAccount(obj);
		
	}
}
