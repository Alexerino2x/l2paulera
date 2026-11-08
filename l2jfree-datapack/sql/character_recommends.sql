CREATE TABLE IF NOT EXISTS `character_recommends` (
  `charId` INT UNSIGNED NOT NULL,
  `target_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId`,`target_id`)
) DEFAULT CHARSET=utf8;
