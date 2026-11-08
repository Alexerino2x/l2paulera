# Fix by Cromir for Kilah
# Quest: Trial Of Challenger
import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "211_TrialOfChallenger"

# NPCS
FILAUR = 30535
KASH = 30644
MARTIEN = 30645
RALDO = 30646
CHEST_OF_SHYSLASSYS = 30647
SHYSLASSYS = 27110
GORR = 27112
BARAHAM = 27113
SUCCUBUS_QUEEN = 27114

# ITEMS
LETTER_OF_KASH = 2628
SCROLL_OF_SHYSLASSY = 2631
WATCHERS_EYE1 = 2629
BROKEN_KEY = 2632
MITHRIL_SCALE_GAITERS_MATERIAL = 2918
BRIGANDINE_GAUNTLET_PATTERN = 2927
MANTICOR_SKIN_GAITERS_PATTERN = 1943
GAUNTLET_OF_REPOSE_OF_THE_SOUL_PATTERN = 1946
IRON_BOOTS_DESIGN = 1940
TOME_OF_BLOOD_PAGE = 2030
ELVEN_NECKLACE_BEADS = 1904
WHITE_TUNIC_PATTERN = 1936
ADENA = 57
MARK_OF_CHALLENGER = 2627
WATCHERS_EYE2 = 2630
DIMENSIONAL_DIAMOND = 7562

