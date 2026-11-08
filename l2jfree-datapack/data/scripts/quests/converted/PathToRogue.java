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
import com.l2jfree.gameserver.gameobjects.itemcontainer.Inventory;
import com.l2jfree.gameserver.gameobjects.itemcontainer.PlayerInventory;
import com.l2jfree.gameserver.model.quest.QuestState;
import com.l2jfree.gameserver.model.quest.State;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;
import com.l2jfree.gameserver.model.skills.L2Skill;
import com.l2jfree.gameserver.network.packets.server.NpcSay;
import com.l2jfree.tools.random.Rnd;

/**
 * 1st class transfer quest for Human Fighter.
 * @author savormix
 */
public final class PathToRogue extends QuestJython
{
	private static final String PATH_TO_ROGUE = "403_PathToRogue";
	
	// Quest NPCs
	private static final int BEZIQUE = 30379;
	private static final int NETI = 30425;
	
	// Quest items
	private static final int BEZIQUES_LETTER = 1180;
	private static final int NETIS_BOW = 1181;
	private static final int NETIS_DAGGER = 1182;
	private static final int SPARTOIS_BONES = 1183;
	private static final int SPARTOI_BONE_COUNT = 10;
	private static final int HORSESHOE_OF_LIGHT = 1184;
	private static final int WANTED_BILL = 1185;
	
	private static final int STOLEN_JEWELRY = 1186;
	private static final int STOLEN_TOMES = 1187;
	private static final int STOLEN_RING = 1188;
	private static final int STOLEN_NECKLACE = 1189;
	private static final int[] STOLEN_ITEMS = { STOLEN_JEWELRY, STOLEN_TOMES, STOLEN_RING, STOLEN_NECKLACE };
	
	private static final int BEZIQUES_RECOMMENDATION = 1190;
	
	// Quest monsters
	private static final int[] SPARTOI = { 20035, 20042, 20045, 20051, 20054, 20060 };
	private static final int CATS_EYE_BANDIT = 27038;
	private static final String BANDIT_ATTACKED = "You childish fool, do you think you can catch me?";
	private static final String BANDIT_KILLED = "I must do something about this shameful incident...";
	
