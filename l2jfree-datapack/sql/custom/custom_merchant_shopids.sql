CREATE TABLE IF NOT EXISTS `custom_merchant_shopids` (
  `shop_id` DECIMAL(9,0) NOT NULL DEFAULT 0,
  `npc_id` VARCHAR(9) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
) DEFAULT CHARSET=utf8;
