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
package quests.converted;

import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.gameobjects.base.ClassId;
import com.l2jfree.gameserver.gameobjects.itemcontainer.PlayerInventory;
import com.l2jfree.gameserver.model.quest.QuestState;
import com.l2jfree.gameserver.model.quest.State;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;
import com.l2jfree.gameserver.model.skills.L2Skill;
import com.l2jfree.gameserver.network.packets.server.NpcSay;

/**
 * 1st class transfer quest for Elven Mystic.
 * @author savormix
 */
public final class PathToOracle extends QuestJython
{
	private static final String PATH_TO_ORACLE = "409_PathToOracle";
	
	// Quest NPCs
	private static final int MANUEL = 30293;
	private static final int ALLANA = 30424;
	private static final int PERRIN = 30428;
	
	// Quest items
	private static final int CRYSTAL_MEDALLION = 1231;
	private static final int SWINDLERS_MONEY = 1232;
	private static final int ALLANAS_DIARY = 1233;
	private static final int LIZARD_CAPTAIN_ORDER = 1234;
	private static final int LEAF_OF_ORACLE = 1235;
	private static final int HALF_OF_DIARY = 1236;
	private static final int TAMILS_NECKLACE = 1275;
	
	// Quest monsters
	private static final int LIZARDMAN_WARRIOR = 27032;
	private static final int LIZARDMAN_SCOUT = 27033;
	private static final int LIZARDMAN = 27034;
	private static final int TAMIL = 27035;
	private static final String WARRIOR_ATTACKED = "The sacred flame is ours!";
	private static final String WARRIOR_KILLED = "Arrghh...we will give up ";
	private static final String LIZARDMAN_ATTACKED = "The sacred flame is ours";
	private static final String TAMIL_ATTACKED = "As you wish, master!";
	
