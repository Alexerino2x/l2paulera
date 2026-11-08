CREATE TABLE IF NOT EXISTS `custom_spawnlist` (
  `id` INT(11) NOT NULL auto_increment,
  `location` VARCHAR(40) NOT NULL DEFAULT '',
  `count` INT(9) NOT NULL DEFAULT 0,
  `npc_templateid` INT(9) NOT NULL DEFAULT 0,
  `locx` INT(9) NOT NULL DEFAULT 0,
  `locy` INT(9) NOT NULL DEFAULT 0,
  `locz` INT(9) NOT NULL DEFAULT 0,
  `randomx` INT(9) NOT NULL DEFAULT 0,
  `randomy` INT(9) NOT NULL DEFAULT 0,
  `heading` INT(9) NOT NULL DEFAULT 0,
  `respawn_delay` INT(9) NOT NULL DEFAULT 0,
  `loc_id` INT(9) NOT NULL DEFAULT 0,
  `periodOfDay` DECIMAL(2,0) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `key_npc_templateid` (`npc_templateid`)
) DEFAULT CHARSET=utf8;
