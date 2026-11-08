CREATE TABLE IF NOT EXISTS `obj_restrictions` (
  `entry_id` INT(10) unsigned NOT NULL auto_increment,
  `obj_Id` INT(11) unsigned NOT NULL DEFAULT 0,
  `type` VARCHAR(50) NOT NULL DEFAULT '',
  `delay` INT(11) NOT NULL DEFAULT -1,
  `message` VARCHAR(250) DEFAULT NULL,
  PRIMARY KEY (`entry_id`)
) DEFAULT CHARSET=utf8;
