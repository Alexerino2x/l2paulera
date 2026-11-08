# Psycho(killer1888) / L2jFree
import sys
from com.l2jfree.gameserver.model.quest.jython    import QuestJython as JQuest
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager

qn = "9005_Hude"

# NPC
HUDE = 32298

# Items
BADGE = 9674
BASIC_CERTIFICATE = 9850
STANDART_CERTIFICATE = 9851
PREMIUM_CERTIFICATE = 9852
NATIVE_TREASURE = 9684
HUDE_ITEMS = [9628,9629,9630]

# Transformation
NATIVE_TRANSFORMATION = 101

class Quest (JQuest) :

	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onAdvEvent (self,event,npc,player):
		htmltext = event
		st = player.getQuestState(qn)
		if not st: return
		if event == "tradeall":
			item = player.getInventory().getItemByItemId(BADGE)
			if item.getCount()>=10:
				for step in range(10,item.getCount(),10):
					player.destroyItemByItemId("Hude", BADGE, 10, player, True)
					i = Rnd.get(len(HUDE_ITEMS))
					player.addItem("Hude", HUDE_ITEMS[i], 1, player, True, True)
				return
		if event == "trade":
			item = player.getInventory().getItemByItemId(BADGE)
			if not item:
				player.sendPacket(SystemMessage.sendString("You must have 10 Darion Badges in your Inventory."))	
				return
			elif item.getCount()<10:
				player.sendPacket(SystemMessage.sendString("You must have 10 Darion Badges in your Inventory."))	
				return
			else:
				player.destroyItemByItemId("Hude", BADGE, 10, player, True)
				i = Rnd.get(len(HUDE_ITEMS))
				player.addItem("Hude", HUDE_ITEMS[i], 1, player, True, True)
			return
		return htmltext

	def onFirstTalk (self,npc,player):
		basicCertif = player.getInventory().getItemByItemId(BASIC_CERTIFICATE)
		standartCertif = player.getInventory().getItemByItemId(STANDART_CERTIFICATE)
		premiumCertif = player.getInventory().getItemByItemId(PREMIUM_CERTIFICATE)
		hellboundLevel = HellboundManager.getInstance().getCurrentLevel()
		if hellboundLevel > 2 and hellboundLevel < 4:
			if not basicCertif:
				htmltext = "hude_no.htm"
			elif basicCertif:
				htmltext = "hude.htm"
		elif hellboundLevel >= 4 and hellboundLevel < 7:
			if not basicCertif:
				htmltext = "hude_no.htm"
			elif basicCertif and not standartCertif:
				htmltext = "hude_certificate.htm"
			elif basicCertif and standartCertif:
				htmltext = "hude_basic.htm"
			else:
				htmltext = "hude_no.htm"
		elif hellboundLevel >= 7:
			if not basicCertif:
				htmltext = "hude_no.htm"
			if basicCertif and not standartCertif:
				htmltext = "hude_certificate.htm"
			elif basicCertif and standartCertif and not premiumCertif:
				htmltext = "hude_premium_certificate.htm"
			elif basicCertif and standartCertif and  premiumCertif:
				htmltext = "hude_advanced.htm"
			else:
				htmltext = "hude_no.htm"
		else:
			htmltext = "hude_no.htm"
		return htmltext

QUEST = Quest(9005,qn,"custom")

QUEST.addStartNpc(HUDE)
QUEST.addFirstTalkId(HUDE)
QUEST.addTalkId(HUDE)