-- ---------------------------
-- Table structure for `clanhall_sieges`
-- ---------------------------
CREATE TABLE IF NOT EXISTS `clanhall_sieges` (
  `hallId` TINYINT UNSIGNED NOT NULL,
  `siegeDate` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `regTimeEnd` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `regTimeOver` ENUM('true','false') NOT NULL DEFAULT 'true',
  PRIMARY KEY `hallId` (`hallId`)
) DEFAULT CHARSET=utf8;

INSERT IGNORE INTO `clanhall_sieges` (`hallId`) VALUES
-- Originally for all contests
-- (21), Doesn't fit in this category
(34), -- Devastated Castle
-- (35), Too different
-- (62), Too different
-- (63), Too different
(64); -- Fortress of the Dead
