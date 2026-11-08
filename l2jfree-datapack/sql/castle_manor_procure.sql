DROP TABLE IF EXISTS `castle_manor_procure`;
CREATE TABLE `castle_manor_procure` (
  `castle_id` TINYINT(1) UNSIGNED NOT NULL,
  `crop_id` int(11) NOT NULL DEFAULT 0,
  `can_buy` int(11) NOT NULL DEFAULT 0,
  `start_buy` int(11) NOT NULL DEFAULT 0,
  `price` int(11) NOT NULL DEFAULT 0,
  `reward_type` int(11) NOT NULL DEFAULT 0,
  `period` INT NOT NULL DEFAULT 1,
  PRIMARY KEY  (`castle_id`,`crop_id`,`period`)
) DEFAULT CHARSET=utf8;
