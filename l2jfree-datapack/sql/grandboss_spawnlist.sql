CREATE TABLE IF NOT EXISTS `grandboss_spawnlist` (
  `boss_id` INT NOT NULL DEFAULT 0,
  `loc_x` INT NOT NULL DEFAULT 0,
  `loc_y` INT NOT NULL DEFAULT 0,
  `loc_z` INT NOT NULL DEFAULT 0,
  `heading` INT NOT NULL DEFAULT 0,
  `respawn_min_delay` INT(11) NOT NULL DEFAULT 86400,
  `respawn_max_delay` INT(11) NOT NULL DEFAULT 129600,
  `respawn_time` BIGINT NOT NULL DEFAULT 0,
  `currentHp` decimal(8,0) DEFAULT NULL,
  `currentMp` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`boss_id`,`loc_x`,`loc_y`,`loc_z`)
) DEFAULT CHARSET=utf8;

-- This table stores spawn infos for all L2 bosses not currently managed by a lair instance.

INSERT IGNORE INTO `grandboss_spawnlist` VALUES
(29001,-21610,181594,-5734,0,86400,129600,0,229898,667),-- Queen Ant (40)
(29006,17726,108915,-6480,0,86400,129600,0,622493,575),-- Core (50)
(29014,55024,17368,-5412,10126,86400,129600,0,622493,1660),-- Orfen (50)
(29022,55312,219168,-3223,0,86400,129600,0,858518,1975),-- Zaken (60)
(22215,24767,-12441,-2532,15314,86400,129600,0,340753,2339),-- Tyrannosaurus (80)
-- (22215,28263,-17486,-2539,50052,86400,129600,0,340753,2339),-- Tyrannosaurus (80) -- TODO: Multiple instances per ID
-- (22215,18229,-17975,-3219,65140,86400,129600,0,340753,2339),-- Tyrannosaurus (80)
(22216,19897,-9087,-2781,2686,86400,129600,0,340753,2339),-- Tyrannosaurus (80)
(22217,22827,-14698,-3080,53946,86400,129600,0,340753,2339); -- Tyrannosaurus (80)