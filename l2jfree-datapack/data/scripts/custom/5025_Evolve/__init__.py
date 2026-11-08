import sys

from com.l2jfree.gameserver.model.quest        import State
from com.l2jfree.gameserver.model.quest        import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "5025_Evolve"

#Minimum pet level in order to evolve
MIN_PET_LEVEL = 55
#Maximum distance allowed between pet and owner
MAX_DISTANCE = 100

#Messages
default = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements....</body></html>"
error_1 = "<html><body>You're suppossed to own a wolf and have it summoned in order for it to evolve.</body></html>"
error_2 = "<html><body>Your pet needs to be level "+str(MIN_PET_LEVEL)+" in order for it to evolve.</body></html>"
error_3 = "Your pet is not a wolf."
error_4 = "Your pet should be nearby."
end_msg = "<html><body>Great job, your Wolf"
end_msg2= "has become a GreatWolf, enjoy!</body></html>"

#Pet Managers
MARTIN = 30731
LUNDY  = 30827
WATERS = 30828
COOPER = 30829
JOEY   = 30830
NELSON = 30831
LEMPER = 30869
ROOD   = 31067
ANNETTE= 31265
WOODS  = 31309
SAROYAN= 31954
NPCS = [30731,30869,31067,31265,31309,31954,30827,30828,30829,30830,30831]

#Items
WOLF_COLLAR     = 2375
GREAT_WOLF_NECK = 9882
#npcId for wolf
WOLF       = 12077

CONTROL_ITEMS = { WOLF_COLLAR:GREAT_WOLF_NECK }

def get_control_item(st) :
  item = st.getPlayer().getPet().getControlItemId()
  if st.getState() == State.CREATED :
      st.set("item",str(item))
  else :
      if  st.getInt("item") != item : item = 0
  return item  

def get_distance(player) :
    is_far = False
    if abs(player.getPet().getX() - player.getX()) > MAX_DISTANCE :
        is_far = True
    if abs(player.getPet().getY() - player.getY()) > MAX_DISTANCE :
        is_far = True
    if abs(player.getPet().getZ() - player.getZ()) > MAX_DISTANCE :
        is_far = True
    return is_far

class Quest (JQuest) :

 def __init__(self,id,name,descr): 
   JQuest.__init__(self,id,name,descr)

 def onTalk (self,npc,player):
   htmltext = default
   st = player.getQuestState(qn)
   if not st : return htmltext
   npcId = npc.getNpcId()
   if player.getPet() == None :
       htmltext = error_1
       st.exitQuest(1)
   elif player.getPet().getTemplate().npcId not in [WOLF] :
       htmltext = error_3
       st.exitQuest(1)
   elif player.getPet().getLevel() < MIN_PET_LEVEL :
       st.exitQuest(1)
       htmltext = error_2
   elif get_distance(player) :
       st.exitQuest(1)
       htmltext = error_4
   elif get_control_item(st) == 0 :
       st.exitQuest(1)
       htmltext = error_3
   else :
       name = player.getPet().getName()
       if name == None : name = " "
       else : name = " "+name+" "
       htmltext = end_msg+name+end_msg2
       item=CONTROL_ITEMS[player.getInventory().getItemByObjectId(player.getPet().getControlItemId()).getItemId()]
       player.getPet().deleteMe(player) #both despawn pet and delete controlitem
       st.giveItems(item,1)
       st.exitQuest(1)
       st.playSound("ItemSound.quest_finish")
   return htmltext

QUEST       = Quest(-1,qn,"Custom")

for i in NPCS:
   QUEST.addStartNpc(i)
   QUEST.addTalkId(i)