# Made by Polo - Have fun! - Fixed by BiTi
# v0.3.1 by DrLecter
# Update 2009.12.23 Psycho(killer1888) / L2jFree (there's no cond 2)
import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "636_TruthBeyond"

#Npc
ELIYAH = 31329
FLAURON = 32010

#Items
MARK = 8064

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)

 def onAdvEvent(self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if htmltext == "31329-04.htm" :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.playSound("ItemSound.quest_accept")
    elif htmltext == "32010-02.htm" :
       st.playSound("ItemSound.quest_finish")
       st.giveItems(MARK,1)
       st.setState(State.COMPLETED)
    return htmltext

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   if st :
     npcId = npc.getNpcId()
     id = st.getState()
     cond = st.getInt("cond")
     if id == State.COMPLETED and not st.getQuestItemsCount(MARK) and not st.getQuestItemsCount(8065) and not st.getQuestItemsCount(8067) :
       st.set("cond","0")
       st.setState(State.CREATED)
       cond = 0
       id = State.CREATED
     elif id == State.COMPLETED and st.getQuestItemsCount(MARK) == 1 :
       return "<html><body>Go to the temple and talk to the teleporter near the front gate.</body></html>"
     elif id == State.COMPLETED and st.getQuestItemsCount(8065) == 1 or st.getQuestItemsCount(8067) == 1 :
       htmltext = "31329-mark.htm"
       return htmltext
     if cond == 0 and id == State.CREATED :
       if npcId == ELIYAH :
         if player.getLevel()>72 :
           htmltext = "31329-02.htm"
       else:
         htmltext = "31329-01.htm"
         st.exitQuest(1)
     elif id == State.STARTED :
       if npcId == ELIYAH :
         htmltext = "31329-05.htm"
       elif npcId == FLAURON :
         if cond == 1 :
           htmltext = "32010-01.htm"
         else :
           htmltext = "32010-03.htm"
           st.exitQuest(1)
   return htmltext

QUEST       = Quest(636,qn,"The Truth Beyond the Gate")

QUEST.addStartNpc(ELIYAH)

QUEST.addTalkId(ELIYAH)
QUEST.addTalkId(FLAURON)