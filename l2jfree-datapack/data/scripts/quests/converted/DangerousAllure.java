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
import com.l2jfree.gameserver.gameobjects.base.Race;
import com.l2jfree.gameserver.gameobjects.itemcontainer.PlayerInventory;
import com.l2jfree.gameserver.model.quest.QuestState;
import com.l2jfree.gameserver.model.quest.State;
import com.l2jfree.gameserver.model.quest.jython.QuestJython;
import com.l2jfree.gameserver.model.skills.L2Skill;
import com.l2jfree.gameserver.network.packets.server.NpcSay;

/**
 * A quest restricted to dark elves.
 * @author savormix
 */
public final class DangerousAllure extends QuestJython
{
	private static final String DANGEROUS_ALLURE = "170_DangerousAllure";
	
	// Quest NPCs
	private static final int VELLIOR = 30305;
	
	// Quest items
	private static final int NIGHTMARE_CRYSTAL = 1046;
	
	// Quest monsters
	private static final int MERKENIS = 27022;
	private static final String MERKENIS_ATTACKED = "I shall put you in a never-ending nightmare!";
	private static final String MERKENIS_KILLED = "My soul is to Icarus...";
	
	private DangerousAllure(int questId, String name, String descr)
	{
		super(questId, name, descr);
		questItemIds = new int[] { NIGHTMARE_CRYSTAL };
		addStartNpc(VELLIOR);
		addTalkId(VELLIOR);
		addAttackId(MERKENIS);
		addKillId(MERKENIS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2Player player)
	{
		QuestState qs = player.getQuestState(DANGEROUS_ALLURE);
		if (qs.isCompleted())
			return QUEST_DONE;
		else if (QUEST_START_EVT.equals(event))
		{
			qs.set(CONDITION, 1);
			qs.setState(State.STARTED);
			player.sendPacket(SND_ACCEPT);
			return "30305-04.htm";
		}
		else
			return event;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2Player attacker, int damage, boolean isPet, L2Skill skill)
	{
		switch (npc.getQuestAttackStatus())
		{
			case ATTACK_NOONE:
				npc.broadcastPacket(new NpcSay(npc, MERKENIS_ATTACKED));
				npc.setQuestAttackStatus(ATTACK_SINGLE);
				npc.setQuestFirstAttacker(attacker);
				break;
			case ATTACK_SINGLE:
				//if (attacker != npc.getQuestFirstAttacker())
				//	npc.setQuestAttackStatus(ATTACK_MULTIPLE);
				break;
		}
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		L2Player quester = killer/*npc.getQuestFirstAttacker()*/;
		if (quester == null)
			return null;
		QuestState qs = quester.getQuestState(DANGEROUS_ALLURE);
		if (qs == null || !qs.isStarted() || qs.getInt(CONDITION) != 1 || npc.getQuestAttackStatus() != ATTACK_SINGLE)
			return null;
		
		if (qs.getQuestItemsCount(NIGHTMARE_CRYSTAL) == 0)
		{
			npc.broadcastPacket(new NpcSay(npc, MERKENIS_KILLED));
			qs.giveItems(NIGHTMARE_CRYSTAL, 1);
			quester.sendPacket(SND_MIDDLE);
			qs.set(CONDITION, 2);
		}
		
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		QuestState qs = talker.getQuestState(DANGEROUS_ALLURE);
		if (qs == null)
			return NO_QUEST;
		else if (qs.isCompleted())
			return QUEST_DONE;
		
		int cond = qs.getInt(CONDITION);
		if (cond == 0)
		{
			if (talker.getRace() != Race.Darkelf)
			{
				qs.exitQuest(true);
				return "30305-00.htm";
			}
			else if (talker.getLevel() < 21)
			{
				qs.exitQuest(true);
				return "30305-02.htm";
			}
			else
				return "30305-03.htm";
		}
		else
		{
			if (qs.getQuestItemsCount(NIGHTMARE_CRYSTAL) != 0)
			{
				qs.exitQuest(false);
				qs.rewardItems(PlayerInventory.ADENA_ID, 102680);
				qs.addExpAndSp(38607, 4018);
				talker.sendPacket(SND_FINISH);
				return "30305-06.htm";
			}
			else
				return "30305-05.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new DangerousAllure(170, DANGEROUS_ALLURE, "Dangerous Allure");
	}
}
