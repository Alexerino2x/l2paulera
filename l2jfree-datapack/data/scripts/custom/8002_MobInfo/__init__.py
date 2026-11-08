import sys
from com.l2jfree.gameserver.model.quest 				import State
from com.l2jfree.gameserver.model.quest 				import QuestState
from com.l2jfree.gameserver.datatables 				import NpcTable
from com.l2jfree.gameserver.gameobjects.templates 				import L2NpcTemplate
from com.l2jfree.gameserver.gameobjects		import L2Player
from com.l2jfree.gameserver.datatables   			import SpawnTable
from com.l2jfree.gameserver.datatables				import ItemTable
from com.l2jfree.gameserver.network.packets.server	import RadarControl
from com.l2jfree.gameserver.model.quest.jython 		import QuestJython as JQuest

qn = "8002_MobInfo"

NPC = [31830,31813,31811,31833,31835,31793,31808,31814,31798,31816,31798,31802,31778,31779,31794,31784,31828,31791,
		31830,31795,32337,31828,31822,31804,31831,31841,31826,31729,31803,31839,31775,31815,31833,31837,31821,31834,
		31821,31734,31786,31785,31827,31824,31799,31837,31776,31832,31799,31820,31738,31832,31825,31806,31840,31817,
		31791,31783,31834,31782,31825,31734,31777,31801,31816,31790,31797,31788,31806,31841,31826,31781,32340,31819,
		31838,31812,31796,31839,31788,31733,31829,31793,31809,31836,31823,31780,31823,31738,31810,31797,31792,31800,
		31808,31805,31794,31838,31820,31732,31836,31807,31789,31733,31827,31789,31796,31818,31790,31792,31831,32339,
		31805,31795,31822,31800,32338,31819,31807,31732,31835,31814,31787,31815,31840,31824,31817]

