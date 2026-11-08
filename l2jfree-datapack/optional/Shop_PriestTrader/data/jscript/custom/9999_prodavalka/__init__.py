print "importing custom: 9999_prodavalka"
import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

NPC=[8078,8079,8080,8081,8082,8083,8084,8085,8086,8087,8088,8089,8090,8091,8168,8169]

class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onTalk (self,npc,st):
   npcId = npc.getNpcId()  
   if npcId in NPC:    
     st.setState(STARTED)
     htmltext = "1.htm"

def onEvent (self,event,st) :
    htmltext = event

# Grate Healing Poition
    if event == "1":
        if st.getQuestItemsCount(5575)>=1080 and st.getQuestItemsCount(57)>=108:
            st.takeItems(5575,1080)
            st.takeItems(57,108)
            st.giveItems(1539,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Blessed scroll of resurection pets
    if event == "2":
        if st.getQuestItemsCount(5575)>=6000 and st.getQuestItemsCount(57)>=600:
            st.takeItems(5575,6000)
            st.takeItems(57,600)
            st.giveItems(6387,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate Haste poition
    if event == "3":
         if st.getQuestItemsCount(5575)>=3600 and st.getQuestItemsCount(57)>=360:
            st.takeItems(5575,3600)
            st.takeItems(57,360)
            st.giveItems(1374,1)
            htmltext = "Item has been succesfully purchased."
         else:
            htmltext = "You do not have enough ancient adena."

# Grate Magic Haste Poition
    if event == "4":
        if st.getQuestItemsCount(5575)>=7200 and st.getQuestItemsCount(57)>=720:
            st.takeItems(5575,7200)
            st.takeItems(57,720)
            st.giveItems(6036,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grater swift attak poition
    if event == "5":
        if st.getQuestItemsCount(5575)>=7200 and st.getQuestItemsCount(57)>=720:
            st.takeItems(5575,7200)
            st.takeItems(57,720)
            st.giveItems(1375,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# CP poition
    if event == "6":
        if st.getQuestItemsCount(5575)>=240 and st.getQuestItemsCount(57)>=24:
            st.takeItems(5575,240)
            st.takeItems(57,24)
            st.giveItems(5591,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate CP poition 
    if event == "7":
        if st.getQuestItemsCount(5575)>=600 and st.getQuestItemsCount(57)>=60:
            st.takeItems(5575,600)
            st.takeItems(5965,60)
            st.giveItems(5592,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Charm of luck
    if event == "8":
        if st.getQuestItemsCount(5575)>=120 and st.getQuestItemsCount(57)>=12:
            st.takeItems(5575,120)
            st.takeItems(57,12)
            st.giveItems(5703,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Charm of luck D-Grade
    if event == "9":
        if st.getQuestItemsCount(5575)>=396 and st.getQuestItemsCount(57)>=32:
            st.takeItems(5575,396)
            st.takeItems(57,32)
            st.giveItems(5803,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Charm of luck C-Grade
    if event == "10":
        if st.getQuestItemsCount(5575)>=792 and st.getQuestItemsCount(57)>=76:
            st.takeItems(5575,792)
            st.takeItems(57,76)
            st.giveItems(5804,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Charm of luck B-Grade
    if event == "11":
        if st.getQuestItemsCount(5575)>=1560 and st.getQuestItemsCount(57)>=156:
            st.takeItems(5575,1560)
            st.takeItems(57,156)
            st.giveItems(5805,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Charm of luck A-Grade
    if event == "12":
        if st.getQuestItemsCount(5575)>=3240 and st.getQuestItemsCount(57)>=324:
            st.takeItems(5575,3240)
            st.takeItems(57,324)
            st.giveItems(5806,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Charm of luck S-Grade
    if event == "13":
        if st.getQuestItemsCount(5575)>=6000 and st.getQuestItemsCount(57)>=600:
            st.takeItems(5575,6000)
            st.takeItems(57,600)
            st.giveItems(5807,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# SP-Scroll low Grade
    if event == "14":
        if st.getQuestItemsCount(5575)>=2400 and st.getQuestItemsCount(57)>=240:
            st.takeItems(5575,2400)
            st.takeItems(57,240)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# SP-Scroll Medium Grade
    if event == "15":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# SP-Scroll High Grade
    if event == "16":
        if st.getQuestItemsCount(5575)>=480000 and st.getQuestItemsCount(57)>=48000:
            st.takeItems(5575,480000)
            st.takeItems(57,48000)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Mystery Potion
    if event == "17":
        if st.getQuestItemsCount(5575)>=12000 and st.getQuestItemsCount(57)>=1200:
            st.takeItems(5575,12000)
            st.takeItems(57,1200)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."
# Face lifing poition A
    if event == "18":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Face lifing poition B
    if event == "19":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Face lifing poition C
    if event == "20":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Dye poition A
    if event == "21":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Dye poition B
    if event == "22":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."
# Dye poition C
    if event == "23":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Dye poition D
    if event == "24":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-A
    if event == "25":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-B
    if event == "26":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-C
    if event == "27":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-D
    if event == "28":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-E
    if event == "29":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-F
    if event == "30":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Hair style Change poition-G
    if event == "31":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(955,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# party mask
    if event == "32":
        if st.getQuestItemsCount(5575)>=600000 and st.getQuestItemsCount(57)>=60000:
            st.takeItems(5575,600000)
            st.takeItems(57,60000)
            st.giveItems(5808,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# squeaking shoes
    if event == "33":
        if st.getQuestItemsCount(5575)>=420000 and st.getQuestItemsCount(57)>=10500:
            st.takeItems(5575,120)
            st.takeItems(57,12)
            st.giveItems(5590,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Blessed scroll of resurection pets x10
    if event == "34":
        if st.getQuestItemsCount(5575)>=60000 and st.getQuestItemsCount(57)>=6000:
            st.takeItems(5575,60000)
            st.takeItems(57,6000)
            st.giveItems(6387,10)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate Haste poition x10
    if event == "35":
        if st.getQuestItemsCount(5575)>=36000 and st.getQuestItemsCount(57)>=3600:
            st.takeItems(5575,36000)
            st.takeItems(57,3600)
            st.giveItems(1374,10)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate Magic Haste Poition x10
    if event == "36":
        if st.getQuestItemsCount(5575)>=72000 and st.getQuestItemsCount(57)>=7200:
            st.takeItems(5575,72000)
            st.takeItems(57,7200)
            st.giveItems(6036,10)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grater swift attak poition x10
    if event == "37":
        if st.getQuestItemsCount(5575)>=72000 and st.getQuestItemsCount(57)>=7200:
            st.takeItems(5575,72000)
            st.takeItems(57,7200)
            st.giveItems(1375,10)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# CP poition x10
    if event == "38":
        if st.getQuestItemsCount(5575)>=2400 and st.getQuestItemsCount(57)>=240:
            st.takeItems(5575,2400)
            st.takeItems(57,240)
            st.giveItems(5591,10)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# CP poition x100
    if event == "39":
        if st.getQuestItemsCount(5575)>=24000 and st.getQuestItemsCount(57)>=2400:
            st.takeItems(5575,24000)
            st.takeItems(57,2400)
            st.giveItems(5591,100)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate CP poition x10
    if event == "40":
        if st.getQuestItemsCount(5575)>=6000 and st.getQuestItemsCount(57)>=600:
            st.takeItems(5575,6000)
            st.takeItems(5965,600)
            st.giveItems(5592,10)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate CP poition x100
    if event == "41":
        if st.getQuestItemsCount(5575)>=60000 and st.getQuestItemsCount(57)>=6000:
            st.takeItems(5575,60000)
            st.takeItems(5965,6000)
            st.giveItems(5592,100)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate Healing Poition x10
    if event == "42":
        if st.getQuestItemsCount(5575)>=10800 and st.getQuestItemsCount(57)>=1080:
            st.takeItems(5575,10800)
            st.takeItems(57,1080)
            st.giveItems(1539,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

# Grate Healing Poition x100
    if event == "43":
        if st.getQuestItemsCount(5575)>=108000 and st.getQuestItemsCount(57)>=10800:
            st.takeItems(5575,108000)
            st.takeItems(57,10800)
            st.giveItems(1539,1)
            htmltext = "Item has been succesfully purchased."
        else:
            htmltext = "You do not have enough ancient adena."

    if event == "0":
      htmltext = "Cancel."
    
    st.setState(COMPLETED)
    st.exitQuest(1)
    return htmltext


QUEST       = Quest(9999,"9999_prodavalka","custom")
CREATED     = State('Start', QUEST)
STARTED     = State('Started', QUEST)
COMPLETED   = State('Completed', QUEST)

QUEST.setInitialState(CREATED)

for item in NPC:
### Quest NPC starter initialization
   QUEST.addStartNpc(item)
### Quest NPC initialization
   STARTED.addTalkId(item)
