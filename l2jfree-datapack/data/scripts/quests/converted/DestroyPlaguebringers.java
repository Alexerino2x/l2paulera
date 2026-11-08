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
 * A repeatable hunting quest restricted to elves.
 * @author savormix
 */
public final class DestroyPlaguebringers extends QuestJython
{
	private static final String DESTROY_PLAGUE_BRINGERS = "316_DestroyPlaguebringers";
	
	// Quest NPCs
	private static final int ELLIASIN = 30155;
	
	// Quest items
	private static final int WERERAT_FANG = 1042;
	private static final int NORMAL_FANG_REWARD = 60;
	private static final int VAROOL_FOULCLAWS_FANG = 1043;
	private static final int LEADER_FANG_REWARD = 10000;
	
	// Quest monsters
	private static final int SUKAR_WERERAT = 20040;
	private static final int SUKAR_WERERAT_LEADER = 20047;
	private static final int VAROOL_FOULCLAW = 27020;
	private static final String VAROOL_ATTACKED = "For what reason are you oppressing us?";
	
	private DestroyPlaguebringers(int questId, String name, String descr)
	{
		super(questId, name, descr);
		questItemIds = new int[] { WERERAT_FANG, VAROOL_FOULCLAWS_FANG };
		addStartNpc(ELLIASIN);
		addTalkId(ELLIASIN);
		addAttackId(SUKAR_WERERAT);
		addKillId(SUKAR_WERERAT);
		addAttackId(SUKAR_WERERAT_LEADER);
		addKillId(SUKAR_WERERAT_LEADER);
		addAttackId(VAROOL_FOULCLAW);
		addKillId(VAROOL_FOULCLAW);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2Player attacker, int damage, boolean isPet, L2Skill skill)
	{
		switch (npc.getQuestAttackStatus())
		{
			case ATTACK_NOONE:
				if (npc.getNpcId() == VAROOL_FOULCLAW)
					npc.broadcastPacket(new NpcSay(npc, VAROOL_ATTACKED));
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
	public String onEvent(String event, QuestState qs)
	{
		if ("30155-04.htm".equals(event))
		{
			qs.set(CONDITION, 1);
			qs.setState(State.STARTED);
			qs.sendPacket(SND_ACCEPT);
		}
		else if ("30155-08.htm".equals(event))
		{
			qs.exitQuest(true);
			qs.sendPacket(SND_FINISH);
		}
		return event;
	}
	
	@Override
	public String onKill(L2Npc npc, L2Player killer, boolean isPet)
	{
		L2Player quester = killer/*npc.getQuestFirstAttacker()*/;
		if (quester == null)
			return null;
		QuestState qs = quester.getQuestState(DESTROY_PLAGUE_BRINGERS);
		if (qs == null || !qs.isStarted() || qs.getInt(CONDITION) != 1 || npc.getQuestAttackStatus() != ATTACK_SINGLE)
			return null;
		
		if (npc.getNpcId() == VAROOL_FOULCLAW)
		{
			if (qs.getQuestItemsCount(VAROOL_FOULCLAWS_FANG) == 0)
				qs.dropQuestItems(VAROOL_FOULCLAWS_FANG, 1, 1, 700000, true, false);
		}
		else
			qs.dropQuestItems(WERERAT_FANG, 1, Long.MAX_VALUE, 500000, true, false);
		
		return null;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2Player talker)
	{
		QuestState qs = talker.getQuestState(DESTROY_PLAGUE_BRINGERS);
		if (qs == null)
			return NO_QUEST;
		
		int cond = qs.getInt(CONDITION);
		if (cond == 0)
		{
			if (talker.getRace() != Race.Elf)
			{
				qs.exitQuest(true);
				return "30155-00.htm";
			}
			else if (talker.getLevel() < 18)
			{
				qs.exitQuest(true);
				return "30155-02.htm";
			}
			else
				return "30155-03.htm";
		}
		else
		{
			long normal = qs.getQuestItemsCount(WERERAT_FANG);
			long leader = qs.getQuestItemsCount(VAROOL_FOULCLAWS_FANG);
			if (normal != 0 || leader != 0)
			{
				qs.takeItems(WERERAT_FANG, normal);
				qs.takeItems(VAROOL_FOULCLAWS_FANG, leader);
				qs.rewardItems(PlayerInventory.ADENA_ID, (normal * NORMAL_FANG_REWARD + leader * LEADER_FANG_REWARD));
				return "30155-07.htm";
			}
			else
				return "30155-05.htm";
		}
	}
	
	public static void main(String[] args)
	{
		new DestroyPlaguebringers(316, DESTROY_PLAGUE_BRINGERS, "Destroy Plague Bringers");
	}
}
