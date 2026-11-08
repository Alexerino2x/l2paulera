DROP TABLE IF EXISTS `auction_lots`;
CREATE TABLE `auction_lots` (
  `lotId` INT(10) NOT NULL AUTO_INCREMENT,
  `ownerId` INT(10) NOT NULL,
  `itemId` INT(6) NOT NULL,
  `objectId` INT(10) NOT NULL,
  `count` BIGINT(20) NOT NULL,
  `enchantLevel` INT(6) NOT NULL,
  `currency` INT(11) NOT NULL,
  `startingBid` BIGINT(20) NOT NULL,
  `bidIncrement` BIGINT(20) NOT NULL,
  `buyNow` BIGINT(20) NOT NULL,
  `endDate` BIGINT(20) NOT NULL,
  `processed` VARCHAR(5) NOT NULL DEFAULT 'false',
  PRIMARY KEY (`lotId`),
  KEY `lotId` (`ownerId`)
) AUTO_INCREMENT=3816 DEFAULT CHARSET=utf8;
