CREATE TABLE IF NOT EXISTS `character_hennas` (
  `charId` INT UNSIGNED NOT NULL,
  `symbol_id` TINYINT UNSIGNED NOT NULL,
  `slot` TINYINT UNSIGNED NOT NULL,
  `class_index` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId`,`slot`,`class_index`)
) DEFAULT CHARSET=utf8;
