#
# Created by DraX on 2005.08.08
#

import sys

from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jfree.gameserver.gameobjects.base import ClassId
from com.l2jfree.gameserver.gameobjects.base import Race

qn = "30154_asterios_occupation_change"
HIERARCH_ASTERIOS = 30154

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onAdvEvent(self,event,npc, player) :
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return

   if event == "30154-01.htm":
     htmltext = event

   if event == "30154-02.htm":
     htmltext = event

   if event == "30154-03.htm":
     htmltext = event

   if event == "30154-04.htm":
     htmltext = event

   if event == "30154-05.htm":
     htmltext = event
   
   if event == "30154-06.htm":
     htmltext = event

   if event == "30154-07.htm":
     htmltext = event

   if event == "30154-08.htm":
     htmltext = event

   if event == "30154-09.htm":
     htmltext = event

   if event == "30154-10.htm":
     htmltext = event
   return htmltext
 
 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()

   race    = st.getPlayer().getRace()
   classId = st.getPlayer().getClassId()

   # Elfs got accepted
   if npcId == HIERARCH_ASTERIOS and race in [Race.Elf]:
     if classId in [ClassId.ElvenFighter]: 
       st.setState(State.STARTED)
       return "30154-01.htm"
     if classId in [ClassId.ElvenMystic]:
       st.setState(State.STARTED)
       return "30154-02.htm"
     if classId in [ClassId.ElvenWizard, ClassId.ElvenOracle, ClassId.ElvenKnight, ClassId.ElvenScout]:
       st.exitQuest(False)
       st.exitQuest(1)
       return "30154-12.htm"
     else:
       st.exitQuest(False)
       st.exitQuest(1)
       return "30154-13.htm"

   # All other Races must be out
   if npcId == HIERARCH_ASTERIOS and race in [Race.Dwarf, Race.Human, Race.Darkelf, Race.Orc, Race.Kamael]:
     st.exitQuest(False)
     st.exitQuest(1)
     return "30154-11.htm"

QUEST     = Quest(30154,qn,"village_master")



QUEST.addStartNpc(30154)

QUEST.addTalkId(30154)