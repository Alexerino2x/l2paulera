ALTER TABLE `character_friends`
  RENAME TO `character_friends2`;

CREATE TABLE `character_friends` (
  `charId1` INT UNSIGNED NOT NULL,
  `charId2` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`charId1`,`charId2`)
) DEFAULT CHARSET=utf8;

INSERT INTO `character_friends` (`charId1`,`charId2`)
  SELECT DISTINCT
    IF(`charId`<`friendId`,`charId`,`friendId`),
    IF(`charId`>`friendId`,`charId`,`friendId`)
  FROM `character_friends2`;
  
DROP TABLE `character_friends2`;