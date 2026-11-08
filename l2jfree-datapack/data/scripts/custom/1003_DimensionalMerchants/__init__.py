# Script by Psycho(killer1888) / L2jFree

import sys
from com.l2jfree                               import Config
from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "1003_DimensionalMerchants"

class Quest (JQuest) :

    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)

    def onAdvEvent(self,event,npc,player):
        st = player.getQuestState(qn)
        if not st: return
        htmltext = event
        if event == "13017" or event == "13018" or event == "13019" or event == "13020":
            # Player can either have an event coupon or a normal coupon, so first check for the normal one
            normalItem = st.getQuestItemsCount(13273)
            eventItem = st.getQuestItemsCount(13383)
            if normalItem >= 1:
                st.takeItems(13273,1)
                st.giveItems(int(event),1)
                st.exitQuest(1)
                return
            elif eventItem >= 1:
                event = int(event) + 286
                st.takeItems(13383,1)
                st.giveItems(int(event),1)
                st.exitQuest(1)
                return
            else:
                htmltext = "32478-11.htm"
        elif event == "13548" or event == "13549" or event == "13550" or event == "13551":
            if st.getQuestItemsCount(14065) >= 1:
                st.takeItems(14065,1)
                st.giveItems(int(event),1)
                st.exitQuest(1)
                return
            else:
                htmltext = "32478-11.htm"
                st.exitQuest(1)
        return htmltext

    def onFirstTalk(self,npc,player):
        st = player.getQuestState(qn)
        if not st :
            st = self.newQuestState(player)
        if Config.ALT_ENABLE_DIMENSIONAL_MERCHANTS:
            htmltext = "32478.htm"
        else:
            htmltext = "32478-na.htm"
        return htmltext

QUEST = Quest(-1, qn, "custom")

QUEST.addStartNpc(32478)
QUEST.addTalkId(32478)
QUEST.addFirstTalkId(32478)
