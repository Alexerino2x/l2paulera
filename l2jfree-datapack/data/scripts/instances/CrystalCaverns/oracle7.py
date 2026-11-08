# Author Psycho(killer1888) / L2jFree

import sys
from com.l2jfree.gameserver.model.quest                 import State
from com.l2jfree.gameserver.model.quest                 import QuestState
from com.l2jfree.gameserver.model.quest.jython          import QuestJython as JQuest
from com.l2jfree.tools.random                           import Rnd
from com.l2jfree.gameserver.datatables                  import ItemTable

ORACLE_GUIDE  = 32280
BLUE_CRYSTAL  = 9695
RED_CRYSTAL   = 9696
CLEAR_CRYSTAL = 9697
BAYLOR        = 29099

CRY = [9695,9696,9697]

class PyObject:
	pass

def exitInstance(player,teleto):
	player.setInstanceId(0)
	player.teleToLocation(teleto.x, teleto.y, teleto.z)
	pet = player.getPet()
	if pet != None :
		pet.setInstanceId(0)
		pet.teleToLocation(teleto.x, teleto.y, teleto.z)

def teleportplayer(player,teleto):
	player.teleToLocation(teleto.x, teleto.y, teleto.z)
	pet = player.getPet()
	if pet != None :
		pet.teleToLocation(teleto.x, teleto.y, teleto.z)
	return

class oracle7(JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.isSpawned = False
		self.baylor = None
		self.instanceId = 0

	def onAdvEvent(self,event,npc,player):
		if event == "startBaylor":
			self.baylor = self.addSpawn(BAYLOR, 154839, 142807, -12718, 32900, False, 0, False, self.instanceId)
			return
		htmltext = event
		if event == "meet":
			item1 = player.getInventory().getItemByItemId(BLUE_CRYSTAL)
			item2 = player.getInventory().getItemByItemId(RED_CRYSTAL)
			item3 = player.getInventory().getItemByItemId(CLEAR_CRYSTAL)
			if not item1 or not item2 or not item3:
				htmltext = "<html><body>Oracle Guide:<br>You do not have all the crystals, I can't let you in.<br><a action=\"bypass -h Quest Quest oracle7 exit\">Let me out!</a></body></html>"
			else:
				player.destroyItemByItemId("Meeting with Baylor", CRY[Rnd.get(len(CRY))], 1, player, True)
				tele = PyObject()
				tele.x = 153570
				tele.y = 142077
				tele.z = -12745
				teleportplayer(player,tele)
				if not self.isSpawned:
					self.instanceId = player.getInstanceId()
					self.startQuestTimer("startBaylor",10000,None,None)
					self.isSpawned = True
					return
		elif event == "exit":
			tele = PyObject()
			tele.x = 149361
			tele.y = 172327
			tele.z = -945
			exitInstance(player,tele)
			return
		return htmltext

	def onTalk (self,npc,player):
		npcId = npc.getNpcId()
		if npcId == ORACLE_GUIDE:
			htmltext = "<html><body>Oracle Guide:<br>You must possess the three crystals, <font color=\"LEVEL\">Clear Crystal, Blue Crystal, Red Crystal</font> in order to meet Baylor<br><br><a action=\"bypass -h Quest oracle7 meet\">Give the crystals</a><br><a action=\"bypass -h Quest oracle7 exit\">Let me out!</a></body></html>"
		return htmltext

QUEST = oracle7(-1, "oracle7", "ai")
QUEST.addStartNpc(ORACLE_GUIDE)
QUEST.addTalkId(ORACLE_GUIDE)