# Author: Psycho(killer1888) / L2jFree
import sys
from com.l2jfree.gameserver.gameobjects.ai 						import CtrlIntention
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager
from com.l2jfree.gameserver.network.packets.server 	import NpcSay
from com.l2jfree.gameserver.model.quest 			import State
from com.l2jfree.gameserver.model.quest 			import QuestState
from com.l2jfree.gameserver.model.quest.jython 		import QuestJython as JQuest
from com.l2jfree.gameserver.gameobjects.instance    import L2MonsterInstance

debug = False

def cancelTimers(self,npc):
	if self.getQuestTimer("CheckIfSafe",npc,None):
		self.getQuestTimer("CheckIfSafe",npc,None).cancel()
	if self.getQuestTimer("CallKillers",npc,None):
		self.getQuestTimer("CallKillers",npc,None).cancel()
	return

class QuarrySlave(JQuest):
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)

	def onAdvEvent (self,event,npc, player) :
		if event == "CheckIfSafe":
			if npc.isDead():
				cancelTimers(self,npc)
				HellboundManager.getInstance().decreaseTrustPoints(30)
				return
			if (npc.getX() >= -5967 and npc.getX() <= -4163) and (npc.getY() >= 251137 and npc.getY() <= 251970) and (npc.getZ() >= -3400 and npc.getZ() <= -3100):
				HellboundManager.getInstance().addTrustPoints(30)
				npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),"Thank you, you saved me! I'll remember you my whole life!"))
				npc.decayMe()
				cancelTimers(self,npc)
			else:
				if debug:
					print "Not on position. Currently: X: " +str(npc.getX())+ " Y: " +str(npc.getY())+ " Z: " +str(npc.getZ())
				self.startQuestTimer("CheckIfSafe",10000,npc,None)
		elif event == "CallKillers":
			if npc.isDead():
				cancelTimers(self,npc)
				HellboundManager.getInstance().decreaseTrustPoints(30)
				return
			for object in npc.getKnownList().getKnownObjects().values():
				if object != None:
					if isinstance(object, L2MonsterInstance):
						objectId = object.getNpcId()
						if objectId in [22347,22344,22346]:
							object.setTarget(npc)
							object.addDamageHate(npc, 0, 999)
							object.setIsRunning(True)
							object.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK)
			self.startQuestTimer("CallKillers",1000,npc,None)
		return

	def onTalk (self,npc,player):
		npcId = npc.getNpcId()
		if npcId == 32299 and HellboundManager.getInstance().getHellboundLevel() == 5:
			npc.setTarget(player);
			npc.setRunning()
			npc.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, player)
			self.startQuestTimer("CheckIfSafe",10000,npc,None)
			self.startQuestTimer("CallKillers",1000,npc,None)
		return
	
QUEST = QuarrySlave(-1, "QuarrySlave", "custom")
QUEST.addStartNpc(32299)
QUEST.addTalkId(32299)
QUEST.addKillId(32299)
