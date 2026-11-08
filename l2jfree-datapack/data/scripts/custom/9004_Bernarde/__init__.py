# Psycho(killer1888) / L2jFree
import sys
from com.l2jfree.gameserver.model.quest.jython    import QuestJython as JQuest
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager

qn = "9004_Bernarde"

# NPC
BERNARDE = 32300

# Items
NATIVE_TREASURE = 9684

# Transformation
NATIVE_TRANSFORMATION = 101

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onAdvEvent (self,event,npc,player):
		htmltext = event
		st = player.getQuestState(qn)
		if not st: return
		if event == "treasure":
			if player.getInventory().getItemByItemId(NATIVE_TREASURE) >= 1:
				player.destroyItemByItemId("Bernarde Exchange", NATIVE_TREASURE, 1, player, True)
				trustPoints = HellboundManaer.getInstance().getTrustPoints()
				trustToAdd = HellboundManager.getInstance().getNeededTrustPoints(4) - trustPoints
				HellboundManager.getinstance().addTrustPoints(trustToAdd)
				htmltext = "bernarde_thanks.htm"
		return htmltext

	def onFirstTalk (self,npc,player):
		if player.isTransformed() and player.getTransformationId() == NATIVE_TRANSFORMATION:
			hellboundLevel = HellboundManager.getInstance().getCurrentLevel()
			trustPoints = HellboundManager.getInstance().getTrustPoints()
			if trustPoints < 999000:
				htmltext = "bernarde_trade.htm"
			elif trustPoints >= 999000 and hellboundLevel < 4:
				htmltext = "bernarde_advanced.htm"
			else:
				htmltext = "bernarde.htm"
		else:
			htmltext = "bernarde_no.htm"
		return htmltext

QUEST = Quest(9004,qn,"custom")

QUEST.addStartNpc(BERNARDE)
QUEST.addTalkId(BERNARDE)
QUEST.addFirstTalkId(BERNARDE)