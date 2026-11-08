ALTER TABLE `custom_etcitem` 
DROP `html`,
CHANGE `skill` `skills_item` VARCHAR(70) NOT NULL DEFAULT '',
ADD `handler` VARCHAR(70) NOT NULL DEFAULT 'none' AFTER `tradeable`;