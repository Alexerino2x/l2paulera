import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "9003_Falk"

class Quest (JQuest) :

    def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

    def onTalk (Self,npc,player):
        item = player.getInventory().getItemByItemId(9674)
        if item:
            if item.getCount() >= 20:
                htmltext = "trade.htm"
            else:
                htmltext = "no.htm"
        else:
            htmltext = "no.htm"
        return htmltext



QUEST = Quest(9003,qn,"custom")

QUEST.addStartNpc(32297)
QUEST.addTalkId(32297)
