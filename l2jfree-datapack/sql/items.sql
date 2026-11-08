CREATE TABLE IF NOT EXISTS `items` (
  `owner_id` INT,                              -- object id of the player or clan,owner of this item
  `object_id` INT NOT NULL DEFAULT 0,          -- object id of the item
  `item_id` INT,                               -- item id
  `count` BIGINT UNSIGNED NOT NULL default 0,
  `enchant_level` INT,
  `loc` VARCHAR(10),                           -- inventory,paperdoll,npc,clan warehouse,pet,and so on
  `loc_data` INT,                              -- depending on location: equiped slot,npc id,pet id,etc
  `custom_type1` INT DEFAULT 0,
  `custom_type2` INT DEFAULT 0,
  `mana_left` SMALLINT NOT NULL DEFAULT -1,    -- duration for shadow items
  `time` DECIMAL(13) NOT NULL DEFAULT 0,       -- duration for time limited items
  PRIMARY KEY (`object_id`),
  KEY `key_owner_id` (`owner_id`),
  KEY `key_loc` (`loc`),
  KEY `key_item_id` (`item_id`)
) DEFAULT CHARSET=utf8;