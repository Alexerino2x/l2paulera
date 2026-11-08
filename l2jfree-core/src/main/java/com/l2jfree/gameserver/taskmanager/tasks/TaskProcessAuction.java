/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree.gameserver.taskmanager.tasks;

import com.l2jfree.gameserver.communitybbs.Manager.AuctionBBSManager;
import com.l2jfree.gameserver.taskmanager.tasks.TaskManager.ExecutedTask;

/**
 * 
 * @author Vital
 * 
 */
public class TaskProcessAuction extends TaskHandler
{
	TaskProcessAuction()
	{
		TaskManager.addUniqueTask(getName(), TaskTypes.TYPE_FIXED_SHEDULED, "3600000", "3600000", "");
	}
	
	@Override
	void onTimeElapsed(ExecutedTask task, String[] params)
	{
		AuctionBBSManager.getInstance().processAuctions();
		AuctionBBSManager.getInstance().removeOldAuctions();
		_log.info("Process Auction Task: launched.");
	}
	
}
