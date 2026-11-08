# Author: Psycho(killer1888) / L2jFree

import sys
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager
from com.l2jfree.gameserver.model.quest           import State
from com.l2jfree.gameserver.model.quest           import QuestState
from com.l2jfree.gameserver.model.quest.jython    import QuestJython as JQuest
from com.l2jfree.gameserver.network.packets.server import SystemMessage
from com.l2jfree.gameserver.network               import SystemMessageId
from com.l2jfree.tools.random                     import Rnd

class Celtus(JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onKill (self,npc,player,isPet):
		if npc.getQuestDropable() == True and not isPet:
			amount = Rnd.get(1,5)
			player.addItem("Celtus", 9682, amount, player, True, True)
		return

	def onSkillSee(self,npc,caster,skill,targets,isPet):
		if HellboundManager.getInstance().getHellboundLevel() >= 7:
			skillId = skill.getId()
			if skillId != 2359:
				return
			if not npc in targets:
				return
			percent = npc.getStatus().getCurrentHp() / npc.getMaxHp() * 100
			if percent <= 10:
				npc.setMagicBottled(True, percent)
			else:
				npc.setQuestDropable(False)
				caster.sendPacket(SystemMessage(SystemMessageId.NOTHING_HAPPENED))
				return
		return

	def onSpawn (self,npc):
		npc.setQuestDropable(False)
		npc.setMagicBottled(False, 100)
		return

QUEST = Celtus(-1, "Celtus", "ai")
QUEST.addKillId(22353)
QUEST.addSpawnId(22353)
QUEST.addSkillSeeId(22353)
