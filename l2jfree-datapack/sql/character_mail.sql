CREATE TABLE IF NOT EXISTS `character_mail` (
  `charId` INT NOT NULL,
  `letterId` INT NOT NULL AUTO_INCREMENT,
  `senderId` INT NOT NULL,
  `location` VARCHAR(45) NOT NULL,
  `recipientNames` VARCHAR(200) DEFAULT NULL,
  `subject` VARCHAR(12) DEFAULT NULL,
  `message` VARCHAR(3000) DEFAULT NULL,
  `sentDate` TIMESTAMP NULL DEFAULT NULL,
  `deleteDate` TIMESTAMP NULL DEFAULT NULL,
  `unread` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`letterId`),
  KEY `charId` (`charId`)
) DEFAULT CHARSET=utf8;
