CREATE TABLE IF NOT EXISTS `castle_hired_guards` (
  `itemId` MEDIUMINT UNSIGNED NOT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  `z` INT NOT NULL,
  `heading` MEDIUMINT NOT NULL,
  UNIQUE KEY (`x`,`y`,`z`)
) DEFAULT CHARSET=utf8;
