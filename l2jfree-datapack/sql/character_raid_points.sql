CREATE TABLE IF NOT EXISTS `character_raid_points` (
  `charId` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `boss_id` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `points` int(11) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`,`boss_id`)
) DEFAULT CHARSET=utf8;
