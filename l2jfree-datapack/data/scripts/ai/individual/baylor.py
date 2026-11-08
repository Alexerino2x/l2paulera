# Psycho(killer1888) / L2jFree

import sys
from java.lang                                          import System
from com.l2jfree.gameserver.gameobjects.ai                          import CtrlIntention
from com.l2jfree.gameserver.datatables                  import SkillTable
from com.l2jfree.gameserver.instancemanager             import InstanceManager
from com.l2jfree.gameserver.model                       import L2CharPosition
from com.l2jfree.gameserver.model.world                       import L2World
from com.l2jfree.gameserver.gameobjects.instance        import L2MonsterInstance
from com.l2jfree.gameserver.model.entity                import Instance
from com.l2jfree.gameserver.gameobjects.itemcontainer         import Inventory
from com.l2jfree.gameserver.model.quest                 import QuestState
from com.l2jfree.gameserver.model.quest                 import State
from com.l2jfree.gameserver.model.quest.jython          import QuestJython as JQuest
from com.l2jfree.gameserver.network.packets.server       import MagicSkillUse
from com.l2jfree.gameserver.network.packets.server       import SocialAction
from com.l2jfree.tools.random                           import Rnd

#NPC
STATUE         = 32109
BAYLOR         = 29099
MINION         = 29104
CHEST          = 29116
CLAWSKILL      = 2360
CRYSTALINE     = 29101
PRISONKEYSKILL = 2362
ALARM          = 18474
BERSERK        = 5224

DOORLIST       = [24220009,24220011,24220012,24220014,24220015,24220016,24220017,24220019]

class PyObject:
    pass

