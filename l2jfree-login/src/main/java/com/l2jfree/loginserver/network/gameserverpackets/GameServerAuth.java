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
package com.l2jfree.loginserver.network.gameserverpackets;

/**
 * @author -Wooden-
 */
public final class GameServerAuth extends GameToLoginPacket
{
	private final byte[] _hexId;
	private final int _desiredId;
	private final boolean _hostReserved;
	private final boolean _acceptAlternativeId;
	private final int _maxPlayers;
	private final int _port;
	private final String _gsNetConfig1;
	private final String _gsNetConfig2;
	
	public GameServerAuth(byte[] decrypt)
	{
		super(decrypt);
		_desiredId = readC();
		_acceptAlternativeId = (readC() != 0);
		_hostReserved = (readC() != 0);
		_gsNetConfig1 = readS();
		_gsNetConfig2 = readS();
		_port = readH();
		_maxPlayers = readD();
		int size = readD();
		_hexId = readB(size);
	}
	
	public byte[] getHexID()
	{
		return _hexId;
	}
	
	public boolean getHostReserved()
	{
		return _hostReserved;
	}
	
	public int getDesiredID()
	{
		return _desiredId;
	}
	
	public boolean acceptAlternateID()
	{
		return _acceptAlternativeId;
	}
	
	/**
	 * @return Returns the max players.
	 */
	public int getMaxPlayers()
	{
		return _maxPlayers;
	}
	
	/**
	 * @return Returns the gameserver netconfig string.
	 */
	public String getNetConfig()
	{
		String _netConfig = "";
		
		//	network configuration string formed on server
		if (_gsNetConfig1.contains(";") || _gsNetConfig1.contains(","))
		{
			_netConfig = _gsNetConfig1;
		}
		else
		// make network config string
		{
			if (_gsNetConfig2.length() > 0) // internal hostname and default internal networks
				_netConfig = _gsNetConfig2 + "," + "10.0.0.0/8,192.168.0.0/16" + ";";
			if (_gsNetConfig1.length() > 0) // external hostname and all avaible addresses by default
				_netConfig += _gsNetConfig1 + "," + "0.0.0.0/0" + ";";
		}
		
		return _netConfig;
	}
	
	/**
	 * @return Returns the port.
	 */
	public int getPort()
	{
		return _port;
	}
}
