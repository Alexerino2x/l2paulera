CREATE TABLE IF NOT EXISTS `petitions` (
  `petition_id` INT(11) NOT NULL auto_increment,
  `charId` INT(11) NOT NULL DEFAULT 0,
  `petition_txt` text NOT NULL,
  `status` VARCHAR(255) NOT NULL DEFAULT 'New',
  PRIMARY KEY (`petition_id`)
) DEFAULT CHARSET=utf8;