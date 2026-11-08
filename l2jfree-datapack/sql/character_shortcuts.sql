CREATE TABLE IF NOT EXISTS `character_shortcuts` (
  `charId` INT UNSIGNED NOT NULL,
  `slot` TINYINT NOT NULL,
  `page` TINYINT NOT NULL,
  `type` TINYINT NOT NULL,
  `shortcut_id` INT NOT NULL,
  `level` SMALLINT(3) NOT NULL,
  `class_index` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId`,`slot`,`page`,`class_index`),
  KEY `shortcut_id` (`shortcut_id`)
) DEFAULT CHARSET=utf8;
