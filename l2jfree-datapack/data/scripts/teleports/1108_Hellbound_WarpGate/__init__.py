# Psycho(killer1888) / L2jFree
import sys
from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jfree.gameserver.instancemanager.hellbound       import HellboundManager

qn = "1108_Hellbound_WarpGate"

WARPGATES  = [32314,32315,32316,32317,32318,32319]
ENERGYFROMMINORBOSSES = True

KECHI      = 25532
DARNEL     = 25531
TEARS      = 25534

BAYLOR = 29099

def checkWarpGate():
    open = HellboundManager.getInstance().isWarpgateActive()
    return open

class Quest(JQuest):

    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)
        longQuest = self.loadGlobalQuestVar("BloodyHotQuest")
        if longQuest == "":
            self.saveGlobalQuestVar("BloodyHotQuest","0")
            longQuest = 0
        self.bloodyHot = int(longQuest)

    def onTalk (self,npc,player):
        st = player.getQuestState(qn)
        if not st: return
        npcId = npc.getNpcId()
        if self.bloodyHot == 0:
            st1 = st.getPlayer().getQuestState("133_ThatsBloodyHot")
            if st1:
                if st1.getState() == State.COMPLETED:
                    HellboundManager.getInstance().addWarpgateEnergy(10000)
                    self.saveGlobalQuestVar("BloodyHotQuest","1")
                    self.bloodyHot = 1
                    player.teleToLocation(-11095, 236440, -3232)
                    htmltext = ""
                else:
                    htmltext = "cant-port.htm"
            else:
                htmltext = "cant-port.htm"
        else:
            if checkWarpGate():
                st2 = st.getPlayer().getQuestState("130_PathToHellbound")
                if st2:
                    if st2.getState() == State.COMPLETED:
                        player.teleToLocation(-11095, 236440, -3232)
                        htmltext = ""
                    else:
                        htmltext = "cant-port.htm"
                else:
                    htmltext = "cant-port.htm"
            else:
                htmltext = "cant-port.htm"
        st.exitQuest(1)
        return htmltext

    def onKill(self,npc,player,isPet):
        npcId = npc.getNpcId()
        if npcId == BAYLOR:
            if self.bloodyHot == 1:
                HellboundManager.getInstance().addWarpgateEnergy(80000)
        if ENERGYFROMMINORBOSSES:
            if npcId == TEARS or npcId == KECHI or npcId == DARNEL:
                HellboundManager.getInstance().addWarpgateEnergy(10000)
        return

QUEST = Quest(1108,qn,"Teleports")

for npcId in WARPGATES :
    QUEST.addStartNpc(npcId)
    QUEST.addTalkId(npcId)

QUEST.addKillId(BAYLOR)
QUEST.addKillId(TEARS)
QUEST.addKillId(KECHI)
QUEST.addKillId(DARNEL)