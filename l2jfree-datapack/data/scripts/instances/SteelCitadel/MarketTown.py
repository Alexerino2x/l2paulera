# author: Psycho(killer1888) / L2jFree

import sys
from com.l2jfree.gameserver.gameobjects.ai                          import CtrlIntention
from com.l2jfree.gameserver.datatables                  import ItemTable
from com.l2jfree.gameserver.instancemanager             import InstanceManager
from com.l2jfree.gameserver.instancemanager.hellbound   import HellboundManager
from com.l2jfree.gameserver.model                       import L2CharPosition
from com.l2jfree.gameserver.model.world                       import L2World
from com.l2jfree.gameserver.model.entity                import Instance
from com.l2jfree.gameserver.model.quest                 import State
from com.l2jfree.gameserver.model.quest                 import QuestState
from com.l2jfree.gameserver.model.quest.jython          import QuestJython as JQuest
from com.l2jfree.gameserver.network                     import SystemMessageId
from com.l2jfree.gameserver.network.packets.server       import NpcSay
from com.l2jfree.gameserver.network.packets.server       import SystemMessage
from com.l2jfree.tools.random                           import Rnd

qn = "MarketTown"

# Npcs
KANAF          = 32346
PRISONER       = 32358
STELE          = 32343
CHARMED_NATIVE = 22323

# Mobs
AMASKARI       = 22449
KEYMASTER      = 22361
GUARD          = 22359

# Items
KEY            = 9714

AMASKARI_TEXT = ['Slimebags death awaits you!','Little humans, what a suprise','First i kill you then the natives you tried to free','Lord Beleth will not be pleased','Not you again...']

SLAVES_TEXT = ['Finally free!','Thank you for your help!','I\'m going home!','Take good care of you my friend!']

LOCS = [
    [14479,250398,-1940],
    [15717,252399,-2013],
    [19800,256230,-2091],
    [17214,252770,-2015],
    [21967,254035,-2010]
]

class PyObject:
    pass

def sendSlaves(self,player,world):
    playerList = InstanceManager.getInstance().getInstance(player.getInstanceId()).getPlayers().toArray()
    for slave in world.AmaskariSlaves.npclist:
        char = playerList[Rnd.get(len(playerList))]
        player = L2World.getInstance().findPlayer(char)
        slave.setRunning()
        slave.addDamageHate(player, 0, 999)
        slave.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)

def callGuards(self,npc,player,world):
    guardList = []
    newNpc = self.addSpawn(GUARD, npc.getX() + 50, npc.getY(), npc.getZ(), 0, False, 0, False, world.instanceId)
    guardList.append(newNpc)
    newNpc = self.addSpawn(GUARD, npc.getX() - 50, npc.getY(), npc.getZ(), 0, False, 0, False, world.instanceId)
    guardList.append(newNpc)
    newNpc = self.addSpawn(GUARD, npc.getX(), npc.getY() + 50, npc.getZ(), 0, False, 0, False, world.instanceId)
    guardList.append(newNpc)
    newNpc = self.addSpawn(GUARD, npc.getX(), npc.getY() - 50, npc.getZ(), 0, False, 0, False, world.instanceId)
    guardList.append(newNpc)
    for mob in guardList:
        mob.setRunning()
        mob.addDamageHate(player, 0, 999)
        mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player)

def spawnAmaskari(self,world):
    world.amaskariAttacked = False
    amaskari = self.addSpawn(AMASKARI, 19496, 253125, -2030, 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves = PyObject()
    world.AmaskariSlaves.npclist = []
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX(), amaskari.getY() + 50, amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX(), amaskari.getY() - 50, amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX() +50, amaskari.getY(), amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX() - 50, amaskari.getY(), amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX() + 50, amaskari.getY() + 50, amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX() -50 , amaskari.getY() - 50, amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX() + 50, amaskari.getY() - 50, amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)
    newNpc = self.addSpawn(CHARMED_NATIVE, amaskari.getX() -50, amaskari.getY() + 50, amaskari.getZ(), 0, False, 0, False, world.instanceId)
    world.AmaskariSlaves.npclist.append(newNpc)

def spawnKeyMaster(self,world):
    loc = LOCS[Rnd.get(len(LOCS))]
    world.keymaster = self.addSpawn(KEYMASTER, loc[0], loc[1], loc[2], 0, False, 0, False, world.instanceId)

def dropItem(npc,itemId,count,player):
    ditem = ItemTable.getInstance().createItem("Market Town KeyMaster", itemId, count, player)
    ditem.dropMe(npc, npc.getX(), npc.getY(), npc.getZ())

def checkCondition(player):
    party = player.getParty()
    if not party:
        player.sendPacket(SystemMessage(SystemMessageId.NOT_IN_PARTY_CANT_ENTER))    
        return False
    else:
        st = player.getQuestState(qn)
        partyLeader = st.getPlayer().getParty().getLeader()
        if player != partyLeader:
            player.sendPacket(SystemMessage(SystemMessageId.ONLY_PARTY_LEADER_CAN_ENTER))
            return
        for partyMember in party.getPartyMembers().toArray():
            if not partyMember.isInsideRadius(player, 500, True, True):
                sm = SystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED)
                sm.addPcName(partyMember)
                player.sendPacket(sm)
                return False
            if not partyMember.getLevel() >= 78:
                sm = SystemMessage(SystemMessageId.C1_LEVEL_REQUIREMENT_NOT_SUFFICIENT)
                sm.addPcName(partyMember)
                player.sendPacket(sm)
                partyMember.sendPacket(sm)
                return False
    return True

