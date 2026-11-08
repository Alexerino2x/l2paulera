# By Psycho(killer1888) / L2jFree

from com.l2jfree.gameserver.instancemanager        import InstanceManager
from com.l2jfree.gameserver.gameobjects            import L2Summon
from com.l2jfree.gameserver.model.entity           import Instance
from com.l2jfree.gameserver.model.quest            import State
from com.l2jfree.gameserver.model.quest            import QuestState
from com.l2jfree.gameserver.model.quest.jython     import QuestJython as JQuest
from com.l2jfree.gameserver.network.packets.server  import CreatureSay
from com.l2jfree.gameserver.network.packets.server  import MagicSkillUse
from com.l2jfree.gameserver.network.packets.server  import SystemMessage
from com.l2jfree.tools.random                      import Rnd
from com.l2jfree.gameserver.datatables             import ItemTable
from com.l2jfree.gameserver.gameobjects   import L2Player

qn = "128_PailakaSongOfIceAndFire"
debug = False

#NPC
ADLER = 32497
ADLER2 = 32510
SINAI = 32500
INSPECTOR = 32507
HILLAS = 18610
PAPION = 18609
KINSUS = 18608
GARGOS = 18607
ADIANTUM = 18620

#ITEM
SWORD = 13034
ENHENCED_SWORD = 13035
ENHENCED_SWORD_2 = 13036
BOOK1 = 13130
BOOK2 = 13131
BOOK3 = 13132
BOOK4 = 13133
BOOK5 = 13134
BOOK6 = 13135
BOOK7 = 13136
WATER_ENHENCER = 13038
FIRE_ENHENCER = 13039
PAILAKA_RING = 13294
PAILAKA_EARRING = 13293
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
    print "Song of Ice and Fire (Lvl 36-42): " +str(instanceId) + " created by player: " + str(player.getName())
    # Teleports player
    teleto.instanceId = instanceId
    teleportplayer(self,player,teleto)
    return instanceId

def exitInstance(player,teleto):
    player.setInstanceId(0)
    player.teleToLocation(teleto.x, teleto.y, teleto.z)
    pet = player.getPet()
    if pet != None :
        pet.setInstanceId(0)
        pet.teleToLocation(teleto.x, teleto.y, teleto.z)
        
