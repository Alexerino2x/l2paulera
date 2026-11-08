INSERT IGNORE INTO `character_birthdays` (`charId`, `lastClaim`, `birthDate`)
SELECT `charId`, 0, 0 FROM `characters`;
UPDATE `character_birthdays` SET `lastClaim` = YEAR(CURDATE()), `birthDate` = CURDATE() WHERE `lastClaim` = 0;