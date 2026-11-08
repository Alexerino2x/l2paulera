# By Psycho(killer1888) / L2jFree

from com.l2jfree.gameserver.instancemanager        import InstanceManager
from com.l2jfree.gameserver.gameobjects            import L2Summon
from com.l2jfree.gameserver.model.entity           import Instance
from com.l2jfree.gameserver.model.quest            import State
from com.l2jfree.gameserver.model.quest            import QuestState
from com.l2jfree.gameserver.model.quest.jython     import QuestJython as JQuest
from com.l2jfree.gameserver.network.packets.server  import SystemMessage
from com.l2jfree.gameserver.datatables             import ItemTable
from com.l2jfree.gameserver.gameobjects   import L2Player

qn = "129_PailakaDevilsLegacy"
debug = False

#NPC
SURVIVOR = 32498
SUPPORTER = 32501
DWARF = 32508
DWARF2 = 32511

KAMS = 18629
HIKORO = 18630
ALKASO = 18631
GERBERA = 18632
LEMATAN = 18633 #respawn 52212 217256 -3344 h16380

#Items
SWORD = 13042
ENHANCED_SWORD = 13043
COMPLETE_SWORD = 13044
UPGRADE1 = 13046
UPGRADE2 = 13047
PAILAKA_BRACELET = 13295
PSOE = 13129

class PyObject:
    pass

def dropItem(npc,itemId,count):
    ditem = ItemTable.getInstance().createItem("Loot", itemId, count, None)
    ditem.dropMe(npc, npc.getX(), npc.getY(), npc.getZ())

def checkCondition(player):
    party = player.getParty()
    if party:
        player.sendPacket(SystemMessage.sendString("Pailaka only for one person."))    
        return False
    return True

def teleportplayer(self,player,teleto):
    player.setInstanceId(teleto.instanceId)
    player.teleToLocation(teleto.x, teleto.y, teleto.z)
    pet = player.getPet()
    if pet != None :
        pet.setInstanceId(teleto.instanceId)
        pet.teleToLocation(teleto.x, teleto.y, teleto.z)
    return

def enterInstance(self,player,template,teleto):
    instanceId = 0
    if not checkCondition(player):
        return 0
    # Create instance
    instanceId = InstanceManager.getInstance().createDynamicInstance(template)
    world = PyObject()
    world.instanceId = instanceId
    self.worlds[instanceId]=world
    self.world_ids.append(instanceId)
    print "INFO Devil's Legacy (Lvl 61-67): " +str(instanceId) + " created by player: " + str(player.getName())
    # Teleports player
    teleto.instanceId = instanceId
    teleportplayer(self,player,teleto)
    return instanceId

def exitInstance(player,tele):
    player.setInstanceId(0)
    player.teleToLocation(teleto.x, teleto.y, teleto.z)
    pet = player.getPet()
    if pet != None :
        pet.setInstanceId(0)
        pet.teleToLocation(teleto.x, teleto.y, teleto.z)
        