class Quest(JQuest):
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        self.questItemIds = [SWORD,ENHENCED_SWORD,ENHENCED_SWORD_2,BOOK1,BOOK2,BOOK3,BOOK4,BOOK5,BOOK6,BOOK7,WATER_ENHENCER,FIRE_ENHENCER]
        self.worlds = {}
        self.world_ids = []
        
    def onAdvEvent(self,event,npc,player):
        htmltext = event
        st = player.getQuestState(qn)
        if not st: return
        npcId = npc.getNpcId()
        if event == "TimeOver":
            teleto = PyObject()
            teleto.x = -52855
            teleto.y = 188199
            teleto.z = -4700
            teleto.instanceId = 0
            teleportplayer(teleto)
            player.setPailaka(False)
            st.exitQuest(True)
        if npcId == ADLER and event == "accept":
            tele = PyObject()
            tele.x = -52855
            tele.y = 188199
            tele.z = -4700
            instanceId = enterInstance(self, player, "SongOfIceAndFire.xml", tele)
            if instanceId == 0:
                return
            st.set("cond","1")
            st.setState(State.STARTED)
            st.playSound("ItemSound.quest_accept")
            htmltext = ""
        if event == "32497-02.htm":
            if player.getLevel() < 36 or player.getLevel() > 42:
                htmltext = "32497-04.htm"
        elif event == "32500-05.htm":
            st.set("cond","2")
            st.playSound("ItemSound.quest_itemget")
            st.giveItems(SWORD, 1)
            st.giveItems(BOOK1, 1)
        elif event == "32507-03.htm":
            st.set("cond","4")
            st.playSound("ItemSound.quest_middle")
            st.giveItems(ENHENCED_SWORD, 1)
            st.takeItems(SWORD, -1)
            st.takeItems(WATER_ENHENCER, -1)
        elif event == "32507-07.htm":
            st.set("cond","7")
            st.playSound("ItemSound.quest_itemget")
            st.takeItems(ENHENCED_SWORD, -1)
            st.takeItems(FIRE_ENHENCER, -1)
            st.takeItems(BOOK5, -1)
            st.giveItems(ENHENCED_SWORD_2, 1)
            st.giveItems(BOOK6, 1)
        elif event == "32510-01.htm":
            st.playSound("ItemSound.quest_finish")
            st.setState(State.COMPLETED)
            st.giveItems(PSOE, 1)
            st.giveItems(PAILAKA_RING, 1)
            st.giveItems(PAILAKA_EARRING, 1)
            player.setVitalityPoints(20000.0, True)
            st.addExpAndSp(810000, 50000)
            st.exitQuest(False)
            if player.getInstanceId() != 0:
                instance = InstanceManager.getInstance().getInstance(player.getInstanceId())
                if instance != None:
                    instance.setDuration(300000)
        return htmltext
            

    def onTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        state = st.getState()
        if state == State.COMPLETED :
            htmltext = "<html><body>This quest has already been completed.</body></html>"
        else :
            cond = st.getInt("cond")
            if npcId == ADLER:
                if cond > 0:
                    tele = PyObject()
                    tele.x = -52855
                    tele.y = 188199
                    tele.z = -4700
                    instanceId = enterInstance(self, player, "SongOfIceAndFire.xml", tele)
                    self.startQuestTimer("TimeOver", 3600000, None, player)
                    player.setPailaka(True)
                    htmltext = ""
                else:
                    htmltext = "32497.htm"
            elif npcId == SINAI:
                if cond == 1:
                    htmltext = "32500.htm"
            elif npcId == ADLER2:
                if cond == 9:
                    htmltext = "32510.htm"
        return htmltext

    def onFirstTalk (self,npc,player):
        htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
        st = player.getQuestState(qn)
        if not st : return htmltext
        npcId = npc.getNpcId()
        state = st.getState()
        cond = st.getInt("cond")
        if npcId == INSPECTOR:
            if cond == 2:
                htmltext = "32507.htm"
            elif cond == 3:
                htmltext = "32507-02.htm"
            elif cond == 5 and st.getQuestItemsCount(BOOK3) == 1:
                st.playSound("ItemSound.quest_itemget")
                st.takeItems(BOOK3, -1)
                st.giveItems(BOOK4, 1)
                htmltext = "32507-04.htm"
            elif cond == 6:
                htmltext = "32507-05.htm"
        return htmltext

    def onKill(self,npc,player,isPet):
        st = player.getQuestState(qn)
        if not st : return
        npcId = npc.getNpcId()
        cond = st.getInt("cond")
        if npcId == HILLAS and cond == 2:
            st.playSound("ItemSound.quest_itemget")
            st.giveItems(WATER_ENHENCER,1)
            st.takeItems(BOOK1, -1)
            st.giveItems(BOOK2, 1)
            st.set("cond","3")
        elif npcId == PAPION and cond == 4:
            st.playSound("ItemSound.quest_itemget")
            st.takeItems(BOOK2, -1)
            st.giveItems(BOOK3,1)
            st.set("cond","5")
        elif npcId == KINSUS and cond == 5:
            st.playSound("ItemSound.quest_itemget")
            st.takeItems(BOOK4, -1)
            st.giveItems(BOOK5, 1)
            st.giveItems(FIRE_ENHENCER,1)
            st.set("cond","6")
        elif npcId == GARGOS and cond == 7:
            st.playSound("ItemSound.quest_itemget")
            st.takeItems(BOOK6, -1)
            st.giveItems(BOOK7, 1)
            st.set("cond","8")
        elif npcId == ADIANTUM and cond == 8:
            st.playSound("ItemSound.quest_middle")
            st.set("cond","9")
            instanceId = player.getInstanceId()
            st.addSpawn(ADLER2, -52586, 185027, -4619, 33486, False, 0, False, instanceId)
        return

QUEST = Quest(128, qn, "Pailaka - Song of Ice and Fire")

QUEST.addStartNpc(ADLER)
QUEST.addTalkId(ADLER)
QUEST.addTalkId(ADLER2)
QUEST.addTalkId(SINAI)
QUEST.addFirstTalkId(INSPECTOR)
QUEST.addTalkId(INSPECTOR)
QUEST.addKillId(HILLAS)
QUEST.addKillId(PAPION)
QUEST.addKillId(KINSUS)
QUEST.addKillId(GARGOS)
QUEST.addKillId(ADIANTUM)