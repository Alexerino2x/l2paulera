# By Evil33t and Psycho(killer1888) / L2jFree
# rev 1: Added support for scales (Psycho)
import sys
from java.lang import System
from com.l2jfree.gameserver.datatables import SkillTable
from com.l2jfree.gameserver.instancemanager import InstanceManager
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jfree.tools.random import Rnd
from com.l2jfree.gameserver.gameobjects.ai import CtrlIntention

TEARS      = 25534
TEARS_COPY = 25535
SCALESKILL = 2369

class PyObject:
	pass

def castInvul(self):
	if self.spawned:
		skill = SkillTable.getInstance().getInfo(5225,1)
		if skill != None:
			self.tears.doCast(skill)
			self.startQuestTimer("CastInvul", 180000, None, None)
	return

class Quest (JQuest) :
	def __init__(self,id,name,descr):
		JQuest.__init__(self,id,name,descr)
		self.npcobject = {}
		self.activatedScaleList = []
		self.minionList = []
		self.spawned = False

	def onSpawn (self,npc):
		self.tears = npc
		self.maxHp = npc.getMaxHp()
		self.instanceId = npc.getInstanceId()
		self.maxHp = self.tears.getMaxHp()
		self.InvulCasted = False
		self.spawned = True
		return

	def onAdvEvent(self,event,npc,player):
		if event == "CastInvul":
			castInvul(self)
		elif event == "ResetCasterList":
			self.activatedScaleList = []
		return

	def onAttack (self, npc, player, damage, isPet, skill):
		npcId = npc.getNpcId()
		if npcId == TEARS:
			nowHp = npc.getStatus().getCurrentHp()
			percentage = nowHp / self.maxHp
			try:
				test = self.npcobject[npc.getObjectId()]
			except:
				self.npcobject[npc.getObjectId()] = PyObject()
			try:
				test = self.npcobject[npc.getObjectId()].copylist
			except:
				self.npcobject[npc.getObjectId()].copylist = [] 
			try:
				test = self.npcobject[npc.getObjectId()].isSpawned
			except:
				self.npcobject[npc.getObjectId()].isSpawned = False

			if self.npcobject[npc.getObjectId()].isSpawned:
				for onpc in self.npcobject[npc.getObjectId()].copylist:
					onpc.onDecay()
				self.npcobject[npc.getObjectId()].copylist = [] 
				self.npcobject[npc.getObjectId()].isSpawned = False
				return
			maxHp = npc.getMaxHp()
			nowHp = npc.getStatus().getCurrentHp()
			rand = Rnd.get(0,150)
			if (percentage <= 0.8 and not self.npcobject[npc.getObjectId()].isSpawned) and rand<5:
				party = player.getParty()
				if party :
					for partyMember in party.getPartyMembers().toArray() :
						partyMember.setTarget(None)
						partyMember.abortAttack()
						partyMember.abortCast()
						partyMember.breakAttack();
						partyMember.breakCast();
						partyMember.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE)
				else:
						player.setTarget(None)
						player.abortAttack()
						player.abortCast()
						player.breakAttack();
						player.breakCast();
						player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE)

				self.npcobject[npc.getObjectId()].isSpawned = True
				for i in range(0,10):
					self.npcobject[npc.getObjectId()].copylist.append(self.addSpawn(TEARS_COPY,npc.getX(),npc.getY(),npc.getZ(),0,False,0,False,player.getInstanceId()))

			if percentage <= 0.1 and not self.InvulCasted:
				castInvul(self)
				self.InvulCasted = True

	def onSkillSee(self,npc,caster,skill,targets,isPet):
		npcId = npc.getNpcId()
		skillId = skill.getId()
		if npcId == TEARS:
			if not npc in targets:
				return
			nowHp = self.tears.getCurrentHp()
			percentage = nowHp / self.maxHp
			if percentage <= 0.1:
				if skillId == SCALESKILL:
					if not self.getQuestTimer("ResetCasterList",npc,None):
						self.startQuestTimer("ResetCasterList",3200,npc,None)
					currentTime = System.currentTimeMillis()
					for player in self.activatedScaleList:
						if player[0] == caster:
							return
					thisCasterInfo = [caster,currentTime]
					self.activatedScaleList.append(thisCasterInfo)
					activatedScalesCount = len(self.activatedScaleList)
					playerList = InstanceManager.getInstance().getInstance(self.instanceId).getPlayers().toArray()
					playerCount = len(playerList)
					if activatedScalesCount == playerCount:
						for info in self.activatedScaleList:
							if currentTime > info[1] + 3000:
								self.activatedScaleList = []
								return
						self.tears.getEffects().stopEffects(5225)
						self.activatedScaleList = []

	def onKill(self,npc,player,isPet):
		npcId = npc.getNpcId()
		if npcId == TEARS:
			self.addSpawn(32279,144307,154419,-11857,0,False,0,False, player.getInstanceId())
		return 

QUEST = Quest(-1,"Tears","ai")
QUEST.addAttackId(TEARS)
QUEST.addSpawnId(TEARS)
QUEST.addSkillSeeId(TEARS)
QUEST.addAttackId(TEARS)
QUEST.addKillId(TEARS)