def spawnMinion(self):
    self.minions = []
    newNpc = self.addSpawn(MINION, self.baylor.getX() + 350, self.baylor.getY(), self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() + 303, self.baylor.getY() + 175, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() + 175, self.baylor.getY() + 303, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX(), self.baylor.getY() + 350, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() - 175, self.baylor.getY() + 303, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() - 303, self.baylor.getY() + 175, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() - 350, self.baylor.getY(), self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() - 303, self.baylor.getY() - 175, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() - 175, self.baylor.getY() - 303, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX(), self.baylor.getY() - 350, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() + 175, self.baylor.getY() - 303, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    newNpc = self.addSpawn(MINION, self.baylor.getX() + 303, self.baylor.getY() - 175, self.baylor.getZ(), 0, False, 0, False, self.instanceId)
    self.minions.append(newNpc)
    self.canSpawnMinion = False
    return

def spawnAlarms(self):
    self.alarms = PyObject()
    self.alarms.npclist = {}
    newNpc = self.addSpawn(ALARM, 154364, 142077, -12740, 32670, False, 0, False, self.instanceId)
    self.alarms.npclist[newNpc] = False
    newNpc = self.addSpawn(ALARM, 153571, 142860, -12740, 48900, False, 0, False, self.instanceId)
    self.alarms.npclist[newNpc] = False
    newNpc = self.addSpawn(ALARM, 152786, 142077, -12740, 62040, False, 0, False, self.instanceId)
    self.alarms.npclist[newNpc] = False
    newNpc = self.addSpawn(ALARM, 153571, 141276, -12740, 14903, False, 0, False, self.instanceId)
    self.alarms.npclist[newNpc] = False
    self.alarmCount += 1
    skill = SkillTable.getInstance().getInfo(BERSERK,1)
    newNpc.setTarget(self.baylor)
    if skill != None:
        newNpc.doCast(skill)
    return

def spawnCrystaline(self):
    self.crystalineList = []
    newNpc = self.addSpawn(CRYSTALINE, 154982, 141267, -12713, 26339, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 154391, 140634, -12713, 21535, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 153565, 140431, -12713, 17164, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 152124, 141263, -12713, 4566, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 151905, 142085, -12713, 64460, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 152737, 143536, -12713, 53120, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 154403, 143509, -12713, 43786, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    newNpc = self.addSpawn(CRYSTALINE, 155264, 142084, -12713, 32767, False, 0, False, self.instanceId)
    self.crystalineList.append(newNpc)
    return

def castInvul(self):
    if self.spawned:
        skill = SkillTable.getInstance().getInfo(5225,1)
        if skill != None:
            self.baylor.doCast(skill)
            self.startQuestTimer("CastInvul", 300000, None, None)
    return

def checkKillProgress(npc,room):
    cont = True
    if room.npclist.has_key(npc):
        room.npclist[npc] = True
    for npc in room.npclist.keys():
        if room.npclist[npc] == False:
            cont = False
    return cont

class baylor (JQuest):

    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.baylor = None
        self.spawned = False
        self.maxHp = 99999
        self.activatedClawList = []
        self.InvulCasted = False
        self.alarmSpawned = False
        self.alarmCount = 0
        self.canSpawnMinion = True
        self.minions = []

    def onAdvEvent(self,event,npc,player):
        if event == "CastInvul":
            castInvul(self)
        elif event == "Crystaline":
            npc.broadcastPacket(MagicSkillUse(npc, npc, 5441, 1, 1, 0))
            self.startQuestTimer("DespawnCrystaline",5000,npc,None)
        elif event == "DespawnCrystaline":
            npc.decayMe()
        elif event == "CanSpawnMinion":
            self.canSpawnMinion = True
        elif event == "ResetCasterList":
            self.activatedClawList = []
        return

    def onSpawn (self,npc):
        self.spawned = True
        self.baylor = npc
        self.maxHp = npc.getMaxHp()
        self.instanceId = npc.getInstanceId()
        playerList = InstanceManager.getInstance().getInstance(self.instanceId).getPlayers().toArray()
        self.playerCount = len(playerList)
        self.maxHp = self.baylor.getMaxHp()
        spawnCrystaline(self)
        for doorId in DOORLIST:
            for door in InstanceManager.getInstance().getInstance(self.instanceId).getDoors():
                if door.getDoorId() == doorId:
                    door.closeMe()
        npc.setRunning()
        npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(153566,142071,-12745,32900))
        return

    def onAttack(self, npc, player, damage, isPet, skill):
        npcId = npc.getNpcId()
        nowHp = self.baylor.getCurrentHp()
        if not self.alarmSpawned:
            spawnAlarms(self)
            self.alarmSpawned = True
        if npcId == BAYLOR:
            if not self.alarmSpawned:
                self.alarmSpawned = True
                spawnAlarms(self)
            random = Rnd.get(100)
            if random <= 1 and self.canSpawnMinion:
                spawnMinion(self)
            percentage = nowHp / self.maxHp
            if percentage <= 0.2 and not self.InvulCasted:
                castInvul(self)
                self.InvulCasted = True
        return

    def onSkillSee(self,npc,caster,skill,targets,isPet):
        npcId = npc.getNpcId()
        skillId = skill.getId()
        if npcId == CRYSTALINE:
            if skillId == PRISONKEYSKILL:
                if caster.isInsideRadius(npc, 350, False, False):
                    baylorPosition = L2CharPosition(self.baylor.getX() + 100, self.baylor.getY() + 100, self.baylor.getZ(), 0)
                    npc.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, baylorPosition)
                    self.startQuestTimer("Crystaline",20000,npc,None)
                    return
        if npcId == BAYLOR:
            if not npc in targets:
                return
            nowHp = self.baylor.getCurrentHp()
            percentage = nowHp / self.maxHp
            if percentage <= 0.2:
                if skillId == CLAWSKILL:
                    if not self.getQuestTimer("ResetCasterList",npc,None):
                        self.startQuestTimer("ResetCasterList",3200,npc,None)
                    currentTime = System.currentTimeMillis()
                    for player in self.activatedClawList:
                        if player[0] == caster:
                            return
                    thisCasterInfo = [caster,currentTime]
                    self.activatedClawList.append(thisCasterInfo)
                    activatedClawsCount = len(self.activatedClawList)
                    if activatedClawsCount == self.playerCount:
                        for info in self.activatedClawList:
                            if currentTime > info[1] + 3000:
                                self.activatedClawList = []
                                return
                        skill = SkillTable.getInstance().getInfo(5480,1)
                        if skill != None:
                            self.baylor.doCast(skill)
                        self.baylor.getEffects().stopEffects(5225)
                        self.activatedClawList = []
        return

    def onKill (self,npc,player,isPet):
        st = player.getQuestState("baylor")
        npcId = npc.getNpcId()
        if npcId == ALARM and self.alarmSpawned and self.alarmCount < 6:
            if checkKillProgress(npc,self.alarms):
                if self.alarmCount < 5:
                    spawnAlarms(self)
                else:
                    self.baylor.getEffects().stopEffects(BERSERK)
        if npcId == MINION and not self.canSpawnMinion:
            if self.getQuestTimer("CanSpawnMinion", None, None) == None:
                self.startQuestTimer("CanSpawnMinion", 300000, None, None)
        if npcId == BAYLOR:
            self.spawned = False
            for mob in self.minions:
                mob.decayMe()
            for mob in self.alarms.npclist:
                mob.decayMe()
            for mob in self.crystalineList:
                mob.decayMe()
            self.addSpawn(CHEST, 153763, 142075, -12741, 64792, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153701, 141942, -12741, 57739, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153573, 141894, -12741, 49471, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153445, 141945, -12741, 41113, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153381, 142076, -12741, 32767, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153441, 142211, -12741, 25730, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153573, 142260, -12741, 16185, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153706, 142212, -12741, 7579, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153571, 142860, -12741, 16716, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 152783, 142077, -12741, 32176, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 153571, 141274, -12741, 49072, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 154365, 142073, -12741, 64149, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 154192, 142697, -12741, 7894, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 152924, 142677, -12741, 25072, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 152907, 141428, -12741, 39590, False, 0, False, self.instanceId)
            self.addSpawn(CHEST, 154243, 141411, -12741, 55500, False, 0, False, self.instanceId)
            self.addSpawn(32273, 153569, 142077, -12740, 55500, False, 0, False, self.instanceId)
            playerList = InstanceManager.getInstance().getInstance(player.getInstanceId()).getPlayers()
            for member in playerList.toArray():
                member = L2World.getInstance().findPlayer(member)
                st = member.getQuestState("350_EnhanceYourWeapon")
                if not st:
                    return
                if st.getState() == State.STARTED:
                    if st.getQuestItemsCount(5914) == 1 or st.getQuestItemsCount(5911) == 1 or st.getQuestItemsCount(5908) == 1:
                         random = Rnd.get(100)
                         GREEN = False
                         RED = False
                         BLUE = False
                         if st.getQuestItemsCount(5914) == 1:
                             BLUE = True
                         elif st.getQuestItemsCount(5911) == 1:
                             GREEN = True
                         elif st.getQuestItemsCount(5908) == 1:
                             RED = True
                         if BLUE:
                             st.takeItems(5914, 1)
                             if random >= 50:
                                 st.giveItems(10161, 1)
                             else:
                                 st.giveItems(9571, 1)
                         elif GREEN:
                             st.takeItems(5911, 1)
                             if random >= 50:
                                 st.giveItems(10162, 1)
                             else:
                                 st.giveItems(9572, 1)
                         elif RED:
                             st.takeItems(5908, 1)
                             if random >= 50:
                                 st.giveItems(10160, 1)
                             else:
                                 st.giveItems(9570, 1)
        return

QUEST = baylor(-1, "baylor", "ai")

QUEST.addStartNpc(STATUE)
QUEST.addSpawnId(BAYLOR)
QUEST.addKillId(BAYLOR)
QUEST.addKillId(ALARM)
QUEST.addKillId(MINION)
QUEST.addSkillSeeId(BAYLOR)
QUEST.addSkillSeeId(CRYSTALINE)
QUEST.addAttackId(BAYLOR)