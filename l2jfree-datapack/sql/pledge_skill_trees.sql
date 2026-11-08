-- ===============
-- L2J-Free addons
-- ===============

DROP TABLE IF EXISTS `pledge_skill_trees`;
CREATE TABLE `pledge_skill_trees` (
  `skill_id` MEDIUMINT UNSIGNED NOT NULL,
  `level` SMALLINT(3) UNSIGNED NOT NULL,
  `name` VARCHAR(25) NOT NULL DEFAULT 'Clan Skill',
  `clan_lvl` TINYINT(2) UNSIGNED NOT NULL,
  `description` VARCHAR(255) NOT NULL DEFAULT '',
  `repCost` INT UNSIGNED NOT NULL,
  `itemId` MEDIUMINT UNSIGNED NOT NULL,
  `itemCount` BIGINT UNSIGNED NOT NULL,
  PRIMARY KEY (`skill_id`,`level`)
) DEFAULT CHARSET=utf8;

SET @CRADLE_OF_CREATION = 8175;
SET @DESTRUCTION_TOMBSTONE = 8176;
SET @MEMENTO_MORI = 9814;
SET @DRAGON_HEART = 9815;
SET @EARTH_EGG = 9816;
SET @NONLIVING_NUCLEUS = 9817;
SET @ANGELIC_ESSENCE = 9818;

