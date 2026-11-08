# By Psycho(killer1888) / L2jFree

import sys
from com.l2jfree.gameserver.model.quest           import State
from com.l2jfree.gameserver.model.quest           import QuestState
from com.l2jfree.gameserver.model.quest.jython    import QuestJython as JQuest

WANDERING_CARAVAN = 22339
BASIC_CERTIFICATE = 9850
STANDARD_CERTIFICATE = 9851
MARK_BETRAYAL = 9676

class WanderingCaravan(JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onKill (self,npc,player,isPet):
		npcId = npc.getNpcId()
		if npcId == WANDERING_CARAVAN:
			bcertificate = player.getInventory().getItemByItemId(BASIC_CERTIFICATE)
			scertificate = player.getInventory().getItemByItemId(STANDARD_CERTIFICATE)
			if bcertificate and not scertificate:
				player.addItem("Wandering Caravan", MARK_BETRAYAL, 1, player, True, True)
		return
	
QUEST = WanderingCaravan(-1, "WanderingCaravan", "ai")
QUEST.addKillId(WANDERING_CARAVAN)