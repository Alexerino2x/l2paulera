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
package com.l2jfree.gameserver.gameobjects.effects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.l2jfree.Config;
import com.l2jfree.L2DatabaseFactory;
import com.l2jfree.gameserver.datatables.SkillTable;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.skills.L2Skill;
import com.l2jfree.gameserver.model.skills.effects.L2Effect;
import com.l2jfree.util.LookupTable;
import com.l2jfree.util.concurrent.ForEachExecutable;

/**
 * @author NB4L1
 */
public final class PlayerEffects extends CreatureEffects
{
	protected static final class StoredEffect
	{
		public final int skillId;
		public final int skillLvl;
		public final int count;
		public final int remaining;
		
		public StoredEffect(L2Effect effect)
		{
			skillId = effect.getSkill().getId();
			skillLvl = effect.getSkill().getLevel();
			count = effect.getCount();
			remaining = effect.getPeriod() - effect.getTime();
		}
		
		public StoredEffect(ResultSet rset) throws SQLException
		{
			skillId = rset.getInt("skillId");
			skillLvl = rset.getInt("skillLvl");
			count = rset.getInt("count");
			remaining = rset.getInt("remaining");
		}
	}
	
	private final LookupTable<ArrayList<StoredEffect>> _storedEffects = new LookupTable<ArrayList<StoredEffect>>();
	
	public PlayerEffects(L2Player owner)
	{
		super(owner);
	}
	
	@Override
	protected L2Player getOwner()
	{
		return (L2Player)_owner;
	}
	
	public void storeEffects(boolean storeActiveEffects)
	{
		if (!Config.STORE_EFFECTS)
			return;
		
		final ArrayList<StoredEffect> list = getEffectList();
		
		list.clear();
		
		if (storeActiveEffects)
			for (L2Effect e : getAllEffects())
				if (e != null && e.canBeStoredInDb())
					list.add(new StoredEffect(e));
		
		// TODO: delay effect storage
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			deleteEffects(con, getOwner().getClassIndex());
			
			PreparedStatement statement =
					con.prepareStatement("INSERT INTO character_effects (charId,classIndex,buffIndex,skillId,skillLvl,count,remaining) VALUES (?,?,?,?,?,?,?)");
			
			int buffIndex = 0;
			for (StoredEffect se : list)
			{
				statement.setInt(1, getOwner().getObjectId());
				statement.setInt(2, getOwner().getClassIndex());
				statement.setInt(3, ++buffIndex);
				statement.setInt(4, se.skillId);
				statement.setInt(5, se.skillLvl);
				statement.setInt(6, se.count);
				statement.setInt(7, se.remaining);
				statement.execute();
			}
			
			statement.close();
		}
		catch (Exception e)
		{
			_log.warn("", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	public void restoreEffects()
	{
		if (!Config.STORE_EFFECTS)
			return;
		
		final ArrayList<StoredEffect> list = getEffectList();
		
		for (final StoredEffect se : list)
		{
			final L2Skill skill = SkillTable.getInstance().getInfo(se.skillId, se.skillLvl);
			if (skill == null)
				continue;
			
			skill.getEffects(getOwner(), getOwner(), new ForEachExecutable<L2Effect>() {
				@Override
				public void execute(L2Effect e)
				{
					e.setTiming(se.count, se.remaining);
				}
			});
		}
	}
	
	public void deleteEffects(Connection con, int classIndex) throws SQLException
	{
		PreparedStatement statement =
				con.prepareStatement("DELETE FROM character_effects WHERE charId=? AND classIndex=?");
		statement.setInt(1, getOwner().getObjectId());
		statement.setInt(2, classIndex);
		statement.execute();
		statement.close();
		
		_storedEffects.remove(classIndex);
	}
	
	private ArrayList<StoredEffect> getEffectList()
	{
		ArrayList<StoredEffect> list = _storedEffects.get(getOwner().getClassIndex());
		
		if (list != null)
			return list;
		
		list = new ArrayList<StoredEffect>();
		
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			
			PreparedStatement statement =
					con.prepareStatement("SELECT skillId,skillLvl,count,remaining FROM character_effects WHERE charId=? AND classIndex=? ORDER BY buffIndex ASC");
			statement.setInt(1, getOwner().getObjectId());
			statement.setInt(2, getOwner().getClassIndex());
			
			ResultSet rset = statement.executeQuery();
			
			while (rset.next())
			{
				list.add(new StoredEffect(rset));
			}
			
			rset.close();
			statement.close();
		}
		catch (Exception e)
		{
			_log.warn("", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
		
		_storedEffects.set(getOwner().getClassIndex(), list);
		
		return list;
	}
}
