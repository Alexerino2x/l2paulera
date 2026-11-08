CREATE TABLE IF NOT EXISTS `castle_doorupgrade` (
  `doorId` INT NOT NULL,
  `level` TINYINT(1) UNSIGNED NOT NULL,
  PRIMARY KEY (`doorId`)
) DEFAULT CHARSET=utf8;
