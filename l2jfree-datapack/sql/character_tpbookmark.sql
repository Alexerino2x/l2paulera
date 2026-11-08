CREATE TABLE IF NOT EXISTS `character_tpbookmark` (
  `charId` INT(20) NOT NULL,
  `Id` INT(20) NOT NULL,
  `x` INT(20) NOT NULL,
  `y` INT(20) NOT NULL,
  `z` INT(20) NOT NULL,
  `icon` INT(20) NOT NULL,
  `tag` VARCHAR(20) DEFAULT NULL,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`charId`,`Id`)
) DEFAULT CHARSET=utf8;
