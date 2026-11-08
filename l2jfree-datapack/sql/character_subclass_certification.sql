CREATE TABLE IF NOT EXISTS `character_subclass_certification` (
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `class_index` int(1) NOT NULL DEFAULT 0,
  `certif_level` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`,`class_index`)
) DEFAULT CHARSET=utf8;
