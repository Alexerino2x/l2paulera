CREATE TABLE IF NOT EXISTS `character_skill_reuses` (
  `charId` INT UNSIGNED NOT NULL,
  `skillId` SMALLINT UNSIGNED NOT NULL,
  `reuseDelay` INT UNSIGNED NOT NULL,
  `expiration` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId`,`skillId`)
) DEFAULT CHARSET=utf8;
