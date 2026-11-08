# Created by L2Emu Team
import sys
from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "8004_PurchaseBracelet"

# NPCs
TRADER_ALEXANDRIA   = 30098

# QUEST ITEMS
ANGEL_BRACELET      = 12779
DEVIL_BRACELET      = 12780
BIG_RED_NIMBLE_FISH = 6471
GREAT_CODRAN        = 5094
MEMENTO_MORI        = 9814
DRAGON_HEART        = 9815
EARTH_EGG           = 9816
NONLIVING_NUCLEUS   = 9817
ADENA_ID            = 57

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onAdvEvent(self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if st.getQuestItemsCount(BIG_RED_NIMBLE_FISH) >= 25 and st.getQuestItemsCount(GREAT_CODRAN) >= 50 and st.getQuestItemsCount(MEMENTO_MORI) >= 4 and st.getQuestItemsCount(EARTH_EGG) >= 5 and st.getQuestItemsCount(NONLIVING_NUCLEUS) >= 5 and st.getQuestItemsCount(DRAGON_HEART) >= 3 and st.getQuestItemsCount(ADENA_ID) >= 7500000 :
       st.takeItems(BIG_RED_NIMBLE_FISH,25)
       st.takeItems(GREAT_CODRAN,50)
       st.takeItems(MEMENTO_MORI,4)
       st.takeItems(DRAGON_HEART,3)
       st.takeItems(EARTH_EGG,5)
       st.takeItems(NONLIVING_NUCLEUS,5)
       st.takeItems(ADENA_ID,7500000)
       htmltext = ""
       if event == "Little_Devil" :
          st.giveItems(DEVIL_BRACELET,1)
       elif event == "Little_Angel" :
          st.giveItems(ANGEL_BRACELET,1)
    else :
        htmltext = "30098-no.htm"
    st.exitQuest(1)
    return htmltext

 def onTalk(self,npc,player):
    htmltext = ""
    st = player.getQuestState(qn)
    if not st :
      st = self.newQuestState(player)
    htmltext = "30098.htm"
    return htmltext

QUEST = Quest(-1,qn,"custom")

QUEST.addStartNpc(TRADER_ALEXANDRIA)

QUEST.addTalkId(TRADER_ALEXANDRIA)