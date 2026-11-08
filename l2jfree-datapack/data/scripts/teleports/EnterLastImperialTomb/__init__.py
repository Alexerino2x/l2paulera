#Author: Psycho(killer1888) / L2jFree
import sys
from com.l2jfree                                             import Config
from com.l2jfree.gameserver.instancemanager.lastimperialtomb import LastImperialTombManager
from com.l2jfree.gameserver.model.quest                      import State
from com.l2jfree.gameserver.model.quest                      import QuestState
from com.l2jfree.gameserver.model.quest.jython               import QuestJython as JQuest

qn = "ImperialTombGuide"

class Quest (JQuest) :

    def __init__(self,id,name,descr): 
        JQuest.__init__(self,id,name,descr)
 
    def onTalk (self,npc,player):
        if Config.LIT_REGISTRATION_MODE == 0:
            if LastImperialTombManager.getInstance().tryRegistrationCc(player):
                LastImperialTombManager.getInstance().registration(player, npc)
        elif Config.LIT_REGISTRATION_MODE == 1:
            if LastImperialTombManager.getInstance().tryRegistrationPt(player):
                LastImperialTombManager.getInstance().registration(player, npc)
        else:
            if LastImperialTombManager.getInstance().tryRegistrationPc(player):
                LastImperialTombManager.getInstance().registration(player, npc)
        return

QUEST = Quest(-1, qn, "Teleports")

QUEST.addStartNpc(32011)
QUEST.addTalkId(32011)