class Quest (JQuest) :

	def __init__(self,id,name,descr): JQuest.__init__(self,id,name,descr)

	def onAdvEvent (self,event,npc,player) :

		def message (text) :
			return L2Player.sendMessage (player, text)

	#main sub begin
		htmltext = "mobinfo.htm"
		st = player.getQuestState(qn)
		if not st: return htmltext
		#ha ha 
		if st.getQuestItemsCount(57) < 100: 
			message ("You not have enought adena for payment this service.")
			return
		st.takeItems(57,100)
		#take 10 adenas for every search
		text = "mobinfo.htm"
		search_by = ''
		param = ''
		page = ''
		inputs = event.split (' ')
		length = len(inputs)
		if length < 3: return htmltext
		search_by = inputs[0]
		#get input data
		if search_by == "m":
			x = int(inputs[1])
			y = int(inputs[2])
			z = int(inputs[3])
		else:
			if inputs[1].isdigit():
				page = int(inputs[1])
			else:
				message ("Incorrect search name!")
				return htmltext
			for i in range(2,length):
				param += inputs[i] + " "
			param = param.strip()

		#find by level
		if search_by == "l" :
			if param.isdigit():
				level = int(param)
				if not (level > 0 and level <100): 
					return "Please enter level between 1 and 99."
				npcData = []
				for t in NpcTable.getInstance().getAllTemplates():
					if  t.getLevel() == level:
						npcData.append(t)
				if npcData:
					length = len(npcData)
					maxPerPage = 15
					maxPages = int(length / maxPerPage)
					if length > maxPerPage * maxPages: maxPages = maxPages + 1
					if page > maxPages: page = maxPages
					start = maxPerPage * page
					end = length
					if (end - start) > maxPerPage: end = start + maxPerPage
					text = "<html><title>Monster by Level</title><body>"
					text += "<table width=260><tr>"
					if page == 0:
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo l "+ str(page-1) + " " + str(level) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					text += "<td width=180><center><a action=\"bypass -h Quest 8002_MobInfo\">Search Result by Level</a><br>Lvl:" + str(level) + " Found:" + str(length) + " Page:" + str(page+1)+ "/" + str(maxPages) +"</center></td>"
					if (page + 1) < maxPages:
						text += "<td width=40><button value=\"Next\" action=\"bypass -h Quest 8002_MobInfo l "+ str(page+1) + " " + str(level) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40></td>"
					text += "</tr></table>"
					text += "<table width=\"100%\">"
					text += "<tr><td><font color=\"LEVEL\">Name</font></td><td width=70><font color=\"LEVEL\">Drop&Spoil</font></td></tr>"
					for i in range(start,end):
						intagro = npcData[i].getAggroRange()
						if intagro > 0: agro = "<font color=\"LEVEL\">*</font>" 
						else: agro = " "
						name = npcData[i].getName()
						if name == '' or name == ' ' or name == '   ':
							name = "noname"
						text += "<tr><td><a action=\"bypass -h Quest 8002_MobInfo s 0 " + str(npcData[i].getNpcId()) + "\">" + name + "</a>" + agro + "</td>"
						text += "<td><center><button value=\"Show\" action=\"bypass -h Quest 8002_MobInfo d 0 " + str(npcData[i].getNpcId()) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr>"
					text += "</table>"
					text += "</body></html>"
					return text
				else:
					message ("I dont know any Monster in level " + str(level) + ".")
			else:
				message ("Please tell me a number between 1 and 99")
		#find by name
		if search_by == "n" :
			count = len(param)
			if count > 3:
				name = param.replace('_',' ')
				npcData = []
				for t in NpcTable.getInstance().getAllTemplates():
					if name.lower() in t.getName().lower():
						npcData.append(t)
				if npcData:
					length = len(npcData)
					maxPerPage = 15
					maxPages = int(length / maxPerPage)
					if length > maxPerPage * maxPages: maxPages = maxPages + 1
					if page > maxPages: page = maxPages
					start = maxPerPage * page
					end = length
					if (end - start) > maxPerPage: end = start + maxPerPage
					text = "<html><title>Monster</title><body>"
					text += "<table width=260><tr>"
					if page == 0:
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo n "+ str(page-1) + " " + str(name) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					text += "<td width=180><center><a action=\"bypass -h Quest 8002_MobInfo\">Search Result by Level</a><br>Name:" + str(name) + " Found:" + str(length) + " Page:" + str(page+1)+ "/" + str(maxPages) +"</center></td>"
					if (page + 1) < maxPages:
						text += "<td width=40><button value=\"Next\" action=\"bypass -h Quest 8002_MobInfo n "+ str(page+1) + " " + str(name) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40></td>"
					text += "</tr></table>"
					text += "<table width=\"100%\">"
					text += "<tr><td><font color=\"LEVEL\">Name</font></td><td><font color=\"LEVEL\">Level</font></td><td width=60><font color=\"LEVEL\">Drop&Spoil</font></td></tr>"
					for i in range(start,end):
						intagro = npcData[i].getAggroRange()
						if intagro > 0: agro = "<font color=\"LEVEL\">*</font>" 
						else: agro = " "
						text += "<tr><td><a action=\"bypass -h Quest 8002_MobInfo s 0 " + str(npcData[i].getNpcId()) + "\">" + npcData[i].getName() + "</a></td>" + "<td>" + str(npcData[i].getLevel()) + agro + "</td>"
						text += "<td><center><button value=\"Show\" action=\"bypass -h Quest 8002_MobInfo d 0 " + str(npcData[i].getNpcId()) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr>"
					text += "</table>"
					text += "</body></html>"
					return text
				else:
					message ("Not found NPC name " + str(name) + ".")
			else:
				message ("I dont understand you, please enter 4 or more chars!")
		#find by item name
		if search_by == "i" :
			count = len(param)
			if count > 3:
				name = param.replace('_',' ')
				items = ItemTable.getInstance().findItemsByName(name)
				if items:
					length = len(items)
					maxPerPage = 15
					maxPages = int(length / maxPerPage)
					if length > maxPerPage * maxPages: maxPages = maxPages + 1
					if page > maxPages: page = maxPages
					start = maxPerPage * page
					end = length
					if (end - start) > maxPerPage: end = start + maxPerPage
					text = "<html><title>Items:</title><body>"
					text += "<table width=260><tr>"
					if page == 0:
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo i "+ str(page-1) + " " + str(name) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					text += "<td width=180><center><a action=\"bypass -h Quest 8002_MobInfo\">Search result for items</a><br>Name:" + str(name) + " Found:" + str(length) + " Page:" + str(page+1)+ "/" + str(maxPages) +"</center></td>"
					if (page + 1) < maxPages:
						text += "<td width=40><button value=\"Next\" action=\"bypass -h Quest 8002_MobInfo i "+ str(page+1) + " " + str(name) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40></td>"
					text += "</tr></table>"
					text += """<table width="100%">"""
					text += "<tr><td width=40></td><td width=260><center><font color=\"LEVEL\">Name                 </font></center></td></tr>"
					for i in range(start,end):
						text += """<tr><td width=40></td><td width="100%"><a action=\"bypass -h Quest 8002_MobInfo x 0 """ + str(items[i].getItemId()) + """">""" + items[i].getName() + "</a></td></tr>"
					text += "</table>"
					text += "</body></html>"
					return text
				else:
					message ("I dont know any item by this name: " + str(name) + ".")
			else:
				message ("Be more precise, enter 4 or more chars!")

		#find by itemid
		if search_by == "x" :
			npcData = NpcTable.getInstance().getMobsByDrop(int(param))
			if npcData:
					length = len(npcData)
					maxPerPage = 15
					maxPages = int(length / maxPerPage)
					if length > maxPerPage * maxPages: maxPages = maxPages + 1
					if page > maxPages: page = maxPages
					start = maxPerPage * page
					end = length
					if (end - start) > maxPerPage: end = start + maxPerPage
					text = "<html><title>Monster</title><body>"
					text += "<table width=260><tr>"
					if page == 0:
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo n "+ str(page-1) + " " + str(param) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					text += "<td width=180><center><a action=\"bypass -h Quest 8002_MobInfo\">Search Result by Level</a><br>Name:" + str(param) + " Found:" + str(length) + " Page:" + str(page+1)+ "/" + str(maxPages) +"</center></td>"
					if (page + 1) < maxPages:
						text += "<td width=40><button value=\"Next\" action=\"bypass -h Quest 8002_MobInfo n "+ str(page+1) + " " + str(param) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40></td>"
					text += "</tr></table>"
					text += "<table width=\"100%\">"
					text += "<tr><td><font color=\"LEVEL\">Name</font></td><td><font color=\"LEVEL\">Level</font></td><td width=60><font color=\"LEVEL\">Drop&Spoil</font></td></tr>"
					for i in range(start,end):
						intagro = npcData[i].getAggroRange()
						if intagro > 0: agro = "<font color=\"LEVEL\">*</font>" 
						else: agro = " "
						text += "<tr><td><a action=\"bypass -h Quest 8002_MobInfo s 0 " + str(npcData[i].getNpcId()) + "\">" + npcData[i].getName() + "</a></td>" + "<td>" + str(npcData[i].getLevel()) + agro + "</td>"
						text += "<td><center><button value=\"Show\" action=\"bypass -h Quest 8002_MobInfo d 0 " + str(npcData[i].getNpcId()) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr>"
					text += "</table>"
					text += "</body></html>"
					return text
			else:
				message ("No monster obtains this item, you must search it somewhere besides the ancient battlegrounds!")

		#show spawns for ID
		if search_by == "s" :
			id = int(param)
			text = ''
			SpawnData = []
			npcData = NpcTable.getInstance().getTemplate(id)
			for t in SpawnTable.getInstance().getSpawnTable().values():
				if  t.getNpcId() == id:
					SpawnData.append(t)
			if SpawnData:
				length = len(SpawnData)
				maxPerPage = 15
				maxPages = int(length / maxPerPage)
				if length > maxPerPage * maxPages: maxPages = maxPages + 1
				if page > maxPages: page = maxPages
				start = maxPerPage * page
				end = length
				if (end - start) > maxPerPage: end = start + maxPerPage
				intagro = npcData.getAggroRange()
				if intagro > 0: agro = "<font color=\"LEVEL\">*</font>" 
				else: agro = " "
				text = "<html><title>Spawns</title><body>"
				text += "<table width=260><tr>"
				if page == 0:
					text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo l 0 " + str(npcData.getLevel()) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
				else :
					text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo s "+ str(page-1) + " " + str(id) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
				text += "<td width=180><center><a action=\"bypass -h Quest 8002_MobInfo\">Spawns Search Result</a></center></td>"
				if (page + 1) < maxPages:
					text += "<td width=40><button value=\"Next\" action=\"bypass -h Quest 8002_MobInfo s "+ str(page+1) + " " + str(id) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
				else :
					text += "<td width=40></td>"
				text += "</tr></table>"
				text += "<center><font color=\"LEVEL\">Level " + str(npcData.getLevel()) + " - " + str(npcData.getName()) + agro + "</font><br>"
				text += "Found:" + str(length) + " Page:" + str(page+1)+ "/" + str(maxPages) +"</center>"
				text += "<table width=\"100%\">"
				text += "<tr><td><center><font color=\"LEVEL\">X         Y         Z</font></center></td></tr>"
				for i in range(start,end):
					xyz = str(SpawnData[i].getLocx()) + " " + str(SpawnData[i].getLocy()) + " " + str(SpawnData[i].getLocz())
					text += "<tr><td><center><a action=\"bypass -h Quest 8002_MobInfo m " + xyz + "\">" + xyz + "</center></td></tr>"
				text += "</table>"
				text += "</body></html>"
				return text
			else:
				message ("No spawn found for monster " + str(npcData.getLevel()) + " - " + npcData.getName() + ".")

		#show Radar
		if search_by == "m" :
			st.addRadar(x,y,z)
			return
	
		# drop and spoil show
		if search_by == "d":
			id = int(param)
			DropData = []
			SweepData = []
			npcData = NpcTable.getInstance().getTemplate(id)
			intagro = npcData.getAggroRange()
			if intagro > 0: agro = "<font color=\"LEVEL\">*</font>" 
			else: agro = " "
			if npcData.getDropData():
				for cat in npcData.getDropData():
					for drop in cat.getAllDrops():
						DropData.append(drop)
						SweepData.append(cat)
				if DropData:
					length = len(DropData)
					maxPerPage = 15
					maxPages = int(length / maxPerPage)
					if length > maxPerPage * maxPages: maxPages = maxPages + 1
					if page > maxPages: page = maxPages
					start = maxPerPage * page
					end = length
					if (end - start) > maxPerPage: end = start + maxPerPage
					text = "<html><title>Moblocator</title><body>"
					text += "<table width=260><tr>"
					if page == 0:
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo l 0 " + str(npcData.getLevel()) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40><button value=\"Back\" action=\"bypass -h Quest 8002_MobInfo d "+ str(page-1) + " " + str(id) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					text += "<td width=180><center><a action=\"bypass -h Quest 8002_MobInfo\">Drop and Spoil Result</a></center></td>"
					if (page + 1) < maxPages:
						text += "<td width=40><button value=\"Next\" action=\"bypass -h Quest 8002_MobInfo d "+ str(page+1) + " " + str(id) + "\" width=40 height=15 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>"
					else :
						text += "<td width=40></td>"
					text += "</tr></table>"
					text += "<center><font color=\"LEVEL\">Level " + str(npcData.getLevel()) + " - " + str(npcData.getName()) + agro + "</font><br>"
					text += "Found:" + str(length) + " Page:" + str(page+1)+ "/" + str(maxPages) +"</center>"
					text += "<table width=260>"
					i = 0
					for i in range(start,end):
						itemname = ItemTable.getInstance().getTemplate(DropData[i].getItemId()).getName()
						type = ""
						if DropData[i].isQuestDrop(): 
							type = "Qu"
						if SweepData[i].isSweep(): 
							type = "<font color=\"LEVEL\">Sw</font>" 
						else:
							type = ""
						text += "<tr>"
						text += "<td width=\"100%\">" + itemname + "</td>"
						text += "<td width=35>" + type + "</td>"
						text += "<td width=90>" + str(float(DropData[i].getChance())/10000) + "</td>"
						text += "</tr>"
					text += "</table>"
					text += "</body></html>"
			else:
				message ("No data found for Level " + str(npcData.getLevel()) + " - " + str(npcData.getName()))
			return text
		return htmltext

	def onTalk (self,npc,player):
		npcId = npc.getNpcId()
		if npcId in NPC :
			htmltext = "mobinfo.htm"
		return htmltext

QUEST = Quest(8002,qn,"custom")

for i in NPC:
	QUEST.addStartNpc(i)
	QUEST.addTalkId(i)
