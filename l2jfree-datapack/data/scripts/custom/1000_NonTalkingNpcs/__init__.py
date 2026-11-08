# Script is used for preventing displaying html for npcs that dont have html on retail
# Visit http://forum.l2jdp.com for more details.
import sys
from com.l2jfree.gameserver.model.quest import Quest as JQuest
from com.l2jfree.gameserver.network.packets.server import ActionFailed

#NPC
RED_STAR_STONE_1    = 18684
RED_STAR_STONE_2    = 18685
RED_STAR_STONE_3    = 18686
BLUE_STAR_STONE_1   = 18687
BLUE_STAR_STONE_2   = 18688
BLUE_STAR_STONE_3   = 18689
GREEN_STAR_STONE_1  = 18690
GREEN_STAR_STONE_2  = 19691
GREEN_STAR_STONE_3  = 18692
MERCENARY_CENTRY    = 31557
ALICE_DE_CATRINA    = 31606
PATROL_1            = 31671
PATROL_2            = 31672
PATROL_3            = 31673
PATROL_4            = 31674
HESTUI_GUARD        = 32026
GARDEN_SCULPTURE    = 32030
ICE_FAIRY_SCULPTURE = 32031
STRANGE_MACHINE     = 32032
NO_NAME_NPC_1       = 32619
NO_NAME_NPC_2       = 32619
NO_NAME_NPC_3       = 32620
NO_NAME_NPC_4       = 32621

NPCs = [RED_STAR_STONE_1, RED_STAR_STONE_2, RED_STAR_STONE_3, BLUE_STAR_STONE_1,
	    BLUE_STAR_STONE_2, BLUE_STAR_STONE_3, GREEN_STAR_STONE_1, GREEN_STAR_STONE_2, GREEN_STAR_STONE_3,
	    MERCENARY_CENTRY, ALICE_DE_CATRINA, PATROL_1, PATROL_2, PATROL_3, PATROL_4,
	    HESTUI_GUARD, GARDEN_SCULPTURE, ICE_FAIRY_SCULPTURE, STRANGE_MACHINE, NO_NAME_NPC_1,
	    NO_NAME_NPC_2, NO_NAME_NPC_3, NO_NAME_NPC_4]

class Quest(JQuest) :
    def __init__(self,id,name,descr):
        JQuest.__init__(self,id,name,descr)

    def onFirstTalk(self,npc,player):
        player.sendPacket(ActionFailed.STATIC_PACKET)
        return None

QUEST      = Quest(-1,".","custom")
for i in NPCs :
  QUEST.addFirstTalkId(i)