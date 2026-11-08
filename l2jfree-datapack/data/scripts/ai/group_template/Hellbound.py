# Psycho(killer1888) / L2jFree

import sys
from com.l2jfree.gameserver.model.quest.jython    import QuestJython as JQuest
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager

QUEST_RATE = 3

JUNIOR_WATCHMAN    = 22320
JUNIOR_SUMMONER    = 22321
BLIND_HUNTSMAN     = 22324
BLIND_WATCHMAN     = 22325
ARCANE_SCOUT       = 22327
ARCANE_GUARDIAN    = 22328
ARCANE_WATCHMAN    = 22329
REMNANT_DIABOLIST  = 18463
REMNANT_DIVINER    = 18464
DARION_EXECUTIONER = 22343
DARION_ENFORCER    = 22342
KELTAS             = 22341
DEREK              = 18465
HELLINARK          = 22326
OUTPOST_CAPTAIN    = 18466
QUARRY_FOREMAN     = 22346
QUARRY_SUPERVISOR  = 22344
QUARRY_PATROL      = 22347
SUBJUGATED_NATIVE  = 22322
CHARMED_NATIVE     = 22323
NATIVE_SLAVE       = 32357
NATIVE_PRISONER    = 32358

BELETH_CLAN = [
JUNIOR_WATCHMAN,
JUNIOR_SUMMONER,
BLIND_HUNTSMAN,
BLIND_WATCHMAN,
ARCANE_SCOUT,
ARCANE_GUARDIAN,
ARCANE_WATCHMAN,
REMNANT_DIABOLIST,
REMNANT_DIVINER,
DARION_EXECUTIONER,
DARION_ENFORCER,
KELTAS,
DEREK,
HELLINARK,
OUTPOST_CAPTAIN
]

NATIVE_CLAN = {
SUBJUGATED_NATIVE:10,
CHARMED_NATIVE:10,
NATIVE_SLAVE:10,
NATIVE_PRISONER:10
}

MOBS_LVL1 = {
JUNIOR_WATCHMAN:1,
JUNIOR_SUMMONER:1,
BLIND_HUNTSMAN:1,
BLIND_WATCHMAN:1,
ARCANE_SCOUT:3,
ARCANE_GUARDIAN:3,
ARCANE_WATCHMAN:3
}

MOBS_LVL2 = {
REMNANT_DIABOLIST:5,
REMNANT_DIVINER:5
}

MOBS_LVL3 = {
DARION_EXECUTIONER:3,
DARION_ENFORCER:3,
KELTAS:100
}

MOBS_LVL4 = {
DEREK:10000
}

MOBS_LVL6 = {
HELLINARK:10000
}

MOBS_LVL8 = {
OUTPOST_CAPTAIN:10000
}


class Hellbound (JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onKill (self,npc,player,isPet):
		npcId = npc.getNpcId()
		if NATIVE_CLAN.has_key(npcId):
			HellboundManager.getInstance().decreaseTrustPoints(NATIVE_CLAN[npcId] * QUEST_RATE)
		else:
			hellboundLevel = HellboundManager.getInstance().getHellboundLevel()
			if npcId in MOBS_LVL1 and hellboundLevel == 1:
				HellboundManager.getInstance().addTrustPoints(MOBS_LVL1[npcId] * QUEST_RATE)
			elif npcId in MOBS_LVL2 and hellboundLevel == 2:
				HellboundManager.getInstance().addTrustPoints(MOBS_LVL2[npcId] * QUEST_RATE)
			elif npcId in MOBS_LVL3 and hellboundLevel == 3:
				HellboundManager.getInstance().addTrustPoints(MOBS_LVL3[npcId] * QUEST_RATE)
			elif npcId in MOBS_LVL4 and hellboundLevel == 4:
				HellboundManager.getInstance().addTrustPoints(MOBS_LVL4[npcId] * QUEST_RATE)
			elif npcId in MOBS_LVL6 and hellboundLevel == 6:
				HellboundManager.getInstance().addTrustPoints(MOBS_LVL6[npcId] * QUEST_RATE)
			elif npcId in MOBS_LVL8 and hellboundLevel == 8:
				HellboundManager.getInstance().addTrustPoints(MOBS_LVL8[npcId] * QUEST_RATE)
		return

QUEST = Hellbound(-2,"Hellbound","ai")

for i in BELETH_CLAN:
	QUEST.addKillId(i)
for i in NATIVE_CLAN.keys():
	QUEST.addKillId(i)