INSERT INTO `pledge_skill_trees` VALUES
-- Level 5
(370, 1, "Clan Body", 5, "A clan member's Max HP increases by 3%. Effect applies after member level.", 1500, @EARTH_EGG, 10),
(373, 1, "Clan Lifeblood", 5, "A clan member's HP recovery bonus increases by 3%. Effect applies after member level.", 1500, @EARTH_EGG, 10),
(379, 1, "Clan Magic Protection", 5, "A clan member's M. Def. increases by 6%. Effect applies after member level.", 1500, @ANGELIC_ESSENCE, 10),
(391, 1, "Clan Imperium", 5, "Grants the privilege of Command Channel formation. It only effects Sage / Elder class and above.", 0, @DESTRUCTION_TOMBSTONE, 1),
-- Level 6
(371, 1, "Clan Spirituality", 6, "A clan member's Max CP increases by 6%. Effect applies after Baron level.", 2100, @NONLIVING_NUCLEUS, 10),
(374, 1, "Clan Morale", 6, "A clan member's CP recovery bonus increases by 6%. Effect applies after Elder level.", 2600, @NONLIVING_NUCLEUS, 10),
(376, 1, "Clan Might", 6, "A clan member's P. Atk. increases by 3%. Effect applies after Knight level.", 3000, @DRAGON_HEART, 10),
(383, 1, "Clan Shield Boost", 6, "A clan member's shield P. Def. increases by 24%. Effect applies after Baron level.", 2100, @DRAGON_HEART, 10),
(377, 1, "Clan Aegis", 6, "A clan member's P. Def. increases by 3%. Effect applies after Knight level.", 3000, @EARTH_EGG, 10),
-- Level 7
(370, 2, "Clan Body", 7, "A clan member's Max HP increases by 5%. Effect applies after member level.", 6900, @EARTH_EGG, 10),
(371, 2, "Clan Spirituality", 7, "A clan member's Max CP increases by 10%. Effect applies after Baron level.", 5600, @NONLIVING_NUCLEUS, 10),
(373, 2, "Clan Lifeblood", 7, "A clan member's HP recovery bonus increases by 5%. Effect applies after member level.", 6900, @EARTH_EGG, 10),
(376, 2, "Clan Might", 7, "A clan member's P. Atk. increases by 5%. Effect applies after Knight level.", 6500, @DRAGON_HEART, 10),
(377, 2, "Clan Aegis", 7, "A clan member's P. Def. increases by 5%. Effect applies after Knight level.", 6500, @EARTH_EGG, 10),
(379, 2, "Clan Magic Protection", 7, "A clan member's M. Def. increases by 10%. Effect applies after member level.", 6900, @ANGELIC_ESSENCE, 10),
(380, 1, "Clan Guidance", 7, "A clan member's accuracy increases by 1. Effect applies after Baron level.", 5600, @MEMENTO_MORI, 10),
(382, 1, "Clan Withstand-Attack", 7, "A clan member's shield defense rate increases by 12%. Effect applies after Viscount level.", 5100, @MEMENTO_MORI, 10),
(384, 1, "Clan Cyclonic Resistance", 7, "A clan member's resistance to water and wind attacks increases by 3. Effect applies after Viscount level.", 5100, @DESTRUCTION_TOMBSTONE, 1),
(385, 1, "Clan Magmatic Resistance", 7, "A clan member's resistance to fire and earth attacks increases by 3. Effect applies after Viscount level.", 5100, @DESTRUCTION_TOMBSTONE, 1),
(386, 1, "Clan Fortitude", 7, "A clan member's resistance to stun attacks increases by 12. Effect applies after Viscount level.", 5100, @DESTRUCTION_TOMBSTONE, 1),
(387, 1, "Clan Freedom", 7, "A clan member's resistance to hold attacks increases by 12. Effect applies after Viscount level.", 5100, @DESTRUCTION_TOMBSTONE, 1),
(388, 1, "Clan Vigilance", 7, "A clan member's resistance to sleep attacks increases by 12. Effect applies after Viscount level.", 5100, @DESTRUCTION_TOMBSTONE, 1),
(390, 1, "Clan Luck", 7, "When killed by PK/ordinary monster, decreases a clan member's Exp. points consumption rate and the probability of incurring a death after-effect. Effect applies after main clan family descendant level.", 6900, @CRADLE_OF_CREATION, 1),
-- Level 8
(371, 3, "Clan Spirituality", 8, "A clan member's Max CP increases by 12%. Effect applies after Baron level.", 12000, @NONLIVING_NUCLEUS, 10),
(372, 1, "Clan Essence", 8, "A clan member's Max MP increases by 3%. Effect applies after Viscount level.", 11700, @ANGELIC_ESSENCE, 10),
(374, 2, "Clan Morale", 8, "A clan member's CP recovery bonus increases by 10%. Effect applies after Elder level.", 13000, @NONLIVING_NUCLEUS, 10),
(375, 1, "Clan Clarity", 8, "A clan member's MP recovery bonus increases by 3%. Effect applies after Viscount level.", 11700, @ANGELIC_ESSENCE, 10),
(376, 3, "Clan Might", 8, "A clan member's P. Atk. increases by 6%. Effect applies after Knight level.", 13000, @DRAGON_HEART, 10),
(377, 3, "Clan Aegis", 8, "A clan member's P. Def. increases by 6%. Effect applies after Knight level.", 13000, @EARTH_EGG, 10),
(378, 1, "Clan Empowerment", 8, "A clan member's M. Atk. increases by 6%. Effect applies after Viscount level.", 11700, @ANGELIC_ESSENCE, 10),
(380, 2, "Clan Guidance", 8, "A clan member's accuracy increases by 2. Effect applies after Baron level.", 12000, @MEMENTO_MORI, 10),
(381, 1, "Clan Agility", 8, "A clan member's evasion increases by 1. Effect applies after Baron level.", 12000, @MEMENTO_MORI, 10),
(382, 2, "Clan Withstand-Attack", 8, "A clan member's shield defense rate increases by 20%. Effect applies after Viscount level.", 12000, @MEMENTO_MORI, 10),
(383, 2, "Clan Shield Boost", 8, "A clan member's shield P. Def. increases by 40%. Effect applies after Baron level.", 12000, @DRAGON_HEART, 10),
(384, 2, "Clan Cyclonic Resistance", 8, "A clan member's resistance to water and wind attacks increases by 5. Effect applies after Viscount level.", 12000, @DESTRUCTION_TOMBSTONE, 1),
(385, 2, "Clan Magmatic Resistance", 8, "A clan member's resistance to fire and earth attacks increases by 5. Effect applies after Viscount level.", 12000, @DESTRUCTION_TOMBSTONE, 1),
(386, 2, "Clan Fortitude", 8, "A clan member's resistance to stun attacks increases by 20. Effect applies after Viscount level.", 12000, @DESTRUCTION_TOMBSTONE, 1),
(387, 2, "Clan Freedom", 8, "A clan member's resistance to hold attacks increases by 20. Effect applies after Viscount level.", 12000, @DESTRUCTION_TOMBSTONE, 1),
(388, 2, "Clan Vigilance", 8, "A clan member's resistance to sleep attacks increases by 20. Effect applies after Viscount level.", 12000, @DESTRUCTION_TOMBSTONE, 1),
(389, 1, "Clan March", 8, "A clan member's movement speed increases by 3. Effect applies after Count level.", 11400, @CRADLE_OF_CREATION, 1),
(390, 2, "Clan Luck", 8, "When killed by PK/ordinary monster, decreases a clan member's Exp. points consumption rate and the probability of incurring a death after-effect. Effect applies after main clan family descendant level.", 14000, @CRADLE_OF_CREATION, 1),
-- Level 9 (missing)
-- Level 10 (missing)
-- Level 11
(370, 3, "Clan Body", 11, "A clan member's Max HP increases by 6%. Effect applies after member level.", 13200, @EARTH_EGG, 10),
(373, 3, "Clan Lifeblood", 11, "A clan member's HP recovery bonus increases by 6%. Effect applies after member level.", 13200, @EARTH_EGG, 10),
(379, 3, "Clan Magic Protection", 11, "A clan member's M. Def. increases by 12%. Effect applies after member level.", 13200, @ANGELIC_ESSENCE, 10);
