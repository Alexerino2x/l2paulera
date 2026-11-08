print "importing custom data: 5007_santa"
import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest
qn = "5007_santa"
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    htmltext = event

# Christmas
    if event == "1":
        if st.getQuestItemsCount(5556) >= 1 and st.getQuestItemsCount(5557) >= 1 and st.getQuestItemsCount(5558) >= 1 and st.getQuestItemsCount(5559) >= 1:
            st.takeItems(5556,1)
            st.takeItems(5557,1)
            st.takeItems(5558,1)
            st.takeItems(5559,1)		
            st.giveItems(5283,3)
            htmltext = "Merry Christmas."
	else:
             htmltext = "You do not have all four ornaments."

    if event == "0":
      htmltext = "Trade has been canceled."
    
    if htmltext != event:
      st.setState(COMPLETED)
      st.exitQuest(1)

    return htmltext

 def onTalk (self,npc,player):

   npcId = npc.getNpcId()
   st = player.getQuestState(qn)
   htmltext = "<html><body>I have nothing to say to you.</body></html>"
   st.set("cond","0")
   st.setState(STARTED)
   return "1.htm"

QUEST       = Quest(5007,"5007_santa","custom")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

QUEST.addStartNpc(12617)
QUEST.addStartNpc(12618)

QUEST.addTalkId(12617)
QUEST.addTalkId(12618)