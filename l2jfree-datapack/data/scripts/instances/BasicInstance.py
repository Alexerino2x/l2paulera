# author: evill33t
from com.l2jfree.gameserver.network.packets.server       import NpcSay
from com.l2jfree.tools.random                           import Rnd
from com.l2jfree.gameserver.gameobjects.itemcontainer         import PlayerInventory
from com.l2jfree.gameserver.model                       import L2ItemInstance
from com.l2jfree.gameserver.network.packets.server       import InventoryUpdate
from com.l2jfree.gameserver.model                       import L2World
from com.l2jfree.gameserver.datatables                  import ItemTable
from com.l2jfree.gameserver.instancemanager             import InstanceManager
from com.l2jfree.gameserver.model.entity                import Instance
from com.l2jfree.gameserver.gameobjects                 import L2Summon
from com.l2jfree.gameserver.model.quest                 import State
from com.l2jfree.gameserver.model.quest                 import QuestState
from com.l2jfree.gameserver.model.quest.jython          import QuestJython as JQuest
from com.l2jfree.gameserver.network.packets.server import SystemMessage
from com.l2jfree.gameserver.network import SystemMessageId

class PyObject:
    pass

class TeleTo:
    def __init__(self,x,y,z,instanceId=0):
        self.x=x
        self.y=y
        self.z=z
        self.instanceId=instanceId

