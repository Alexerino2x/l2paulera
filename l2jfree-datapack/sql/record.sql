CREATE TABLE IF NOT EXISTS `record` ( 
  `maxplayer` SMALLINT NOT NULL, 
  `date` DATE NOT NULL 
) DEFAULT CHARSET=utf8;

INSERT INTO `record` VALUES (0, NOW());