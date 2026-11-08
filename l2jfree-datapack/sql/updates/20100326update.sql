REPLACE INTO `hellbounds`
	(SELECT 'trust_points', `value` FROM `quest_global_data` WHERE `quest_name` = 'Hellbound' AND `var` = 'HellboundPoints');

REPLACE INTO `hellbounds`
	(SELECT 'warpgates_energy', `value` FROM `quest_global_data` WHERE `quest_name` = '1108_Hellbound_WarpGate' AND `var` = 'WarpGateEnergy');