def teleportplayer(self,player,teleto):
    player.teleToLocation(teleto.x, teleto.y, teleto.z)
    player.setInstanceId(teleto.instanceId)
    pet = player.getPet()
    if pet != None :
        pet.setInstanceId(teleto.instanceId)
        pet.teleToLocation(teleto.x, teleto.y, teleto.z)
    return

def enterInstance(self,player,template,teleto):
    instanceId = 0
    if not checkCondition(player):
        return 0
    party = player.getParty()
    # Check for existing instances of party members
    for partyMember in party.getPartyMembers().toArray():
        if partyMember.getInstanceId()!=0:
            sm = SystemMessage(SystemMessageId.C1_IS_IN_LOCATION_THAT_CANNOT_BE_ENTERED)
            sm.addPcName(partyMember)
            player.sendPacket(sm)
            return 0
    instanceId = InstanceManager.getInstance().createDynamicInstance(template)
    world = PyObject()
    world.instanceId = instanceId
    world.instanceFinished = False
    world.guardsSpawned = False
    world.status = 0
    self.worlds[instanceId] = world
    self.world_ids.append(instanceId)
    spawnKeyMaster(self,world)
    # Teleports player
    teleto.instanceId = instanceId
    for partyMember in party.getPartyMembers().toArray():
        st = partyMember.getQuestState(qn)
        if not st:
            self.newQuestState(partyMember)
        teleportplayer(self,partyMember,teleto)
    return instanceId

class MarketTown(JQuest):

    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.worlds = {}
        self.world_ids = []

    def onAdvEvent(self,event,npc,player):
        if event == "teleportKeyMaster":
            world = self.worlds[npc.getInstanceId()]
            loc = LOCS[Rnd.get(len(LOCS))]
            world.keymaster.teleToLocation(loc[0], loc[1], loc[2])
        elif event == "decayNpc":
            npc.decayMe()
        elif event == "finishInstance":
            world = self.worlds[npc.getInstanceId()]
            playerList = InstanceManager.getInstance().getInstance(npc.getInstanceId()).getPlayers()
            for obj in playerList.toArray():
                member = L2World.getInstance().findPlayer(obj)
                member.setInstanceId(0)
                member.teleToLocation(13105, 282099, -9701)
        return

    def onTalk (self,npc,player):
        npcId = npc.getNpcId()
        if npcId == KANAF :
            if HellboundManager.getInstance().getCurrentLevel() >= 10:
                instanceId = 0
                tele = PyObject()
                tele.x = 13881
                tele.y = 255491
                tele.z = -2025
                instanceId = enterInstance(self,player,"MarketTown.xml",tele)
                if instanceId == 0:
                    return
            else:
                htmltext = "You may not yet enter the Market Town..."
                return htmltext
        elif npcId == STELE:
            world = self.worlds[npc.getInstanceId()]
            if not world.instanceFinished:
                key = player.getInventory().getItemByItemId(KEY);
                if key != None:
                    world.instanceFinished = True
                    player.destroyItemByItemId("Moonlight Stone", KEY, 1, player, True);
                    instance = InstanceManager.getInstance().getInstance(npc.getInstanceId())
                    if instance != None:
                        instance.setDuration(330000)
                        self.startQuestTimer("finishInstance", 300000, npc, None)
        elif npcId == PRISONER:
            world = self.worlds[npc.getInstanceId()]
            npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npc.getNpcId(),"Thank you, i hope Amaskari wont notice!"))
            self.startQuestTimer("decayNpc", 15000, npc, None)
            HellboundManager.getInstance().addTrustPoints(10)
            if not world.guardsSpawned:
                callGuards(self,npc,player,world)
                world.guardsSpawned = True
        return

    def onKill(self,npc,player,isPet):
        if self.worlds.has_key(npc.getInstanceId()):
            world = self.worlds[npc.getInstanceId()]
            npcId = npc.getNpcId()
            npc.getSpawn().stopRespawn()
            if npcId == KEYMASTER:
                if Rnd.get(100) >= 40:
                    text = "Oh no my key............."
                    dropItem(npc,KEY,1,player)
                    spawnAmaskari(self,world)
                else:
                    text = "You will never get my key!"
                npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npcId,text))
            elif npcId == CHARMED_NATIVE:
                HellboundManager.getInstance().decreaseTrustPoints(10)
            elif npcId == AMASKARI:
                for slave in world.AmaskariSlaves.npclist:
                    slave.setRunning()
                    slave.getAI().setIntention(CtrlIntention.AI_INTENTION_MOVE_TO, L2CharPosition(17384, 251788, -2015, 0))
                    self.startQuestTimer("decayNpc",8000,slave,None)
                    slave.broadcastPacket(NpcSay(slave.getObjectId(),0,CHARMED_NATIVE,SLAVES_TEXT[Rnd.get(len(SLAVES_TEXT))]))
        return
                
    def onAttack(self,npc,player,damage,isPet,skill):
        st = player.getQuestState(qn)
        if self.worlds.has_key(npc.getInstanceId()):
            world = self.worlds[npc.getInstanceId()]
            npcId = npc.getNpcId()
            if npcId == AMASKARI:
                if not world.amaskariAttacked:
                    world.amaskariAttacked = True
                    sendSlaves(self,player,world)
                    text = AMASKARI_TEXT[Rnd.get(len(AMASKARI_TEXT))]
                    npc.broadcastPacket(NpcSay(npc.getObjectId(),0,npcId,text))
        return

QUEST = MarketTown(-1, qn, "instances")

QUEST.addStartNpc(KANAF)
QUEST.addStartNpc(PRISONER)
QUEST.addStartNpc(STELE)
QUEST.addTalkId(KANAF)
QUEST.addTalkId(PRISONER)
QUEST.addTalkId(STELE)
QUEST.addAttackId(AMASKARI)
QUEST.addKillId(KEYMASTER)
QUEST.addKillId(AMASKARI)