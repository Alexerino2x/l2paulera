CREATE TABLE IF NOT EXISTS `pets` (
  `item_obj_id` DECIMAL(11) NOT NULL DEFAULT 0,
  `name` VARCHAR(16),
  `level` DECIMAL(11),
  `curHp` DECIMAL(18,0),
  `curMp` DECIMAL(18,0),
  `exp` DECIMAL(20,0),
  `sp` DECIMAL(11),
  `fed` DECIMAL(11),
  `weapon` INT(5),
  `armor` INT(5),
  `jewel` INT(5),
  PRIMARY KEY (`item_obj_id`)
) DEFAULT CHARSET=utf8;