class BasicInstance(JQuest):
    MEMBERS_IN_OTHER_INSTANCE = "Your Party Members are in another Instance."

    # Creates the "master" quest and a list that holds the running instances
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.worlds = {}
        self.world_ids = []

    # Very basic instance setup
    def setupWorld(self,instanceId):
        world = PyObject()
        world.instanceId = instanceId
        return world

    def teleportGroup(self,teleto,player,instant=True,inInstance=True,delay=30000):
        world = self.getWorld(player)
        if not world:
            inInstance = False
        if not instant or not world:
            world.nextTeleLoc = teleto
            self.startQuestTimer("teleportNext",delay,None,player)
        else:
            self.teleportPlayer(player,teleto)
            members = self.getMembers(player,inInstance)
            for member in members :
                self.teleportPlayer(member,teleto)
        return world

    def teleportPlayer(self,player,teleto):
        player.setInstanceId(teleto.instanceId)
        player.teleToLocation(teleto.x, teleto.y, teleto.z)
        pet = player.getPet()
        if pet != None :
            pet.setInstanceId(teleto.instanceId)
            pet.teleToLocation(teleto.x, teleto.y, teleto.z)

    def getMembers(self,player,inInstance=False):
        party = player.getParty()
        if party != None :
            channel = party.getCommandChannel()
            if channel != None :
                members = channel.getMembers().toArray()
            else:
                members = party.getPartyMembers().toArray()
        else:
            members = []
        if inInstance:
            newmembers = []
            for member in members:
                if member.getInstanceId()==player.getInstanceId():
                    newmembers.append(member)
            members = newmembers
        return members

    def checkKillProgress(self,npc,room):
        cont = True
        if room.npclist.has_key(npc):
            room.npclist[npc] = True
        for npc in room.npclist.keys():
            if room.npclist[npc] == False:
                cont = False
        if self._log.isDebugEnabled():
            self._log.info(room.npclist)
        return cont

    def dropItem(self,npc,itemId,count,player):
        ditem = ItemTable.getInstance().createItem("Loot", itemId, count, player)
        ditem.dropMe(npc, npc.getX(), npc.getY(), npc.getZ())

    # Opens a door inside the instance
    def openDoor(self,doorId,world):
        for door in InstanceManager.getInstance().getInstance(world.instanceId).getDoors():
            if door.getDoorId() == doorId:
                door.openMe()

    # Closes a door inside the instance
    def closeDoor(self,doorId,world):
        for door in InstanceManager.getInstance().getInstance(world.instanceId).getDoors():
            if door.getDoorId() == doorId:
                door.closeMe()

    # Returns all players inside the instance
    def getAllPlayers(self,world):
        return InstanceManager.getInstance().getInstance(world.instanceId).getPlayers()

    # Broadcast npc say, hint: if you wanna make npcs shout that are not near
    # the players use the player or a near npc as broadcaster
    def npcTalk(self,npc,text,chan=0,broadcaster=npc):
        if isinstance(text,[]):
            broadcaster.broadcastPacket(NpcSay(npc.getObjId, chan, npc.getNpcId(), text[Rnd.get(len(text))]))
        else:
            broadcaster.broadcastPacket(NpcSay(npc.getObjId, chan, npc.getNpcId(), text))

    # Adds a new spawn
    def newSpawn(self,npcId,x,y,z,world,respawn=0,heading=0):
        npc = self.addSpawn(npcId,x, y, z, heading,False,0,False, world.instanceId)
        if respawn>0:
            npc.getSpawn().setRespawnDelay(respawn)
        return npc

    # Dummy, overwrite it in actual implementations
    def checkCondition(self,player):
        return True

    def enterInstance(self,player,template,teleto):
        instanceId = 0
        if not self.checkCondition(player):
            return instanceId
        members = self.getMembers(player)
        # Check for existing instances of party members or channel members
        for member in members :
            if member.getInstanceId()!= 0 and member.getInstanceId() != player.getInstanceId():
                instanceId = member.getInstanceId()
        # Existing instance
        if instanceId != 0:
            foundworld = False
            for worldid in self.world_ids:
                if worldid == instanceId:
                    foundworld = True
            if not foundworld:
                self.sendString(player,self.MEMBERS_IN_OTHER_INSTANCE)
                return 0
            teleto.instanceId = instanceId
            self.teleportPlayer(player, teleto)
            return instanceId
        else:
            instanceId = InstanceManager.getInstance().createDynamicInstance(template)
            if not self.worlds.has_key(instanceId):
                # create new world
                world = self.setupWorld(instanceId)
                self.worlds[instanceId]=world
                self.world_ids.append(instanceId)
                self._log.info(self.name + " started, Template: " + template + " InstanceId: " +str(instanceId) + " created by: " + str(player.getName()))
            # Teleports player
            teleto.instanceId = instanceId
            self.teleportPlayer(player,teleto)
            return instanceId
        return instanceId

    # Roll the dice
    def calculateChance(self,chance):
        dice = chance = Rnd.get(100)
        if dice >= chance:
            return False
        return True

    # Teleports a npc to player location and sets optional aggro
    def teleportMobToPlayer(self,npc,player,aggro=0):
        npc.teleToLocation(player.getX(),player.getY(),player.getZ())
        if aggro>0:
            npc.addDamageHate(player,0,aggro)

    # Makes a npc decay delay
    def decayDelayed(self,npc,player,delay=5000):
        self.startQuestTimer("decayNpc", delay, npc, player)

    def sendString(self,player,string):
        player.sendPacket(SystemMessage.sendString(string))

    def checkItem(self,player,itemId,count=1):
        item = player.getInventory().getItemByItemId(itemId)
        if not item:
            return False
        if item.getCount()<count:
            return False
        return True

    def checkDestroyItem(self,player,itemId,count=1):
        if not self.checkItem(player,itemId,count):
            return False
        player.destroyItemByItemId("Quest", itemId, count, player, True)
        return True

    def closeAllDoors(self,world):
        for door in InstanceManager.getInstance().getInstance(world.instanceId).getDoors():
                door.closeMe()

    def getWorld(self,object):
        try:
            if self.worlds.has_key(object.getInstanceId()):
                world = self.worlds[object.getInstanceId()]
                return world
        except:
            return None
        return None

    def giveItem(self,player,itemId,count):
        item = player.getInventory().addItem("Quest", itemId, count, player, None)
        iu = InventoryUpdate()
        iu.addItem(item)
        player.sendPacket(iu)
        sm = SystemMessage(SystemMessageId.YOU_PICKED_UP_S1_S2)
        sm.addItemName(item)
        sm.addNumber(1)
        player.sendPacket(sm)
        return item

    # Quest events
    def onAdvEvent(self,event,npc,player):
        world = self.getWorld(npc) and self.getWorld(npc) or self.getWorld(player)
        if event == "teleportNext":
            if world.nextTeleLoc:
                self.teleportGroup(world.nextTeleLoc,player,True)
        if event == "decayNpc":
            npc.decayMe()
        return world

    def onTalk(self,npc,player):
        return self.getWorld(npc)

    def onKill(self,npc,player,isPet):
        return self.getWorld(npc)

    def onAttack(self,npc,player,damage,isPet,skill):
        return self.getWorld(npc)

    def onFirstTalk (self,npc,player):
        return self.getWorld(npc)

    def onAggroRangeEnter(self,npc,player,isPet):
        return self.getWorld(npc)
