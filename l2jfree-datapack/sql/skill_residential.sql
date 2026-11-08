DROP TABLE IF EXISTS `skill_residential`;
CREATE TABLE `skill_residential` (
  `entityId` INT(11) NOT NULL,
  `skillId` INT(11) NOT NULL DEFAULT 0,
  `skillLevel` INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`entityId`,`skillId`)
) DEFAULT CHARSET=utf8;

INSERT INTO `skill_residential` VALUES
(1,593,1), -- Gludio Castle Residence Health
(1,600,1), -- Gludio Castle Residence Guidance
(1,606,1), -- Gludio Castle Residence Fortitude
(2,591,1), -- Dion Castle Residence Spirit
(2,597,1), -- Dion Castle Residence Shield
(2,594,1),
(3,609,1),
(3,601,1), -- Giran Castle Residence Agility
(3,607,1),
(4,598,1), -- Oren Castle Residence Empower
(4,605,1), -- Oren Castle Residence Resist Lava
(5,596,1), -- Aden Castle Residence Might
(5,608,1), -- Aden Castle Residence Vigilance
(6,595,1), -- Inadril Castle Residence Clarity
(6,597,1),
(6,609,1),
(7,594,1), -- Goddard Castle Residence Moral
(7,598,1),
(7,603,1), -- Goddard Castle Residence Shield Defense
(8,593,1), -- Rune Castle Residence Health
(8,599,1), -- Rune Castle Residence Magic Barrier
(8,604,1), -- Rune Castle Residence Resist Typhoon
(9,592,1), -- Schuttgart Castle Residence Soul
(9,600,1), -- Schuttgart Castle Residence Guidance
(9,610,1), -- Schuttgart Castle Residence Death Fortune
(101,590,1), -- Shanty Fort Residence Body
(101,602,1), -- Shanty Fort Residence Shield Block
(102,602,1), -- Southern Fort Residence Shield Block
(102,604,1), -- Southern Fort Residence Resist Typhoon
(103,601,1), -- Hive Fort Residence Agility
(103,605,1), -- Hive Fort Residence Resist Lava
(104,595,1), -- Valley Fort Residence Clarity
(104,606,1), -- Valley Fort Residence Fortitude
(105,594,1), -- Ivory Fort Residence Moral
(105,607,1), -- Ivory Fort Residence Freedom
(106,593,1), -- Narsell Fort Residence Health
(106,608,1), -- Narsell Fort Residence Vigilance
(107,596,1), -- Bayou Fort Residence Might
(107,605,1),
(108,592,1), -- White Sands Fort Residence Soul
(108,599,1), -- White Sands Fort Residence Magic Barrier
(109,591,1), -- Borderland Fort Residence Spirit
(109,610,1), -- Borderland Fort Residence Death Fortune
-- missing Marshland Fortress
(111,590,1), -- Archaic Fort Residence Body
(111,608,1), -- Archaic Fort Residence Vigilance
(112,590,1),
(112,608,1),
(113,604,1),
(113,610,1), -- Cloud Mountain Fort Residence Death Fortune
(114,605,1), -- Tanor Fort Residence Resist Lava
(114,609,1), -- Tanor Fort Residence Movement
(115,599,1), -- Dragonspine Fort Residence Magic Barrier
(115,604,1), -- Dragonspine Fort Residence Resist Typhoon
(116,598,1), -- Antharas Fort Residence Empower
(116,603,1), -- Antharas Fort Residence Shield Defense
(117,597,1), -- Western Fort Residence Shield
(117,602,1), -- Western Fort Residence Shield Block
(117,610,1), -- Western Fort Residence Death Fortune
(118,596,1), -- Hunters Fort Residence Might
(118,601,1), -- Hunters Fort Residence Agility
(119,592,1), -- Aaru Fort Residence Soul
(119,595,1), -- Aaru Fort Residence Clarity
(120,591,1), -- Demon Fort Residence Spirit
(120,594,1), -- Demon Fort Residence Moral
(121,590,1), -- Monastic Fort Residence Body
(121,593,1); -- Monastic Fort Residence Health

--
-- L2J-Free Add-ons
--
DELETE FROM `skill_residential` WHERE `entityId` = 2 AND `skillId` = 594;
DELETE FROM `skill_residential` WHERE `entityId` = 3 AND `skillId` = 609;
DELETE FROM `skill_residential` WHERE `entityId` = 3 AND `skillId` = 607;
DELETE FROM `skill_residential` WHERE `entityId` = 6 AND `skillId` = 597;
DELETE FROM `skill_residential` WHERE `entityId` = 6 AND `skillId` = 609;
DELETE FROM `skill_residential` WHERE `entityId` = 7 AND `skillId` = 598;

DELETE FROM `skill_residential` WHERE `entityId` = 107 AND `skillId` = 605;
DELETE FROM `skill_residential` WHERE `entityId` = 112 AND `skillId` = 590;
DELETE FROM `skill_residential` WHERE `entityId` = 112 AND `skillId` = 608;
DELETE FROM `skill_residential` WHERE `entityId` = 113 AND `skillId` = 604;

INSERT INTO `skill_residential` VALUES
-- Castle
(2,609,1), -- Dion Castle Residence Movement
(3,592,1), -- Giran Castle Residence Soul
(3,610,1), -- Giran Castle Residence Death Fortune
(4,590,1), -- Oren Castle Residence Body
(5,602,1), -- Aden Castle Residence Shield Block
(6,599,1), -- Inadril Castle Residence Magic Barrier
(6,607,1), -- Inadril Castle Residence Freedom
(7,590,1), -- Goddard Castle Residence Body
-- Fort
(107,598,1), -- Bayou Fort Residence Empower
(110,597,1), -- Marshland/Swamp Fort Residence Shield
(110,600,1), -- Marshland/Swamp Fort Residence Guidance
(112,601,1), -- Floran Fort Residence Agility
(112,607,1), -- Floran Fort Residence Freedom
(113,606,1); -- Cloud Mountain Fort Residence Fortitude