class Quest (JQuest) :

 def __init__(self,id,name,descr):
     JQuest.__init__(self,id,name,descr)
     self.questItemIds = [SCROLL_OF_SHYSLASSY, LETTER_OF_KASH, WATCHERS_EYE1, BROKEN_KEY, WATCHERS_EYE2]

 def onAdvEvent (self,event,npc, player) :
    htmltext = event
    st = player.getQuestState(qn)
    if not st : return
    if event == "1" :
      htmltext = "30644-05.htm"
      st.set("cond","1")
      st.setState(State.STARTED)
      st.playSound("ItemSound.quest_accept")
    elif event == "30644_1" :
          htmltext = "30644-04.htm"
    elif event == "30645_1" :
          htmltext = "30645-02.htm"
          st.takeItems(LETTER_OF_KASH,1)
          st.set("cond","4")
          st.playSound("Itemsound.quest_middle")
    elif event == "30647_1" :
          if st.getQuestItemsCount(BROKEN_KEY) == 1 :
             st.giveItems(SCROLL_OF_SHYSLASSY,1)
             st.playSound("Itemsound.quest_middle")
             if st.getRandom(10) < 2 :
              htmltext = "30647-03.htm"
              st.takeItems(BROKEN_KEY,1)
              st.playSound("ItemSound.quest_jackpot")
              n = st.getRandom(100)
              if n > 90 :
                 st.giveItems(MITHRIL_SCALE_GAITERS_MATERIAL,1)
                 st.giveItems(BRIGANDINE_GAUNTLET_PATTERN,1)
                 st.giveItems(MANTICOR_SKIN_GAITERS_PATTERN,1)
                 st.giveItems(GAUNTLET_OF_REPOSE_OF_THE_SOUL_PATTERN,1)
                 st.giveItems(IRON_BOOTS_DESIGN,1)
                 st.playSound("Itemsound.quest_middle")
              elif n > 70 :
                 st.giveItems(TOME_OF_BLOOD_PAGE,1)
                 st.giveItems(ELVEN_NECKLACE_BEADS,1)
                 st.playSound("Itemsound.quest_middle")
              elif n > 40 :
                 st.giveItems(WHITE_TUNIC_PATTERN,1)
                 st.playSound("Itemsound.quest_middle")
              else:
                 st.giveItems(IRON_BOOTS_DESIGN,1)
                 st.playSound("Itemsound.quest_middle")
             else:
              htmltext = "30647-02.htm"
              n = st.getRandom(1000)+1
              st.takeItems(BROKEN_KEY,1)
              st.rewardItems(ADENA,n)
              st.playSound("Itemsound.quest_middle")
          else:
            htmltext = "30647-04.htm"
            st.takeItems(BROKEN_KEY,1)
    elif event == "30646_1" :
          htmltext = "30646-02.htm"
    elif event == "30646_2" :
          htmltext = "30646-03.htm"
    elif event == "30646_3" :
          htmltext = "30646-04.htm"
          st.set("cond","8")
          st.takeItems(WATCHERS_EYE2,1)
    elif event == "30646_4" :
          htmltext = "30646-06.htm"
          st.set("cond","8")
          st.takeItems(WATCHERS_EYE2,1)
    return htmltext

 def onTalk (self,npc,player):
   htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
   st = player.getQuestState(qn)
   if not st : return htmltext
   npcId = npc.getNpcId()
   id = st.getState()
   if npcId != KASH and id != State.STARTED : return htmltext
   cond=st.getInt("cond")
   if id == State.CREATED :
     if npcId == KASH :
        if player.getClassId().ordinal() in [0x01,0x13,0x20,0x2d,0x2f] :
           if player.getLevel() >= 35 :
              htmltext = "30644-03.htm"
           else :
              htmltext = "30644-01.htm"
              st.exitQuest(1)
        else :
           htmltext = "30644-02.htm"
           st.exitQuest(1)
   elif npcId == KASH and id == State.COMPLETED :
      htmltext = "<html><body>This quest has already been completed.</body></html>"
   elif npcId == KASH and cond == 1 :
      htmltext = "30644-06.htm"
   elif npcId == KASH and cond == 2 and st.getQuestItemsCount(SCROLL_OF_SHYSLASSY) == 1 :
      htmltext = "30644-07.htm"
      st.takeItems(SCROLL_OF_SHYSLASSY,1)
      st.giveItems(LETTER_OF_KASH,1)
      st.set("cond","3")
      st.playSound("Itemsound.quest_middle")
   elif npcId == KASH and cond == 1 and st.getQuestItemsCount(LETTER_OF_KASH) == 1 :
      htmltext = "30644-08.htm"
   elif npcId == KASH and cond >= 7 :
      htmltext = "30644-09.htm"
   elif npcId == MARTIEN and cond == 3 and st.getQuestItemsCount(LETTER_OF_KASH) == 1 :
      htmltext = "30645-01.htm"
   elif npcId == MARTIEN and cond == 4 and st.getQuestItemsCount(WATCHERS_EYE1) == 0 :
      htmltext = "30645-03.htm"
   elif npcId == MARTIEN and cond == 5 and st.getQuestItemsCount(WATCHERS_EYE1) :
      htmltext = "30645-04.htm"
      st.takeItems(WATCHERS_EYE1,1)
      st.set("cond","6")
      st.playSound("Itemsound.quest_middle")
   elif npcId == MARTIEN and cond == 6 :
      htmltext = "30645-05.htm"
   elif npcId == MARTIEN and cond >= 7 :
      htmltext = "30645-06.htm"
   elif npcId == CHEST_OF_SHYSLASSYS and cond == 2 :
      htmltext = "30647-01.htm"
   elif npcId == RALDO and cond == 7 and st.getQuestItemsCount(WATCHERS_EYE2) :
      htmltext = "30646-01.htm"
   elif npcId == RALDO and cond == 7 :
      htmltext = "30646-06a.htm"
   elif npcId == RALDO and cond == 10 :
      st.addExpAndSp(72394,11250)
      st.rewardItems(ADENA,97278)
      st.rewardItems(DIMENSIONAL_DIAMOND,8)
      htmltext = "30646-07.htm"
      st.takeItems(BROKEN_KEY,1)
      st.giveItems(MARK_OF_CHALLENGER,1)
      st.exitQuest(False)
      st.playSound("ItemSound.quest_finish")
      st.set("cond","0")
   elif npcId == FILAUR and cond == 7 :
      if player.getLevel() >= 35 :
        htmltext = "30535-01.htm"
        st.addRadar(176560,-184969,-3729);
        st.set("cond","8")
        st.playSound("Itemsound.quest_middle")
      else:
        htmltext = "30535-03.htm"
   elif npcId == FILAUR and cond == 8 :
      htmltext = "30535-02.htm"
      st.addRadar(176560,-184969,-3729);
      st.set("cond","9")
      st.playSound("Itemsound.quest_middle")
   return htmltext

 def onKill(self,npc,player,isPet):
   st = player.getQuestState(qn)
   if not st : return
   if st.getState() != State.STARTED : return
   cond = st.getInt("cond")
   npcId = npc.getNpcId()
   if npcId == SHYSLASSYS and cond == 1 and not st.getQuestItemsCount(BROKEN_KEY) :
      st.giveItems(BROKEN_KEY,1)
      st.addSpawn(CHEST_OF_SHYSLASSYS,npc,True,0)
      st.playSound("ItemSound.quest_middle")
      st.set("cond","2")
   elif npcId == GORR and cond == 4 and not st.getQuestItemsCount(WATCHERS_EYE1) :
      st.giveItems(WATCHERS_EYE1,1)
      st.set("cond","5")
      st.playSound("ItemSound.quest_middle")
   elif npcId == BARAHAM and cond == 6 and not st.getQuestItemsCount(WATCHERS_EYE2) :
      st.giveItems(WATCHERS_EYE2,1)
      st.playSound("ItemSound.quest_middle")
      st.set("cond","7")
      st.addSpawn(RALDO,npc,0,300000)
   elif npcId == SUCCUBUS_QUEEN and cond == 9 :
      st.set("cond","10")
      st.playSound("ItemSound.quest_middle")
      st.addSpawn(RALDO,npc,0,300000)
   return

QUEST       = Quest(211,qn,"Trial Of Challenger")

QUEST.addStartNpc(KASH)

QUEST.addTalkId(FILAUR)
QUEST.addTalkId(KASH)
QUEST.addTalkId(MARTIEN)
QUEST.addTalkId(RALDO)
QUEST.addTalkId(CHEST_OF_SHYSLASSYS)

QUEST.addKillId(SHYSLASSYS)
QUEST.addKillId(GORR)
QUEST.addKillId(BARAHAM)
QUEST.addKillId(SUCCUBUS_QUEEN)
