CREATE TABLE IF NOT EXISTS `custom_etcitem` (
  `item_id` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
  `name` VARCHAR(100) NOT NULL DEFAULT '',
  `crystallizable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `item_type` VARCHAR(14) NOT NULL DEFAULT 'none',
  `weight` MEDIUMINT(5) NOT NULL DEFAULT 0,
  `consume_type` VARCHAR(9) NOT NULL DEFAULT 'normal',
  `material` VARCHAR(15) NOT NULL DEFAULT 'wood',
  `crystal_type` VARCHAR(4) NOT NULL DEFAULT 'none',
  `duration` SMALLINT(3) NOT NULL DEFAULT -1,           -- duration in minutes for shadow items
  `time` MEDIUMINT(6) NOT NULL DEFAULT -1,              -- duration in minutes for time limited items
  `price` INT UNSIGNED NOT NULL DEFAULT 0,
  `crystal_count` SMALLINT(4) UNSIGNED NOT NULL DEFAULT 0,
  `sellable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `dropable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `destroyable` VARCHAR(5) NOT NULL DEFAULT 'true',
  `tradeable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `depositable` VARCHAR(5) NOT NULL default 'false',
  `handler` VARCHAR(70) NOT NULL DEFAULT 'none',
  `skills_item` VARCHAR(70) NOT NULL DEFAULT '',
  PRIMARY KEY (`item_id`)
) DEFAULT CHARSET=utf8;

ALTER TABLE `custom_etcitem`
  ADD COLUMN `item_display_id` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0 AFTER `item_id`;
