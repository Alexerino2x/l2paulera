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
package instances.SteelCitadel;

import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.instancemanager.hellbound.TowerOfNaiaManager;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;

/**
 * @author hex1r0
 */
public class TowerOfNaia extends QuestJython
{
	@SuppressWarnings("unused")
	private L2Npc _lock = null;
	@SuppressWarnings("unused")
	private boolean _areWardsSpawned = false;
	
	public TowerOfNaia(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addAttackId(TowerOfNaiaManager.ROOF_LOCK_ID);
		
		addSpawnId(TowerOfNaiaManager.ROOF_LOCK_ID);
		addSpawnId(TowerOfNaiaManager.DARION_ID);
		
		addKillId(TowerOfNaiaManager.DARION_ID);
		
		addStartNpc(TowerOfNaiaManager.ROOF_CONTROLLER_ID);
		addStartNpc(TowerOfNaiaManager.ROOM_CONTROLLER_ID);
		addTalkId(TowerOfNaiaManager.ROOF_CONTROLLER_ID);
		addTalkId(TowerOfNaiaManager.ROOM_CONTROLLER_ID);
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		if (npc.getNpcId() == TowerOfNaiaManager.DARION_ID)
			TowerOfNaiaManager.getInstance().openEnteranceDoors();
		else
			TowerOfNaiaManager.getInstance().notifyMobKilled(npc.getInstanceId());
		
		return super.onKill(npc, killer, isPet);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc.getNpcId() == TowerOfNaiaManager.ROOF_LOCK_ID)
			_lock = npc;
		else if (npc.getNpcId() == TowerOfNaiaManager.DARION_ID)
			TowerOfNaiaManager.getInstance().closeEnteranceDoors();
		
		return super.onSpawn(npc);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		if (event.equalsIgnoreCase("startinstance"))
		{
			// TODO temp disabled
			//if (_lock != null && _lock.getCurrentHp() < _lock.getMaxHp() * 0.1)
			//{
			if (player.isInParty())
			{
				final int instanceId = TowerOfNaiaManager.getInstance().startInstance();
				if (instanceId != Integer.MIN_VALUE)
				{
					for (L2Player partyMember : player.getParty().getPartyMembers())
					{
						partyMember.teleToLocation(TowerOfNaiaManager.WAITING_ROOM_START_POINT, true);
						partyMember.setInstanceId(instanceId);
					}
				}
			}
			else
			{
				player.sendMessage("You are not in party!");
			}
			//}
			//else
			//{
			//player.sendMessage("Incorrect conditions!");
			// TODO lock & controller should dissapear if incorrect conditions are met
			//}
			_areWardsSpawned = false;
		}
		else if (event.equalsIgnoreCase("startroom"))
		{
			TowerOfNaiaManager.getInstance().startRoomInvasion(player.getInstanceId());
			npc.decayMe();
		}
		
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2Player attacker, int damage, boolean isPet)
	{
		//		if (npc.getNpcId() == TowerOfNaiaManager.ROOF_LOCK_ID)
		//		{
		//			// TODO testme
		//			if (!_areWardsSpawned && npc.getCurrentHp() < npc.getMaxHp() * 0.8)
		//			{
		//				_areWardsSpawned = true;
		//				addSpawn(TowerOfNaiaManager.WARD_ID, npc);
		//			}
		//		}
		
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	public static void main(String[] args)
	{
		new TowerOfNaia(-1, "TowerOfNaia", "TowerOfNaia");
	}
}
