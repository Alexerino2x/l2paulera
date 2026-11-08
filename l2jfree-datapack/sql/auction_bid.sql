CREATE TABLE IF NOT EXISTS `auction_bid` (
  `id` INT NOT NULL DEFAULT 0,
  `auctionId` INT NOT NULL DEFAULT 0,
  `bidderId` INT NOT NULL DEFAULT 0,
  `bidderName` VARCHAR(35) NOT NULL,
  `clan_name` VARCHAR(45) NOT NULL,
  `maxBid` INT UNSIGNED NOT NULL DEFAULT 0,
  `time_bid` BIGINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`auctionId`, `bidderId`),
  KEY `id` (`id`)
) DEFAULT CHARSET=utf8;
