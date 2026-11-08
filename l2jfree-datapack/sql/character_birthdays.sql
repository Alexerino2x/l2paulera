CREATE TABLE IF NOT EXISTS `character_birthdays` (
  `charId` INT UNSIGNED NOT NULL,
  `lastClaim` SMALLINT(4) UNSIGNED NOT NULL,
  `birthDate` DATE NOT NULL,
  PRIMARY KEY (`charId`)
) DEFAULT CHARSET=utf8;
