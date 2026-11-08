CREATE TABLE IF NOT EXISTS `castle_functions` (
  `castle_id` TINYINT(1) UNSIGNED NOT NULL,
  `type` TINYINT(1) NOT NULL,
  `lvl` SMALLINT UNSIGNED NOT NULL,
  `lease` INT NOT NULL,
  `rate` BIGINT NOT NULL,
  `endTime` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`castle_id`,`type`)
) DEFAULT CHARSET=utf8;