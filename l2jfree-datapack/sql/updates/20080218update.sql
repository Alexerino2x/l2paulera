-- Adding race/class/sex restrictions
ALTER TABLE `armor` ADD `races` VARCHAR(20) NOT NULL DEFAULT '-1';
ALTER TABLE `armor` ADD `classes` VARCHAR(255) NOT NULL DEFAULT '-1';
ALTER TABLE `armor` ADD `sex` INT (1) NOT NULL DEFAULT -1;
ALTER TABLE `custom_armor` ADD `races` VARCHAR(20) NOT NULL DEFAULT '-1';
ALTER TABLE `custom_armor` ADD `classes` VARCHAR(255) NOT NULL DEFAULT '-1';
ALTER TABLE `custom_armor` ADD `sex` INT (1) NOT NULL DEFAULT -1;
ALTER TABLE `weapon` ADD `races` VARCHAR(20) NOT NULL DEFAULT '-1';
ALTER TABLE `weapon` ADD `classes` VARCHAR(255) NOT NULL DEFAULT '-1';
ALTER TABLE `weapon` ADD `sex` INT (1) NOT NULL DEFAULT -1;
ALTER TABLE `custom_weapon` ADD `races` VARCHAR(20) NOT NULL DEFAULT '-1';
ALTER TABLE `custom_weapon` ADD `classes` VARCHAR(255) NOT NULL DEFAULT '-1';
ALTER TABLE `custom_weapon` ADD `sex` INT (1) NOT NULL DEFAULT -1;

-- Multiple skills per item
ALTER TABLE `armor`
CHANGE `item_skill_id` `item_skill_id` VARCHAR(60) NOT NULL DEFAULT '0',
CHANGE `item_skill_lvl` `item_skill_lvl` VARCHAR(30) NOT NULL DEFAULT '0';
ALTER TABLE `custom_armor`
CHANGE `item_skill_id` `item_skill_id` VARCHAR(60) NOT NULL DEFAULT '0',
CHANGE `item_skill_lvl` `item_skill_lvl` VARCHAR(30) NOT NULL DEFAULT '0';
ALTER TABLE `weapon`
CHANGE `item_skill_id` `item_skill_id` VARCHAR(60) NOT NULL DEFAULT '0',
CHANGE `item_skill_lvl` `item_skill_lvl` VARCHAR(30) NOT NULL DEFAULT '0';
ALTER TABLE `custom_weapon`
CHANGE `item_skill_id` `item_skill_id` VARCHAR(60) NOT NULL DEFAULT '0',
CHANGE `item_skill_lvl` `item_skill_lvl` VARCHAR(30) NOT NULL DEFAULT '0';
-- Armor dual-skill fix example
UPDATE `armor` SET `item_skill_id` = '3632,3633', `item_skill_lvl` = '1,2' WHERE `item_id` = 6841;
-- Wedding System part
UPDATE `weapon` SET `item_skill_id` = '3260,3261,3262', `item_skill_lvl` = '1,1,1' WHERE `item_id` IN (9140,9141);