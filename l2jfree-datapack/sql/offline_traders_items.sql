-- Table to keep items of offline traders
-- author hex1r0
CREATE TABLE IF NOT EXISTS `offline_traders_items` (
  `char_id` INT NOT NULL ,
  `item_obj_id` INT NOT NULL ,
  `count` BIGINT NOT NULL ,
  `price` BIGINT NOT NULL
)DEFAULT CHARSET=utf8;