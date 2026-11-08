CREATE TABLE IF NOT EXISTS `character_friends` (
  `charId1` INT UNSIGNED NOT NULL,
  `charId2` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId1`,`charId2`)
) DEFAULT CHARSET=utf8;
