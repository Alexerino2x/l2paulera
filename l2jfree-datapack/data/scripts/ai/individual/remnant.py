import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

MOBLIST = [18463,18464]

class remnant(JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onKill (self,npc,player,isPet):
		return
		
	def onSpawn(self, npc):
		npc.setKillable(False)
	
QUEST = remnant(-1, "remnant", "ai")

for mob in MOBLIST:
	QUEST.addKillId(mob)
	QUEST.addSpawnId(mob)