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
package com.l2jfree.gameserver.instancemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.l2jfree.L2DatabaseFactory;
import com.l2jfree.gameserver.datatables.NpcTable;
import com.l2jfree.gameserver.gameobjects.L2Boss;
import com.l2jfree.gameserver.gameobjects.instance.L2GrandBossInstance;
import com.l2jfree.gameserver.gameobjects.templates.L2NpcTemplate;
import com.l2jfree.gameserver.model.world.spawn.L2Spawn;
import com.l2jfree.gameserver.templates.StatsSet;

/**
 * @author Crion/kombat
 */
public class GrandBossSpawnManager extends BossSpawnManager
{
	public static GrandBossSpawnManager getInstance()
	{
		return SingletonHolder._instance;
	}
	
	@Override
	protected void init()
	{
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection(con);
			
			PreparedStatement statement = con.prepareStatement("SELECT * from grandboss_spawnlist ORDER BY boss_id");
			ResultSet rset = statement.executeQuery();
			
			L2Spawn spawnDat;
			L2NpcTemplate template;
			
			while (rset.next())
			{
				template = getValidTemplate(rset.getInt("boss_id"));
				if (template != null)
				{
					spawnDat = new L2Spawn(template);
					spawnDat.setLocx(rset.getInt("loc_x"));
					spawnDat.setLocy(rset.getInt("loc_y"));
					spawnDat.setLocz(rset.getInt("loc_z"));
					spawnDat.setHeading(rset.getInt("heading"));
					spawnDat.setRespawnMinDelay(rset.getInt("respawn_min_delay"));
					spawnDat.setRespawnMaxDelay(rset.getInt("respawn_max_delay"));
					spawnDat.setAmount(1);
					
					addNewSpawn(spawnDat, rset.getLong("respawn_time"), rset.getDouble("currentHp"),
							rset.getDouble("currentMp"), false);
				}
				else
				{
					_log.warn("GrandBossSpawnManager: Could not load grandboss #" + rset.getInt("boss_id") + " from DB");
				}
			}
			
			_log.info("GrandBossSpawnManager: Loaded " + _bosses.size() + " Instances");
			_log.info("GrandBossSpawnManager: Scheduled " + _schedules.size() + " Instances");
			
			rset.close();
			statement.close();
		}
		catch (SQLException e)
		{
			_log.warn("GrandBossSpawnManager: Couldnt load grandboss_spawnlist table");
		}
		catch (Exception e)
		{
			_log.error(e.getMessage(), e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	@Override
	protected void insertIntoDb(L2Spawn spawnDat, long respawnTime, double currentHP, double currentMP)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection(con);
			PreparedStatement statement =
					con.prepareStatement("INSERT INTO grandboss_spawnlist (boss_id,loc_x,loc_y,loc_z,heading,respawn_time,currentHp,currentMp) values(?,?,?,?,?,?,?,?)");
			statement.setInt(1, spawnDat.getNpcId());
			statement.setInt(2, spawnDat.getLocx());
			statement.setInt(3, spawnDat.getLocy());
			statement.setInt(4, spawnDat.getLocz());
			statement.setInt(5, spawnDat.getHeading());
			statement.setLong(6, respawnTime);
			statement.setDouble(7, currentHP);
			statement.setDouble(8, currentMP);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			// Problem with storing spawn
			_log.warn("GrandBossSpawnManager: Could not store grand boss #" + spawnDat.getNpcId() + " in the DB:", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	@Override
	public void updateSpawn(int bossId, int x, int y, int z, int h)
	{
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection(con);
			PreparedStatement statement =
					con.prepareStatement("UPDATE grandboss_spawnlist SET loc_x = ?, loc_y = ?, loc_z = ?, heading = ? WHERE boss_id=?");
			statement.setInt(1, x);
			statement.setInt(2, y);
			statement.setInt(3, z);
			statement.setInt(4, h);
			statement.setInt(5, bossId);
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warn("GrandBossSpawnManager: Could not update raidboss #" + bossId + " in DB: ", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	@Override
	protected void deleteFromDb(L2Spawn spawnDat, int bossId)
	{
		Connection con = null;
		
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection(con);
			PreparedStatement statement = con.prepareStatement("DELETE FROM grandboss_spawnlist WHERE boss_id=?");
			statement.setInt(1, bossId);
			
			statement.execute();
			statement.close();
		}
		catch (Exception e)
		{
			// Problem with deleting spawn
			_log.warn("GrandBossSpawnManager: Could not remove grand boss #" + bossId + " from DB: ", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	@Override
	protected void updateDb()
	{
		for (Integer bossId : _storedInfo.keySet())
		{
			Connection con = null;
			try
			{
				con = L2DatabaseFactory.getInstance().getConnection(con);
				L2Boss boss = _bosses.get(bossId);
				L2Spawn spawnDat = _spawns.get(bossId);
				if (boss == null || spawnDat == null)
				{
					continue;
				}
				
				if (boss.getRaidStatus().equals(StatusEnum.ALIVE))
					updateStatus(boss, false);
				
				StatsSet info = _storedInfo.get(bossId);
				if (info == null)
				{
					continue;
				}
				
				PreparedStatement statement =
						con.prepareStatement("UPDATE grandboss_spawnlist SET respawn_time = ?, currentHp = ?, currentMp = ? WHERE boss_id = ?");
				statement.setLong(1, info.getLong("respawnTime"));
				statement.setDouble(2, info.getDouble("currentHp"));
				statement.setDouble(3, info.getDouble("currentMp"));
				statement.setInt(4, bossId);
				statement.execute();
				statement.close();
			}
			catch (SQLException e)
			{
				_log.error("GrandBossSpawnManager: Couldnt update grandboss_spawnlist table", e);
			}
			finally
			{
				L2DatabaseFactory.close(con);
			}
		}
	}
	
	@Override
	public L2NpcTemplate getValidTemplate(int bossId)
	{
		L2NpcTemplate template = NpcTable.getInstance().getTemplate(bossId);
		if (template == null)
			return null;
		if (!template.isAssignableTo(L2GrandBossInstance.class))
			return null;
		return template;
	}
	
	@SuppressWarnings("synthetic-access")
	private static class SingletonHolder
	{
		protected static final GrandBossSpawnManager _instance = new GrandBossSpawnManager();
	}
}
