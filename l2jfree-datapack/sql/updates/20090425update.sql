ALTER TABLE `accounts`
CHANGE `login` `login` VARCHAR(45) NOT NULL,
CHANGE `password` `password` VARCHAR(45) NOT NULL,
CHANGE `lastactive` `lastactive` BIGINT UNSIGNED,
CHANGE `accessLevel` `accessLevel` SMALLINT(4) NOT NULL DEFAULT 0,
CHANGE `lastServerId` `lastServerId` TINYINT(2) UNSIGNED NOT NULL DEFAULT 0,
ADD `birthYear` SMALLINT(4) UNSIGNED NOT NULL DEFAULT 1900 AFTER `lastServerId`,
ADD `birthMonth` TINYINT(2) UNSIGNED NOT NULL DEFAULT 1 AFTER `birthYear`,
ADD `birthDay` TINYINT(2) UNSIGNED NOT NULL DEFAULT 1 AFTER `birthMonth`;