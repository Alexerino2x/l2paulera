# Made by Michiru
import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "1107_cruma_tower"

MOZELLA = 30483

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   if npcId == MOZELLA:
     if st.getPlayer().getLevel() < 56 :
       player.teleToLocation(17724,114004,-11672)
       st.exitQuest(1)
       return
     else:
       st.exitQuest(1)
       return "1.htm"

QUEST       = Quest(-1,qn,"Teleports")

for i in [MOZELLA] :
    QUEST.addStartNpc(i)
    QUEST.addTalkId(i)