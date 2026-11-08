import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "9002_Buron"

class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

	def onTalk (self,npc,player):
		item = player.getInventory().getItemByItemId(9850)
		if item:
			return "trade.htm"
		else:
			return "no.htm"

QUEST = Quest(9002,qn,"custom")

QUEST.addStartNpc(32345)
QUEST.addTalkId(32345)
