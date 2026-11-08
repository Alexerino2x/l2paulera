# Made by disKret
import sys
from com.l2jfree import Config
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "13_ParcelDelivery"

#QUEST LEVEL
QLVL = 74

#NPC
FUNDIN = 31274
VULCAN = 31539

#QUEST ITEM
PACKAGE = 7263

#REWARDS
ADENA_ID     = 57
ADENA_REWARD = 157834
EXP          = 589092
SP           = 58794

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [PACKAGE]

 def onAdvEvent (self,event,npc, player) :
   htmltext = event
   st = player.getQuestState(qn)
   if not st : return
   cond = st.getInt("cond")
   if event == "31274-2.htm" :
     if cond == 0 :
       st.set("cond","1")
       st.setState(State.STARTED)
       st.giveItems(PACKAGE,1)
       st.playSound("ItemSound.quest_accept")
   if event == "31539-1.htm" :
     if cond == 1 and st.getQuestItemsCount(PACKAGE) == 1 :
       st.rewardItems(ADENA_ID,ADENA_REWARD)
       st.takeItems(PACKAGE,1)
       st.addExpAndSp(EXP,SP)
       st.exitQuest(False)
       st.set("cond","0")
       st.playSound("ItemSound.quest_finish")
     else :
       htmltext = "You don't have required items"
   return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext

   npcId = npc.getNpcId()
   id = st.getState()
   cond = st.getInt("cond")
   if id == State.COMPLETED :
      htmltext = "<html><body>This quest has already been completed.</body></html>"
   elif npcId == FUNDIN and id == State.CREATED :
     if player.getLevel() < 74 :
       htmltext = "31274-1.htm"
       st.exitQuest(1)
     else :
       htmltext = "31274-0.htm"
   elif npcId == FUNDIN and cond == 1 :
     htmltext = "31274-2.htm"
   elif npcId == VULCAN and cond == 1 and id == State.STARTED:
     htmltext = "31539-0.htm"
   return htmltext

QUEST       = Quest(13,qn,"Parcel Delivery")

QUEST.addStartNpc(FUNDIN)
QUEST.addTalkId(FUNDIN)
QUEST.addTalkId(VULCAN)
