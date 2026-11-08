CREATE TABLE IF NOT EXISTS `character_instance_time` (
  `charId` INT UNSIGNED NOT NULL,
  `instanceId` SMALLINT UNSIGNED NOT NULL,
  `time` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId`,`instanceId`)
) DEFAULT CHARSET=utf8;
