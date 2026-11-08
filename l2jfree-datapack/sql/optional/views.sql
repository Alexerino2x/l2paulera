-- This VIEWs could be very slow, so think before using.


DROP VIEW IF EXISTS `online_characters`;
CREATE VIEW `online_characters` AS
	SELECT
		`characters`.*
	FROM
		`characters`
	WHERE
		`characters`.`online` > 0
	ORDER BY
		`characters`.`account_name` ASC;


DROP VIEW IF EXISTS `online_characters_with_class_names`;
CREATE VIEW `online_characters_with_class_names` AS
	SELECT
		`class_list`.`class_name`,
		`characters`.`classid` AS `class_id`,
		`characters`.*
	FROM
		`characters`
	INNER JOIN
		`class_list` ON `characters`.`classid` = `class_list`.`id`
	WHERE
		`characters`.`online` > 0
	ORDER BY
		`characters`.`account_name` ASC;


DROP VIEW IF EXISTS `character_skills_with_char_names`;
CREATE VIEW `character_skills_with_char_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`character_skills`.*
	FROM
		`character_skills`
	INNER JOIN
		`characters` USING (`charId`)
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC,
		`character_skills`.`class_index` ASC,
		`character_skills`.`skill_id` ASC;


DROP VIEW IF EXISTS `character_effects_with_char_names`;
CREATE VIEW `character_effects_with_char_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`character_effects`.*
	FROM
		`character_effects`
	INNER JOIN
		`characters` USING (`charId`)
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC,
		`character_effects`.`classIndex` ASC,
		`character_effects`.`skillId` ASC;

DROP VIEW IF EXISTS `character_skill_reuses_with_char_names`;
CREATE VIEW `character_skill_reuses_with_char_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`character_skill_reuses`.*
	FROM
		`character_skill_reuses`
	INNER JOIN
		`characters` USING (`charId`)
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC,
		`character_skill_reuses`.`skillId` ASC;

DROP VIEW IF EXISTS `heroes_with_char_names`;
CREATE VIEW `heroes_with_char_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`heroes`.*
	FROM
		`heroes`
	INNER JOIN
		`characters` USING (`charId`)
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC;


DROP VIEW IF EXISTS `olympiad_nobles_with_char_names`;
CREATE VIEW `olympiad_nobles_with_char_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`olympiad_nobles`.*
	FROM
		`olympiad_nobles`
	INNER JOIN
		`characters` USING (`charId`)
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC;


DROP VIEW IF EXISTS `olympiad_nobles_eom_with_char_names`;
CREATE VIEW `olympiad_nobles_eom_with_char_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`olympiad_nobles_eom`.*
	FROM
		`olympiad_nobles_eom`
	INNER JOIN
		`characters` USING (`charId`)
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC;


--
-- You should choose one solution from the next two. TABLE based is faster, but must be updated regularly.
--
DROP VIEW IF EXISTS `item_names`;
CREATE VIEW `item_names` AS
		SELECT
			`armor`.`item_id` AS `item_id`,
			`armor`.`name` AS `item_name`,
			"armor" AS `item_type`
		FROM
			`armor`
	UNION
		SELECT
			`etcitem`.`item_id` AS `item_id`,
			`etcitem`.`name` AS `item_name`,
			"etcitem" AS `item_type`
		FROM
			`etcitem`
	UNION
		SELECT
			`weapon`.`item_id` AS `item_id`,
			`weapon`.`name` AS `item_name`,
			"weapon" AS `item_type`
		FROM
			`weapon`
	ORDER BY
		`item_id` ASC;
--
-- OR
--
/*
DROP TABLE IF EXISTS `item_names`;
CREATE TABLE `item_names` (
	`item_id` MEDIUMINT UNSIGNED NOT NULL,
	`item_name` VARCHAR(120) NOT NULL,
	`item_type` ENUM("armor", "etcitem", "weapon") NOT NULL,
	PRIMARY KEY (`item_id`)
) DEFAULT CHARSET = utf8;

INSERT INTO `item_names`
		SELECT
			`armor`.`item_id` AS `item_id`,
			`armor`.`name` AS `item_name`,
			"armor" AS `item_type`
		FROM
			`armor`
	UNION
		SELECT
			`etcitem`.`item_id` AS `item_id`,
			`etcitem`.`name` AS `item_name`,
			"etcitem" AS `item_type`
		FROM
			`etcitem`
	UNION
		SELECT
			`weapon`.`item_id` AS `item_id`,
			`weapon`.`name` AS `item_name`,
			"weapon" AS `item_type`
		FROM
			`weapon`;
*/
--
-- 
--


DROP VIEW IF EXISTS `items_with_char_and_item_names`;
CREATE VIEW `items_with_char_and_item_names` AS
	SELECT
		`characters`.`account_name`,
		`characters`.`char_name`,
		`characters`.`accesslevel`,
		`item_names`.`item_name`,
		`item_names`.`item_type`,
		`items`.*
	FROM
		`items`
	INNER JOIN
		`characters` ON `characters`.`charId` = `items`.`owner_id`
	INNER JOIN
		`item_names` ON item_names.`item_id` = `items`.`item_id`
	ORDER BY
		`characters`.`account_name` ASC,
		`characters`.`char_name` ASC,
		`items`.`item_id` ASC;
