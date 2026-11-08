CREATE TABLE IF NOT EXISTS `custom_npcskills` (
  `npcid` INT(11) NOT NULL DEFAULT 0,
  `skillid` INT(11) NOT NULL DEFAULT 0,
  `level` INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`npcid`,`skillid`,`level`)
) DEFAULT CHARSET=utf8;
