CREATE TABLE IF NOT EXISTS `custom_armor` (
  `item_id` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
  `name` VARCHAR(120) NOT NULL DEFAULT '',
  `bodypart` VARCHAR(15) NOT NULL DEFAULT 'none',
  `crystallizable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `armor_type` VARCHAR(5) NOT NULL DEFAULT 'none',
  `weight` MEDIUMINT(5) NOT NULL DEFAULT 0,
  `material` VARCHAR(15) NOT NULL DEFAULT 'wood',
  `crystal_type` VARCHAR(4) NOT NULL DEFAULT 'none',
  `avoid_modify` TINYINT(1) NOT NULL DEFAULT 0,
  `duration` SMALLINT(3) NOT NULL DEFAULT -1,           -- duration in minutes for shadow items
  `time` MEDIUMINT(6) NOT NULL DEFAULT -1,              -- duration in minutes for time limited items
  `p_def` SMALLINT(3) NOT NULL DEFAULT 0,
  `m_def` TINYINT(2) NOT NULL DEFAULT 0,
  `mp_bonus` SMALLINT(3) NOT NULL DEFAULT 0,
  `price` INT UNSIGNED NOT NULL DEFAULT 0,
  `crystal_count` SMALLINT(4) UNSIGNED NOT NULL DEFAULT 0,
  `sellable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `dropable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `destroyable` VARCHAR(5) NOT NULL default 'false',
  `tradeable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `depositable` VARCHAR(5) NOT NULL default 'false',
  `enchant4_skill` VARCHAR(8) NOT NULL DEFAULT '',
  `skills_item` VARCHAR(70) NOT NULL DEFAULT '',
  PRIMARY KEY (`item_id`)
) DEFAULT CHARSET=utf8;

ALTER TABLE `custom_armor`
  ADD COLUMN `item_display_id` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0 AFTER `item_id`;
