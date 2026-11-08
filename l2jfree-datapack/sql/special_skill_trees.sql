DROP TABLE IF EXISTS `special_skill_trees`;
CREATE TABLE `special_skill_trees` (
  `skill_id` INT(10) NOT NULL default 0,
  `level` INT(10) NOT NULL default 0,
  `name` VARCHAR(25) NOT NULL default '',
  `costid` INT(10) NOT NULL default 0,
  `cost` INT(10) NOT NULL default 0,
  PRIMARY KEY (`skill_id`,`level`)
) DEFAULT CHARSET=utf8;

INSERT INTO `special_skill_trees` VALUES
(932,1,'Star Stone',13728,1),
(932,2,'Star Stone',57,400000),
(932,3,'Star Stone',57,1200000);