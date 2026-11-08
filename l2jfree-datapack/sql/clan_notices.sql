CREATE TABLE IF NOT EXISTS `clan_notices` (
  `clanID` INT(32) NOT NULL,
  `notice` TEXT NOT NULL,
  `enabled` enum('true','false') DEFAULT 'false' NOT NULL,
  PRIMARY KEY  (`clanID`)
) DEFAULT CHARSET=utf8;