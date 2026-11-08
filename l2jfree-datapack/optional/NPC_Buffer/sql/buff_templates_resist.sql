-- --------------------------------------------------------
--
-- SQL Buff Template for NPC Buffer (by G1ta0) for L2j-Free
--
-- --------------------------------------------------------

SET @tmpl_name = 'ResistBuffs';

DELETE FROM `buff_templates` WHERE `name` LIKE 'ResistBuffs';

-- Minimum level of player (0 - any)
SET @min_level = 40;

-- Maximum level of player (0 - any)
SET @max_level = 80;

SET @tmpl_id = (SELECT MAX(`id`) FROM `buff_templates`) + 1;

SET @order = 0;

-- --------------------------------------------------------

-- Buffs for class (0 - common, 1 - fighter, 2 - mage)
SET @class = 0;

-- --------------------------------------------------------
--
-- Common Buffs - Resist Poison
--
-- --------------------------------------------------------
SET @skill_id = 1033;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Invigor
--
-- --------------------------------------------------------
SET @skill_id = 1032;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Elemental Protection
--
-- --------------------------------------------------------
SET @skill_id = 1352;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Holy Resistance
--
-- --------------------------------------------------------
SET @skill_id = 1392;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Unholy Resistance
--
-- --------------------------------------------------------
SET @skill_id = 1393;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Mental Shield
--
-- --------------------------------------------------------
SET @skill_id = 1035;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Resist Shock
--
-- --------------------------------------------------------
SET @skill_id = 1259;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Arcane Protection
--
-- --------------------------------------------------------
SET @skill_id = 1354;

--
-- Price: 30.000 Adena
--

SET @price = 30000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);