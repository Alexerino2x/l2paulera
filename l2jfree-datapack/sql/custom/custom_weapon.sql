CREATE TABLE IF NOT EXISTS `custom_weapon` (
  `item_id` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
  `name` VARCHAR(120) NOT NULL DEFAULT '',
  `bodypart` VARCHAR(15) NOT NULL DEFAULT '',
  `crystallizable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `weight` MEDIUMINT(5) NOT NULL DEFAULT 0,
  `soulshots` TINYINT(2) NOT NULL DEFAULT 0,
  `spiritshots` TINYINT(2) NOT NULL DEFAULT 0,
  `material` VARCHAR(15) NOT NULL DEFAULT 'wood',
  `crystal_type` VARCHAR(4) NOT NULL DEFAULT 'none',
  `p_dam` SMALLINT(3) NOT NULL DEFAULT 0,
  `rnd_dam` TINYINT(2) NOT NULL DEFAULT 0,
  `weaponType` VARCHAR(10) NOT NULL DEFAULT '',
  `critical` TINYINT(2) NOT NULL DEFAULT 0,
  `hit_modify` TINYINT(1) NOT NULL DEFAULT 0,
  `avoid_modify` TINYINT(1) NOT NULL DEFAULT 0,
  `shield_def` SMALLINT(3) NOT NULL DEFAULT 0,
  `shield_def_rate` TINYINT(2) NOT NULL DEFAULT 0,
  `atk_speed` SMALLINT(3) NOT NULL DEFAULT 0,
  `mp_consume` TINYINT(2) NOT NULL DEFAULT 0,
  `m_dam` SMALLINT(3) NOT NULL DEFAULT 0,
  `duration` SMALLINT(3) NOT NULL DEFAULT -1,           -- duration in minutes for shadow items
  `time` MEDIUMINT(6) NOT NULL DEFAULT -1,              -- duration in minutes for time limited items
  `price` INT UNSIGNED NOT NULL DEFAULT 0,
  `crystal_count` SMALLINT(4) UNSIGNED NOT NULL DEFAULT 0,
  `sellable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `dropable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `destroyable` VARCHAR(5) NOT NULL DEFAULT 'true',
  `tradeable` VARCHAR(5) NOT NULL DEFAULT 'false',
  `depositable` VARCHAR(5) NOT NULL default 'false',
  `enchant4_skill` VARCHAR(70) NOT NULL DEFAULT '',
  `skills_onCast` VARCHAR(70) NOT NULL DEFAULT '',
  `skills_onCrit` VARCHAR(70) NOT NULL DEFAULT '',
  `change_weaponId` MEDIUMINT(5) NOT NULL DEFAULT 0,
  `skills_item` VARCHAR(70) NOT NULL DEFAULT '',
  PRIMARY KEY (`item_id`)
) DEFAULT CHARSET=utf8;

ALTER TABLE `custom_weapon`
  ADD COLUMN `item_display_id` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0 AFTER `item_id`;
