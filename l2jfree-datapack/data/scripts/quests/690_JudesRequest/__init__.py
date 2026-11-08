import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

qn = "690_JudesRequest"

# Npc
JUDE = 32356

# Quest Item
EVIL_WEAPON = 10327

# Mobs
MOBS = [22398,22399]

DROP_CHANCE = 60

class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

	def onEvent (self,event,st) :
		htmltext = event
		if event == "32356-02.htm" :
			st.set("cond","1")
			st.setState(State.STARTED)
			st.playSound("ItemSound.quest_accept")
		elif event == "32356-06.htm" :
			st.playSound("ItemSound.quest_finish")
			st.exitQuest(1)
		return htmltext

	def onTalk (self,npc,player) :
		htmltext = "<html><body>You are either not on a quest that involves this NPC, or you don't meet this NPC's minimum quest requirements.</body></html>"
		st = player.getQuestState(qn)
		if not st : return htmltext
		id = st.getState()
		cond = st.getInt("cond")
		if player.getLevel() >= 78 :
			if id == State.CREATED :
				htmltext = "32356-01.htm"
				return htmltext
			elif id == State.STARTED and st.getQuestItemsCount(EVIL_WEAPON) >= 5:
				htmltext = "32356-03.htm"
				return htmltext
		else :
			htmltext = "32356-01a.htm"
			st.exitQuest(1)
		return htmltext

	def onKill(self,npc,player,isPet):
		st = player.getQuestState(qn)
		if not st : return
		if st.getState() != State.STARTED : return
		npcId = npc.getNpcId()
		if npcId in MOBS:
			if st.getRandom(100) < DROP_CHANCE:
				if npcId == 22398:
					st.giveItems(EVIL_WEAPON,1)
				else:
					st.giveItems(EVIL_WEAPON,2)
				st.playSound("ItemSound.quest_itemget")
		return

QUEST       = Quest(690,qn,"Jude's Request")

QUEST.addStartNpc(JUDE)

QUEST.addTalkId(JUDE)

for mobId in MOBS:
	QUEST.addKillId(mobId)