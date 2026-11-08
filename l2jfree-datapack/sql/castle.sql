CREATE TABLE IF NOT EXISTS `castle` (
  `id` INT NOT NULL DEFAULT 0,
  `name` VARCHAR(10) NOT NULL,
  `taxPercent` TINYINT(2) UNSIGNED NOT NULL DEFAULT 0,
  `newTax` TINYINT(2) UNSIGNED NOT NULL DEFAULT 0,
  `taxSetDate` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `treasury` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `siegeDate` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `regTimeOver` ENUM('true','false') DEFAULT 'true' NOT NULL,
  `regTimeEnd` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`name`),
  KEY `id` (`id`)
) DEFAULT CHARSET=utf8;

INSERT IGNORE INTO `castle` (`id`, `name`) VALUES 
(1,'Gludio'),
(2,'Dion'),
(3,'Giran'),
(4,'Oren'),
(5,'Aden'),
(6,'Innadril'),
(7,'Goddard'),
(8,'Rune'),
(9,'Schuttgart');