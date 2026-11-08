CREATE TABLE IF NOT EXISTS `character_blocks` (
  `charId` INT UNSIGNED NOT NULL,
  `name` VARCHAR(35) NOT NULL,
  PRIMARY KEY (`charId`,`name`)
) DEFAULT CHARSET=utf8;
