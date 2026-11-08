CREATE TABLE IF NOT EXISTS `grandboss_intervallist` (
  `bossId` INT(11) NOT NULL,
  `respawnDate` DECIMAL(20,0) NOT NULL,
  `state` INT(11) NOT NULL,
  PRIMARY KEY (`bossId`)
) DEFAULT CHARSET=utf8;

INSERT IGNORE INTO `grandboss_intervallist` (`bossId`,`respawnDate`,`state`) VALUES
(29019,0,0),
(29020,0,0),
(29028,0,0),
(29045,0,0),
(29062,0,0),
(29065,0,0),
(29099,0,0);