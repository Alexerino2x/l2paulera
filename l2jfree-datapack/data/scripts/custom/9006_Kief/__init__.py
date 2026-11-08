# Psycho(killer1888) / L2jFree
import sys
from com.l2jfree.gameserver.model.quest.jython    import QuestJython as JQuest
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager

qn = "9006_Kief"

# NPC
KIEF = 32354

# Items
BADGE = 9674
DIMLIFEFORCE = 9680
REGULARLIFEFORCE = 9681
CONTAINEDLIFEFORCE = 9682

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onAdvEvent (self,event,npc,player):
		htmltext = event
		st = player.getQuestState(qn)
		if not st: return
		if event == "exchangeBadges":
			if player.getInventory().getItemByItemId(BADGE) >= 1:
				player.destroyItemByItemId("Kief Exchange", BADGE, 1, player, True)
				trustPoints = HellboundManager.getInstance().getTrustPoints()
				if trustPoints + 10 > 999000:
					points = 999000 - trustPoints
				else:
					points = 10
				HellboundManager.getInstance().addTrustPoints(points)
				htmltext = "kief_thanks.htm"
			else:
				htmltext = "<html><body>You don't have any Darion's Badge...</body></html>"
		elif event == "exchangeAllBadges":
			if player.getInventory().getItemByItemId(BADGE) >= 1:
				count = st.getQuestItemsCount(BADGE)
				player.destroyItemByItemId("Kief Exchange", BADGE, count, player, True)
				trustPoints = HellboundManager.getInstance().getTrustPoints()
				if trustPoints + (10 * count) > 999000:
					points = 999000 - trustPoints
				else:
					points = 10 * count
				HellboundManager.getInstance().addTrustPoints(points)
				htmltext = "kief_thanks.htm"
			else:
				htmltext = "<html><body>You don't have any Darion's Badge...</body></html>"
		elif event == "exchangeDim":
			if player.getInventory().getItemByItemId(DIMLIFEFORCE) >= 1:
				player.destroyItemByItemId("Kief Exchange", DIMLIFEFORCE, 1, player, True)
				trustPoints = HellboundManager.getInstance().getTrustPoints()
				HellboundManager.getInstance().addTrustPoints(5)
				htmltext = "kief_thanks.htm"
			else:
				htmltext = "<html><body>You don't have any Dim Life Force...</body></html>"
		elif event == "exchangeRegular":
			if player.getInventory().getItemByItemId(REGULARLIFEFORCE) >= 1:
				player.destroyItemByItemId("Kief Exchange", REGULARLIFEFORCE, 1, player, True)
				HellboundManager.getInstance().addTrustPoints(10)
				htmltext = "kief_thanks.htm"
			else:
				htmltext = "<html><body>You don't have any Regular Life Force...</body></html>"
		elif event == "exchangeContained":
			if player.getInventory().getItemByItemId(CONTAINEDLIFEFORCE) >= 1:
				player.destroyItemByItemId("Kief Exchange", CONTAINEDLIFEFORCE, 1, player, True)
				HellboundManager.getInstance().addTrustPoints(25)
				htmltext = "kief_thanks.htm"
			else:
				htmltext = "<html><body>You don't have any Contained Life Force...</body></html>"
		return htmltext

	def onFirstTalk (self,npc,player):
		level = HellboundManager.getInstance().getHellboundLevel()
		if HellboundManager.getInstance().getTrustPoints() < 999000:
			htmltext = "kief_exchange.htm"
		elif level >= 7 and level < 8:
			htmltext = "kief_trade_extended.htm"
		else:
			htmltext = "kief_trade.htm"
		return htmltext

QUEST = Quest(9006,qn,"custom")

QUEST.addStartNpc(KIEF)
QUEST.addFirstTalkId(KIEF)
QUEST.addTalkId(KIEF)