	private PathToRogue(int questId, String name, String descr)
	{
		super(questId, name, descr);
		questItemIds =
				new int[] { BEZIQUES_LETTER, NETIS_BOW, NETIS_DAGGER, SPARTOIS_BONES, HORSESHOE_OF_LIGHT, WANTED_BILL,
						STOLEN_JEWELRY, STOLEN_TOMES, STOLEN_RING, STOLEN_NECKLACE, BEZIQUES_RECOMMENDATION };
		addStartNpc(BEZIQUE);
		addTalkId(BEZIQUE);
		addTalkId(NETI);
		for (int mobId : SPARTOI)
		{
			addAttackId(mobId);
			addKillId(mobId);
		}
		addAttackId(CATS_EYE_BANDIT);
		addKillId(CATS_EYE_BANDIT);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2Player attacker, int damage, boolean isPet, L2Skill skill)
	{
		int weaponId = attacker.getInventory().getPaperdollItemId(Inventory.PAPERDOLL_RHAND);
		switch (npc.getQuestAttackStatus())
		{
			case ATTACK_NOONE:
				npc.setQuestFirstAttacker(attacker);
				if (weaponId == NETIS_BOW || weaponId == NETIS_DAGGER)
				{
					if (npc.getNpcId() == CATS_EYE_BANDIT)
						npc.broadcastPacket(new NpcSay(npc, BANDIT_ATTACKED));
					npc.setQuestAttackStatus(ATTACK_SINGLE);
				}
				else
					npc.setQuestAttackStatus(ATTACK_MULTIPLE);
				break;
			case ATTACK_SINGLE:
				if (weaponId != NETIS_BOW && weaponId != NETIS_DAGGER)
					npc.setQuestAttackStatus(ATTACK_MULTIPLE);
				if (attacker != npc.getQuestFirstAttacker())
					npc.setQuestAttackStatus(ATTACK_MULTIPLE);
				break;
		}
		return null;
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		QuestState qs = player.getQuestState(PATH_TO_ROGUE);
		if ("30379_2".equals(event))
		{
			ClassId prof = player.getClassId();
			if (prof == ClassId.HumanFighter && !qs.isCompleted())
			{
				if (player.getLevel() > 17)
				{
					if (player.getInventory().getInventoryItemCount(BEZIQUES_RECOMMENDATION, -1) != 0)
						return "30379-04.htm";
					else
						return "30379-05.htm";
				}
				else
					return "30379-03.htm";
			}
			else if (prof == ClassId.Rogue)
				return "30379-02a.htm";
			else
				return "30379-02.htm";
		}
		else if (!qs.isCompleted())
		{
			if (QUEST_START_EVT.equals(event))
			{
				qs.set(CONDITION, 1);
				qs.setState(State.STARTED);
				player.sendPacket(SND_ACCEPT);
				qs.giveItems(BEZIQUES_LETTER, 1);
				return "30379-06.htm";
			}
			else if ("30425_1".equals(event))
			{
				qs.takeItems(BEZIQUES_LETTER, -1);
				if (qs.getQuestItemsCount(NETIS_BOW) == 0)
					qs.giveItems(NETIS_BOW, 1);
				if (qs.getQuestItemsCount(NETIS_DAGGER) == 0)
					qs.giveItems(NETIS_DAGGER, 1);
				qs.set(CONDITION, 2);
				return "30425-05.htm";
			}
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		if (npc.getNpcId() == CATS_EYE_BANDIT)
			npc.broadcastPacket(new NpcSay(npc, BANDIT_KILLED));
		L2Player quester = npc.getQuestFirstAttacker();
		if (quester == null)
			return null;
		QuestState qs = quester.getQuestState(PATH_TO_ROGUE);
		if (qs == null || !qs.isStarted() || qs.getInt(CONDITION) == 0 || npc.getQuestAttackStatus() != ATTACK_SINGLE)
			return null;
		
		int npcId = npc.getNpcId();
		if (npcId == CATS_EYE_BANDIT)
		{
			if (qs.getQuestItemsCount(WANTED_BILL) == 0)
				return null;
			// item type depends on chance, drop chance cannot be changed
			int ran = Rnd.get(4);
			if (qs.getQuestItemsCount(STOLEN_ITEMS[ran]) == 0)
			{
				qs.giveItems(STOLEN_ITEMS[ran], 1);
				if (allStolenItems(qs))
				{
					qs.set(CONDITION, 6);
					quester.sendPacket(SND_MIDDLE);
				}
				else
					quester.sendPacket(SND_ITEM_GET);
			}
		}
		else
		{
			int chance;
			if (npcId == SPARTOI[0] || npcId == SPARTOI[2] || npcId == SPARTOI[3])
				chance = 200000;
			else if (npcId == SPARTOI[1])
				chance = 300000;
			else
				chance = 800000;
			if (qs.dropQuestItems(SPARTOIS_BONES, 1, SPARTOI_BONE_COUNT, chance, true, false))
				qs.set(CONDITION, 3);
		}
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		QuestState qs = talker.getQuestState(PATH_TO_ROGUE);
		if (qs == null)
			return NO_QUEST;
		
		int npcId = npc.getNpcId();
		int state = qs.getState();
		int cond = qs.getInt(CONDITION);
		
		if (npcId == BEZIQUE)
		{
			if (cond == 0)
				return "30379-01.htm";
			else if (qs.getQuestItemsCount(HORSESHOE_OF_LIGHT) == 0)
			{
				if (allStolenItems(qs))
				{
					String done = qs.getGlobalQuestVar("1ClassQuestFinished");
					qs.set(CONDITION, 0);
					qs.exitQuest(false);
					if (done.isEmpty())
					{
						qs.rewardItems(PlayerInventory.ADENA_ID, 81900);
						qs.addExpAndSp(295862, 16814);
						qs.giveItems(BEZIQUES_RECOMMENDATION, 1);
					}
					qs.saveGlobalQuestVar("1ClassQuestFinished", "1");
					talker.sendPacket(SND_FINISH);
					return "30379-09.htm";
				}
				else if (qs.getQuestItemsCount(BEZIQUES_LETTER) != 0)
					return "30379-07.htm";
				else if (qs.getQuestItemsCount(NETIS_BOW) != 0 && qs.getQuestItemsCount(NETIS_DAGGER) != 0
						&& qs.getQuestItemsCount(WANTED_BILL) == 0)
					return "30379-10.htm";
				else
					return "30379-11.htm";
			}
			else
			{
				qs.takeItems(HORSESHOE_OF_LIGHT, -1);
				qs.giveItems(WANTED_BILL, 1);
				qs.set(CONDITION, 5);
				return "30379-08.htm";
			}
		}
		else
		{
			if (state != State.STARTED || cond == 0)
				return NO_QUEST;
			else if (qs.getQuestItemsCount(BEZIQUES_LETTER) != 0)
				return "30425-01.htm";
			else if (qs.getQuestItemsCount(HORSESHOE_OF_LIGHT) != 0)
				return "30425-08.htm";
			else if (qs.getQuestItemsCount(WANTED_BILL) != 0)
				return "30425-08.htm";
			else if (qs.getQuestItemsCount(SPARTOIS_BONES) >= SPARTOI_BONE_COUNT)
			{
				qs.takeItems(SPARTOIS_BONES, -1);
				qs.giveItems(HORSESHOE_OF_LIGHT, 1);
				qs.set(CONDITION, 4);
				return "30425-07.htm";
			}
			else
				return "30425-06.htm";
		}
	}
	
	private static final boolean allStolenItems(QuestState qs)
	{
		return (qs.getQuestItemsCount(STOLEN_JEWELRY) + qs.getQuestItemsCount(STOLEN_TOMES)
				+ qs.getQuestItemsCount(STOLEN_RING) + qs.getQuestItemsCount(STOLEN_NECKLACE) == 4);
	}
	
	public static void main(String[] args)
	{
		new PathToRogue(403, PATH_TO_ROGUE, "Path to Rogue");
	}
}