class Quest(JQuest):
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [SWORD,ENHANCED_SWORD,COMPLETE_SWORD,UPGRADE1,UPGRADE2]
        self.worlds = {}
        self.world_ids = []
        
    def onAdvEvent(self,event,npc,player):
        htmltext = event
        st = player.getQuestState(qn)
        if not st: return
        npcId = npc.getNpcId()
        if event == "32498-01.htm":
            if player.getLevel() < 61 or player.getLevel() > 67:
                htmltext = "32498-08.htm"
        elif event == "32498-04.htm":
            if not checkCondition(player): return
            st.set("cond","1")
            st.setState(State.STARTED)
            st.playSound("ItemSound.quest_accept")
        elif event == "32498-06.htm":
            tele = PyObject()
            tele.x = 43739
            tele.y = 206905
            tele.z = -3760
            instanceId = enterInstance(self, player, "DevilsLegacy.xml", tele)
            if instanceId == 0:
                return
            st.set("cond","2")
            st.playSound("ItemSound.quest_middle")
        elif event == "32501-02.htm":
            st.playSound("ItemSound.quest_middle")
            st.giveItems(SWORD, 1)
            st.set("cond","3")
        elif event == "32508-02.htm":
            pet = player.getPet()
            if pet != None :
                htmltext = "32508-01.htm"
            else:
                stateKams = st.getInt("kams")
                if st.getQuestItemsCount(UPGRADE1) == 0:
                    htmltext = "32508-04.htm"
                elif stateKams == 1 and st.getQuestItemsCount(UPGRADE1) == 1:
                    st.playSound("ItemSound.quest_itemget")
                    st.takeItems(SWORD, -1)
                    st.takeItems(UPGRADE1, -1)
                    st.giveItems(ENHANCED_SWORD, 1)
        elif event == "32511-01.htm":
            pet = player.getPet()
            if pet != None:
                htmltext = "32511.htm"
            else:
                st.playSound("ItemSound.quest_finish")
                st.giveItems(PSOE, 1)
                st.giveItems(PAILAKA_BRACELET, 1)
                player.setVitalityPoints(20000.0, True)
                st.addExpAndSp(10800000, 950000)
                st.exitQuest(False)
        return htmltext
            

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId == SUPPORTER:
            if cond == 2:
                htmltext = "32501.htm"
            elif cond > 2:
                htmltext = "32501-04.htm"
        return htmltext

    def onFirstTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : st = self.newQuestState(player)
        npcId = npc.getNpcId()
        state = st.getState()
        if state == State.COMPLETED :
            htmltext = "<html><body>This quest has already been completed.</body></html>"
            return htmltext
        cond = st.getInt("cond")
        if npcId == SURVIVOR:
            if state == State.COMPLETED :
                htmltext = "32498-07.htm"
                return htmltext
            if cond == 0:
                htmltext = "32498.htm"
            elif cond == 1:
                htmltext = "32498-05.htm"
        elif npcId == DWARF:
            if cond == 3:
                stateKams = st.getInt("kams")
                stateAlkaso = st.getInt("alkaso")
                if stateKams == 1 and stateAlkaso == 1 and st.getQuestItemsCount(UPGRADE2) == 1:
                    pet = player.getPet()
                    if pet != None :
                        htmltext = "32508-01.htm"
                    else:
                        st.playSound("ItemSound.quest_itemget")
                        st.giveItems(COMPLETE_SWORD, 1)
                        st.takeItems(ENHANCED_SWORD, -1)
                        st.takeItems(UPGRADE2, -1)
                        htmltext = "32508-03.htm"
                else:
                    htmltext = "32508.htm"
        elif npcId == DWARF2:
            if cond == 4:
                htmltext = "32511.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        stateKams = st.getInt("kams")
        stateHikoro = st.getInt("hikoro")
        stateAlkaso = st.getInt("alkaso")
        stateGerbera = st.getInt("gerbera")
        if npcId == KAMS:
            if cond == 3 and stateKams == 0:
                st.playSound("ItemSound.quest_itemget")
                st.giveItems(UPGRADE1, 1)
                stateKams = 1
                st.set("kams","1")
        elif npcId == HIKORO:
            if cond == 3 and stateHikoro == 0:
                st.playSound("ItemSound.quest_middle")
                stateHikoro = 1
                st.set("hikoro","1")
        elif npcId == ALKASO:
            if cond == 3 and stateAlkaso == 0:
                st.playSound("ItemSound.quest_itemget")
                st.giveItems(UPGRADE2, 1)
                stateAlkaso = 1
                st.set("alkaso","1")
        elif npcId == GERBERA:
            if cond == 3 and stateGerbera == 0:
                st.playSound("ItemSound.quest_middle")
                stateGerbera = 1
                st.set("gerbera","1")
        if stateKams == 1 and stateHikoro == 1 and stateAlkaso == 1 and stateGerbera == 1 and cond == 3:
            instanceId = player.getInstanceId()
            st.addSpawn(LEMATAN, 56452, 216038, -3509, 32760, False, 0, False, instanceId)
        if npcId == LEMATAN and cond == 3:
            st.set("cond","4")
            st.playSound("ItemSound.quest_middle")
            st.addSpawn(DWARF2, 52218, 217608, -3344, 49140, False, 0, False, instanceId)
        return

QUEST = Quest(129, qn, "Pailaka - Devil's Legacy")

QUEST.addStartNpc(SURVIVOR)
QUEST.addTalkId(SUPPORTER)
QUEST.addTalkId(SURVIVOR)
QUEST.addTalkId(DWARF)
QUEST.addTalkId(DWARF2)
QUEST.addFirstTalkId(SURVIVOR)
QUEST.addFirstTalkId(DWARF)
QUEST.addFirstTalkId(DWARF2)
QUEST.addKillId(KAMS)
QUEST.addKillId(GERBERA)
QUEST.addKillId(LEMATAN)
QUEST.addKillId(HIKORO)
QUEST.addKillId(ALKASO)