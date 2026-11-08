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
package com.l2jfree.gameserver.network.gameserverpackets;

import java.util.ArrayList;

import com.l2jfree.network.ServerStatusAttributes;

public final class ServerStatusPacket extends GameServerBasePacket
{
	private final ArrayList<Attribute> _attributes = new ArrayList<Attribute>();
	
	private static final class Attribute
	{
		public final int id;
		public final int value;
		
		private Attribute(ServerStatusAttributes pType, int pValue)
		{
			id = pType.ordinal();
			value = pValue;
		}
	}
	
	public ServerStatusPacket()
	{
		super(0x06);
	}
	
	public void addAttribute(ServerStatusAttributes type, int value)
	{
		_attributes.add(new Attribute(type, value));
	}
	
	public void addAttribute(ServerStatusAttributes type, boolean value)
	{
		addAttribute(type, value ? 0x01 : 0x00);
	}
	
	@Override
	public byte[] getContent()
	{
		writeD(_attributes.size());
		for (int i = 0; i < _attributes.size(); i++)
		{
			Attribute temp = _attributes.get(i);
			writeD(temp.id);
			writeD(temp.value);
		}
		
		return super.getContent();
	}
}
