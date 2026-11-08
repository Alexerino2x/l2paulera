### ---------------------------------------------------------------------------
### <history>
###		
### </history>
### ---------------------------------------------------------------------------

### Settings
qn = "6009_marks"
NPC         = [7097]
QuestId     = 6009
QuestName   = "marks"
QuestDesc   = "custom"
#QuestDesc   = "Buy mark with adena"
InitialHtml = "1.htm"
SuccessMsg  = ""
FailureMsg  = "You do not have enough adena."
CancelMsg   = "1.htm"

### Items - Format [name, eventId, [giveItems], [takeitems], [teleLocation x, teleLocation y, teleLocation z]]
### giveItems - Format [itemId, qty]
### takeItems - Format [itemId, qty]
### example: 
### Items = [
###     ["MyItem1", 1001, [[ 234,   10], [ 333,    1]], [[ 563,  100], [ 363,  150]], [-80826,149775,-3043]],
###     ["MyItem2", 1002, [[ 453,    1], [  63,    1]], [[  23,   10], [ 774,  100]], [-80826,149775,-3043]]
### ]
Items       = [
    ["Mark of Challenger", 5001, [[2627, 1]],[[57, 1000000]], []],
    ["Mark of Duty",       5002, [[2633, 1]],[[57, 1000000]], []],
    ["Mark of Seeker",     5003, [[2673, 1]],[[57, 1000000]], []],
    ["Mark of Scholar",    5004, [[2674, 1]],[[57, 1000000]], []],
    ["Mark of Pilgrim",    5005, [[2721, 1]],[[57, 1000000]], []],
    ["Mark of Guildsman",  5006, [[3119, 1]],[[57, 1000000]], []],
    ["Mark of Trust",      5007, [[2734, 1]],[[57, 1000000]], []],
    ["Mark of Life",       5008, [[3140, 1]],[[57, 1000000]], []],
    ["Mark of Fate",       5009, [[3172, 1]],[[57, 1000000]], []],
    ["Mark of Glory",      5010, [[3203, 1]],[[57, 1000000]], []],
    ["Mark of Prosperity", 5011, [[3238, 1]],[[57, 1000000]], []],
    ["Mark of Duelist",    5012, [[2762, 1]],[[57, 1000000]], []],
    ["Mark of Champion",   5013, [[3276, 1]],[[57, 1000000]], []],
    ["Mark of Healer",     5014, [[2820, 1]],[[57, 1000000]], []],
    ["Mark of Witchcraft", 5015, [[3307, 1]],[[57, 1000000]], []],
    ["Mark of Searcher",   5016, [[2809, 1]],[[57, 1000000]], []],
    ["Mark of Sagittarius",5017, [[3293, 1]],[[57, 1000000]], []],
    ["Mark of Magus",      5018, [[2840, 1]],[[57, 1000000]], []],
    ["Mark of Summoner",   5019, [[3336, 1]],[[57, 1000000]], []],
    ["Mark of Reformer",   5020, [[2821, 1]],[[57, 1000000]], []],
    ["Mark of Lord",       5022, [[3390, 1]],[[57, 1000000]], []],
    ["Mark of War Spirit", 5023, [[2879, 1]],[[57, 1000000]], []],
    ["Mark of Maestro",    5024, [[2867, 1]],[[57, 1000000]], []]
]

### ---------------------------------------------------------------------------
### DO NOT MODIFY BELOW THIS LINE
### ---------------------------------------------------------------------------

print "importing " + str(QuestId) + ": " + QuestDesc,
import sys
from com.l2jfree.gameserver.model.quest import State
from com.l2jfree.gameserver.model.quest import QuestState
from com.l2jfree.gameserver.model.quest.jython import QuestJython as JQuest

### Events
def do_Validate(st, items) :
    if len(items) > 0 :
        for item in items:
            if st.getQuestItemsCount(item[0]) < item[1] :
                return False
    return True

def do_GiveItems(st, items) :
    if len(items) > 0 :
        for item in items:
            st.giveItems(item[0], item[1])

def do_TakeItems(st, items) :
    if len(items) > 0 :
        for item in items:
            st.takeItems(item[0], item[1])

def do_Teleport(st, items) :
    if len(items) > 0 :
        st.player.teleToLocation(items[0], items[1], items[2])

def do_RequestedEvent(event, st, item) :
    if do_Validate(st, item[3]) :
        do_TakeItems(st, item[3])
        do_GiveItems(st, item[2])
        do_Teleport(st, item[4])
        if SuccessMsg != "" :
            return SuccessMsg
        return event + ".htm"
    else :
        if FailureMsg != "" :
            return FailureMsg
        return event + "-0.htm"

def do_RequestEvent(event,st) :
    htmltext = event

    if event == "0":
        if CancelMsg != "" :
            return CancelMsg
        return "Transaction has been canceled."

    for item in Items:
        if event == str(item[1]):
            return do_RequestedEvent(event, st, item)

	if htmltext != event:
		st.setState(COMPLETED)
		st.exitQuest(1)

    return htmltext

### main code
class Quest (JQuest) :

 def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

 def onEvent (self,event,st) :
    return do_RequestEvent(event,st)

 def onTalk (self,npc,player):

   npcId = npc.getNpcId()
   st = player.getQuestState(qn)
   htmltext = "<html><body>I have nothing to say with you</body></html>"
   id = st.getState()
   st.setState(STARTED)
   if InitialHtml == "onEvent" :
     return do_RequestEvent(str(npcId),st)
   elif InitialHtml != "" :
     return InitialHtml
   return htmltext

### Quest class and state definition
QUEST       = Quest(QuestId, str(QuestId) + "_" + QuestName, QuestDesc)
CREATED     = State('Start',     QUEST)
STARTED     = State('Started',   QUEST)
COMPLETED   = State('Completed', QUEST)

### Quest initialization
QUEST.setInitialState(CREATED)

for item in NPC:
### Quest NPC starter initialization
   QUEST.addStartNpc(item)

### Quest NPC initialization
   QUEST.addTalkId(item)

print  ": Loaded " + str(len(Items)) + " item(s)"
