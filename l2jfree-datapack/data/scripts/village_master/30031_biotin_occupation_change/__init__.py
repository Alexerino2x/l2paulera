#
# Created by DraX on 2005.08.08
#

import sys

from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest
from com.l2jfree.gameserver.gameobjects.base import ClassId
from com.l2jfree.gameserver.gameobjects.base import Race

qn = "30031_biotin_occupation_change"
HIGH_PRIEST_BIOTIN = 30031

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onAdvEvent(self,event,npc, player) :
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return

   if event == "30031-01.htm":
     htmltext = event

   if event == "30031-02.htm":
     htmltext = event

   if event == "30031-03.htm":
     htmltext = event

   if event == "30031-04.htm":
     htmltext = event

   if event == "30031-05.htm":
     htmltext = event

   return htmltext

 
 def onTalk (self,npc,player):
   st = player.getQuestState(qn)
   npcId = npc.getNpcId()
   
   race    = st.getPlayer().getRace()
   classId = st.getPlayer().getClassId()
   
   # Humans got accepted
   if npcId == HIGH_PRIEST_BIOTIN and race in [Race.Human]:
     if classId in [ClassId.HumanFighter, ClassId.Warrior, ClassId.HumanKnight, ClassId.Rogue]:
       htmltext = "30031-08.htm"
     if classId in [ClassId.Warlord, ClassId.Paladin, ClassId.TreasureHunter]:
       htmltext = "30031-08.htm"
     if classId in [ClassId.Gladiator, ClassId.DarkAvenger, ClassId.Hawkeye]:
       htmltext = "30031-08.htm"
     if classId in [ClassId.HumanWizard, ClassId.Cleric]:
       htmltext = "30031-06.htm"
     if classId in [ClassId.Sorceror, ClassId.Necromancer, ClassId.Warlock, ClassId.Bishop, ClassId.Prophet]:
       htmltext = "30031-07.htm"
     else:
       htmltext = "30031-01.htm"

     st.setState(State.STARTED)
     return htmltext

   # All other Races must be out
   if npcId == HIGH_PRIEST_BIOTIN and race in [Race.Dwarf, Race.Darkelf, Race.Elf, Race.Orc, Race.Kamael]:
     st.exitQuest(False)
     st.exitQuest(1)
     return "30031-08.htm"

QUEST     = Quest(30031,qn,"village_master")



QUEST.addStartNpc(30031)

QUEST.addTalkId(30031)
