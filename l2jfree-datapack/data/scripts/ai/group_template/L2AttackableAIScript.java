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
package ai.group_template;

import static com.l2jfree.gameserver.gameobjects.ai.CtrlIntention.AI_INTENTION_ATTACK;

import org.apache.commons.lang3.ArrayUtils;

import com.l2jfree.Config;
import com.l2jfree.gameserver.datatables.NpcTable;
import com.l2jfree.gameserver.gameobjects.L2Attackable;
import com.l2jfree.gameserver.gameobjects.L2Creature;
import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Object;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.gameobjects.ai.CtrlEvent;
import com.l2jfree.gameserver.gameobjects.ai.CtrlIntention;
import com.l2jfree.gameserver.gameobjects.ai.FactionAggressionNotificationQueue;
import com.l2jfree.gameserver.gameobjects.instance.L2RiftInvaderInstance;
import com.l2jfree.gameserver.gameobjects.templates.L2NpcTemplate;
import com.l2jfree.gameserver.instancemanager.DimensionalRiftManager;
import com.l2jfree.gameserver.model.quest.Quest;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;
import com.l2jfree.gameserver.model.skills.L2Skill;

/**
 * 
 * Overarching Superclass for all mob AI
 * @author Fulminus
 *
 */
public class L2AttackableAIScript extends QuestJython
{
	/**
	 * This is used to register all monsters contained in mobs for a particular script
	 * @param mobs
	 */
	public void registerMobs(int... mobs)
	{
		for (int id : mobs)
		{
			addEventId(id, Quest.QuestEventType.ON_ATTACK);
			addEventId(id, Quest.QuestEventType.ON_KILL);
			addEventId(id, Quest.QuestEventType.ON_SPAWN);
			addEventId(id, Quest.QuestEventType.ON_SPELL_FINISHED);
			addEventId(id, Quest.QuestEventType.ON_SKILL_SEE);
			addEventId(id, Quest.QuestEventType.ON_FACTION_CALL);
			addEventId(id, Quest.QuestEventType.ON_AGGRO_RANGE_ENTER);
			addEventId(id, Quest.QuestEventType.ON_ARRIVED);
		}
	}
	
	/**
	 * This is used simply for convenience of replacing
	 * jython 'element in list' boolean method.
	 */
	public static <T> boolean contains(T[] array, T obj)
	{
		return ArrayUtils.contains(array, obj);
	}
	
	public static boolean contains(int[] array, int obj)
	{
		return ArrayUtils.contains(array, obj);
	}
	
	public L2AttackableAIScript(int questId, String name, String descr)
	{
		super(questId, name, descr);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		return null;
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2Player player, L2Skill skill)
	{
		return null;
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2Player caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		if (caster == null)
			return null;
		
		if (!(npc instanceof L2Attackable))
			return null;
		
		L2Attackable attackable = (L2Attackable)npc;
		int skillAggroPoints = skill.getAggroPoints();
		if (caster.getPet() != null)
		{
			if (targets.length == 1 && contains(targets, caster.getPet()))
				skillAggroPoints = 0;
		}
		if (skillAggroPoints > 0)
		{
			skillAggroPoints /= Config.ALT_BUFFER_HATE;
			
			if (attackable.hasAI() && (attackable.getAI().getIntention() == AI_INTENTION_ATTACK))
			{
				L2Object npcTarget = attackable.getTarget();
				for (L2Object skillTarget : targets)
				{
					if (npcTarget == skillTarget || npc == skillTarget)
					{
						L2Creature originalCaster = isPet ? caster.getPet() : caster;
						attackable.addDamageHate(originalCaster, 0, (skillAggroPoints * 150)
								/ (attackable.getLevel() + 7));
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public String onFactionCall(L2Npc npc, L2Npc caller, L2Player attacker, boolean isPet)
	{
		L2Creature originalAttackTarget = (isPet ? attacker.getPet() : attacker);
		
		if (attacker.isInParty() && attacker.getParty().isInDimensionalRift())
		{
			byte riftType = attacker.getParty().getDimensionalRift().getType();
			byte riftRoom = attacker.getParty().getDimensionalRift().getCurrentRoom();
			
			if (caller instanceof L2RiftInvaderInstance
					&& !DimensionalRiftManager.getInstance().getRoom(riftType, riftRoom)
							.checkIfInZone(npc.getX(), npc.getY(), npc.getZ()))
			{
				return null;
			}
		}
		
		// By default, when a faction member calls for help, attack the caller's attacker.
		// Notify the AI with EVT_AGGRESSION
		FactionAggressionNotificationQueue.add(npc.getFactionId(), npc, originalAttackTarget);
		
		return null;
	}
	
	@Override
	public String onAggroRangeEnter(L2Npc npc, L2Player player, boolean isPet)
	{
		L2Creature target = isPet ? player.getPet() : player;
		
		((L2Attackable)npc).addDamageHate(target, 0, 1);
		
		// Set the intention to the L2Attackable to AI_INTENTION_ACTIVE
		if (npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_IDLE)
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
		return null;
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		return null;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2Player attacker, int damage, boolean isPet)
	{
		if (attacker != null && (npc instanceof L2Attackable))
		{
			L2Attackable attackable = (L2Attackable)npc;
			
			L2Creature originalAttacker = isPet ? attacker.getPet() : attacker;
			attackable.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, originalAttacker);
			attackable.addDamageHate(originalAttacker, damage, (damage * 100) / (attackable.getLevel() + 7));
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		return null;
	}
	
	public static void main(String[] args)
	{
		L2AttackableAIScript ai = new L2AttackableAIScript(-1, "L2AttackableAIScript", "L2AttackableAIScript");
		// register all mobs here...
		for (int level = 1; level < 100; level++)
		{
			L2NpcTemplate[] templates = NpcTable.getInstance().getAllOfLevel(level);
			if (templates != null)
			{
				for (L2NpcTemplate t : templates)
				{
					try
					{
						if (t.isAssignableTo(L2Attackable.class))
						{
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_ATTACK);
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_KILL);
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_SPAWN);
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_SKILL_SEE);
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_FACTION_CALL);
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_AGGRO_RANGE_ENTER);
							ai.addEventId(t.getNpcId(), Quest.QuestEventType.ON_ARRIVED);
						}
					}
					catch (RuntimeException e)
					{
						_log.warn("", e);
					}
				}
			}
		}
	}
}