	private PathToOracle(int questId, String name, String descr)
	{
		super(questId, name, descr);
		questItemIds =
				new int[] { CRYSTAL_MEDALLION, SWINDLERS_MONEY, ALLANAS_DIARY, LIZARD_CAPTAIN_ORDER, LEAF_OF_ORACLE,
						HALF_OF_DIARY, TAMILS_NECKLACE };
		addStartNpc(MANUEL);
		addTalkId(MANUEL);
		addTalkId(ALLANA);
		addTalkId(PERRIN);
		addAttackId(LIZARDMAN_WARRIOR);
		addKillId(LIZARDMAN_WARRIOR);
		addAttackId(LIZARDMAN_SCOUT);
		addKillId(LIZARDMAN_SCOUT);
		addAttackId(LIZARDMAN);
		addKillId(LIZARDMAN);
		addAttackId(TAMIL);
		addKillId(TAMIL);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2Player attacker, int damage, boolean isPet, L2Skill skill)
	{
		switch (npc.getQuestAttackStatus())
		{
			case ATTACK_NOONE:
				npc.setQuestFirstAttacker(attacker);
				String say;
				switch (npc.getNpcId())
				{
					case LIZARDMAN_WARRIOR:
						say = WARRIOR_ATTACKED;
						break;
					case TAMIL:
						say = TAMIL_ATTACKED;
						break;
					default:
						say = LIZARDMAN_ATTACKED;
						break;
				}
				npc.broadcastPacket(new NpcSay(npc, say));
				npc.setQuestAttackStatus(ATTACK_SINGLE);
				break;
			case ATTACK_SINGLE:
				if (attacker != npc.getQuestFirstAttacker())
					npc.setQuestAttackStatus(ATTACK_MULTIPLE);
				break;
		}
		return null;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		QuestState qs = player.getQuestState(PATH_TO_ORACLE);
		if (QUEST_START_EVT.equals(event))
		{
			ClassId prof = player.getClassId();
			if (prof == ClassId.ElvenMystic && !qs.isCompleted())
			{
				if (player.getLevel() > 17)
				{
					if (player.getInventory().getInventoryItemCount(LEAF_OF_ORACLE, -1) == 0)
					{
						qs.set(CONDITION, 1);
						qs.setState(State.STARTED);
						player.sendPacket(SND_ACCEPT);
						qs.giveItems(CRYSTAL_MEDALLION, 1);
						return "30293-05.htm";
					}
					else
						return "30293-04.htm";
				}
				else
					return "30293-03.htm";
			}
			else if (prof == ClassId.ElvenOracle)
				return "30293-02a.htm";
			else
				return "30293-02.htm";
		}
		else if (!qs.isCompleted())
		{
			/*
			if ("30424-08.htm".equals(event))
			{
				qs.set(CONDITION, 2);
				qs.addSpawn(LIZARDMAN_WARRIOR);
				qs.addSpawn(LIZARDMAN_SCOUT);
				qs.addSpawn(LIZARDMAN);
			}
			else */if ("30424_1".equals(event))
			{
				qs.set(CONDITION, 2);
				qs.addSpawn(LIZARDMAN_WARRIOR);
				qs.addSpawn(LIZARDMAN_SCOUT);
				qs.addSpawn(LIZARDMAN);
				return null;
			}
			else if ("30428_1".equals(event))
				return "30428-02.htm";
			else if ("30428_2".equals(event))
				return "30428-03.htm";
			else if ("30428_3".equals(event))
			{
				qs.addSpawn(TAMIL);
				return null;
			}
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		L2Player quester = npc.getQuestFirstAttacker();
		if (quester == null)
			return null;
		QuestState qs = quester.getQuestState(PATH_TO_ORACLE);
		if (qs == null || !qs.isStarted() || qs.getInt(CONDITION) == 0 || npc.getQuestAttackStatus() != ATTACK_SINGLE)
			return null;
		
		int npcId = npc.getNpcId();
		if (npcId == TAMIL)
		{
			if (qs.getQuestItemsCount(TAMILS_NECKLACE) == 0)
			{
				qs.giveItems(TAMILS_NECKLACE, 1);
				quester.sendPacket(SND_MIDDLE);
				qs.set(CONDITION, 5);
			}
		}
		else if (qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 0)
		{
			if (npcId == LIZARDMAN_WARRIOR)
				npc.broadcastPacket(new NpcSay(npc, WARRIOR_KILLED));
			qs.giveItems(LIZARD_CAPTAIN_ORDER, 1);
			quester.sendPacket(SND_MIDDLE);
			qs.set(CONDITION, 3);
		}
		
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		QuestState qs = talker.getQuestState(PATH_TO_ORACLE);
		if (qs == null)
			return NO_QUEST;
		
		int npcId = npc.getNpcId();
		int cond = qs.getInt(CONDITION);
		
		if (npcId == MANUEL)
		{
			if (qs.getQuestItemsCount(CRYSTAL_MEDALLION) != 0)
			{
				if (qs.getQuestItemsCount(ALLANAS_DIARY) == 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 0
						&& qs.getQuestItemsCount(SWINDLERS_MONEY) == 0 && qs.getQuestItemsCount(HALF_OF_DIARY) == 0)
				{
					if (cond == 0)
						return "30293-06.htm";
					else
						return "30293-09.htm";
				}
				else
				{
					if (qs.getQuestItemsCount(ALLANAS_DIARY) != 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) != 0
							&& qs.getQuestItemsCount(SWINDLERS_MONEY) != 0 && qs.getQuestItemsCount(HALF_OF_DIARY) == 0)
					{
						qs.takeItems(SWINDLERS_MONEY, -1);
						qs.takeItems(ALLANAS_DIARY, -1);
						qs.takeItems(LIZARD_CAPTAIN_ORDER, -1);
						qs.takeItems(CRYSTAL_MEDALLION, -1);
						String done = qs.getGlobalQuestVar("1ClassQuestFinished");
						qs.set(CONDITION, 0);
						qs.exitQuest(false);
						if (done.isEmpty())
						{
							qs.rewardItems(PlayerInventory.ADENA_ID, 81900);
							qs.addExpAndSp(295862, 16894); // 2140?
							qs.giveItems(LEAF_OF_ORACLE, 1);
						}
						qs.saveGlobalQuestVar("1ClassQuestFinished", "1");
						talker.sendPacket(SND_FINISH);
						return "30293-08.htm";
					}
					else
						return "30293-07.htm";
				}
			}
			else if (cond == 0)
			{
				if (qs.getQuestItemsCount(LEAF_OF_ORACLE) == 0)
					return "30293-01.htm";
				else
					return "30293-04.htm";
			}
		}
		else if (cond != 0 && qs.getQuestItemsCount(CRYSTAL_MEDALLION) != 0)
		{
			if (npcId == ALLANA)
			{
				if (qs.getQuestItemsCount(ALLANAS_DIARY) == 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) == 0
						&& qs.getQuestItemsCount(SWINDLERS_MONEY) == 0 && qs.getQuestItemsCount(HALF_OF_DIARY) == 0)
				{
					if (cond > 2)
						return "30424-05.htm";
					else
						return "30424-01.htm";
				}
				else if (qs.getQuestItemsCount(ALLANAS_DIARY) == 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) != 0
						&& qs.getQuestItemsCount(SWINDLERS_MONEY) == 0 && qs.getQuestItemsCount(HALF_OF_DIARY) == 0)
				{
					qs.giveItems(HALF_OF_DIARY, 1);
					qs.set(CONDITION, 4);
					return "30424-02.htm";
				}
				else if (qs.getQuestItemsCount(ALLANAS_DIARY) == 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) != 0
						&& qs.getQuestItemsCount(SWINDLERS_MONEY) == 0 && qs.getQuestItemsCount(HALF_OF_DIARY) != 0)
				{
					if (qs.getQuestItemsCount(TAMILS_NECKLACE) == 0)
						return "30424-06.htm";
					else
						return "30424-03.htm";
				}
				else if (qs.getQuestItemsCount(ALLANAS_DIARY) == 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) != 0
						&& qs.getQuestItemsCount(SWINDLERS_MONEY) != 0 && qs.getQuestItemsCount(HALF_OF_DIARY) != 0)
				{
					qs.takeItems(HALF_OF_DIARY, -1);
					qs.giveItems(ALLANAS_DIARY, 1);
					qs.set(CONDITION, 7);
					return "30424-04.htm";
				}
				else if (qs.getQuestItemsCount(ALLANAS_DIARY) != 0 && qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) != 0
						&& qs.getQuestItemsCount(SWINDLERS_MONEY) != 0 && qs.getQuestItemsCount(HALF_OF_DIARY) == 0)
					return "30424-05.htm";
			}
			else if (qs.getQuestItemsCount(LIZARD_CAPTAIN_ORDER) != 0)
			{
				if (qs.getQuestItemsCount(TAMILS_NECKLACE) != 0)
				{
					qs.takeItems(TAMILS_NECKLACE, -1);
					qs.giveItems(SWINDLERS_MONEY, 1);
					qs.set(CONDITION, 6);
					return "30428-04.htm";
				}
				else
				{
					if (qs.getQuestItemsCount(SWINDLERS_MONEY) == 0)
					{
						if (cond > 4)
							return "30428-06.htm";
						else
							return "30428-01.htm";
					}
					else
						return "30428-05.htm";
				}
			}
		}
		return NO_QUEST;
	}
	
	public static void main(String[] args)
	{
		new PathToOracle(409, PATH_TO_ORACLE, "Path to Oracle");
	}
}
