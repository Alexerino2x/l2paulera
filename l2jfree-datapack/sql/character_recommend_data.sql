CREATE TABLE IF NOT EXISTS `character_recommend_data` (
  `charId` INT UNSIGNED NOT NULL,
  `evaluationAble` TINYINT(1) UNSIGNED NOT NULL DEFAULT 3,
  `evaluationPoints` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `lastUpdate` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId`)
) DEFAULT CHARSET=utf8;
