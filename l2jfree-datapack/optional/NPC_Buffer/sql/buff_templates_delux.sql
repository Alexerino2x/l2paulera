-- --------------------------------------------------------
--
-- SQL Buff Template for NPC Buffer (by G1ta0) for L2j-Free
--
-- --------------------------------------------------------

SET @tmpl_name = 'DeluxeBuffs';

DELETE FROM `buff_templates` WHERE `name` LIKE 'DeluxeBuffs';

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
-- Common Buffs - Wind Walk
--
-- --------------------------------------------------------
SET @skill_id = 1204;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Decrease Weight
--
-- --------------------------------------------------------
SET @skill_id = 1257;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Kiss of Eva
--
-- --------------------------------------------------------
SET @skill_id = 1073;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Shield
--
-- --------------------------------------------------------
SET @skill_id = 1040;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Might
--
-- --------------------------------------------------------
SET @skill_id = 1068;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Haste
--
-- --------------------------------------------------------
SET @skill_id = 1086;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Focus
--
-- --------------------------------------------------------
SET @skill_id = 1077;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Death Whisper
--
-- --------------------------------------------------------
SET @skill_id = 1242;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Bless the Body
--
-- --------------------------------------------------------
SET @skill_id = 1045;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Common Buffs - Regeneration
--
-- --------------------------------------------------------
SET @skill_id = 1044;

--
-- Price: 20.000 Adena
--

SET @price = 20000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- Buffs for class (0 - common, 1 - fighter, 2 - mage)
SET @class = 1;

-- --------------------------------------------------------
--
-- Fighter Buffs - Guidance
--
-- --------------------------------------------------------
SET @skill_id = 1240;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Fighter Buffs - Agility
--
-- --------------------------------------------------------
SET @skill_id = 1087;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Fighter Buffs - Soul Shield
--
-- --------------------------------------------------------
SET @skill_id = 1010;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Fighter Buffs - Magic Barrier
--
-- --------------------------------------------------------
SET @skill_id = 1036;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Fighter Buffs - Bless Shield
--
-- --------------------------------------------------------
SET @skill_id = 1243;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Fighter Buffs - Vampiric Rage
--
-- --------------------------------------------------------
SET @skill_id = 1268;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- Buffs for class (0 - common, 1 - fighter, 2 - mage)
SET @class = 2;

-- --------------------------------------------------------
--
-- Mage Buffs - Wild Magic
--
-- --------------------------------------------------------
SET @skill_id = 1303;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Mage Buffs - Bless the Soul
--
-- --------------------------------------------------------
SET @skill_id = 1048;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Mage Buffs - Concentration
--
-- --------------------------------------------------------
SET @skill_id = 1078;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Mage Buffs - Acumen
--
-- --------------------------------------------------------
SET @skill_id = 1085;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);

-- --------------------------------------------------------
--
-- Mage Buffs - Empower
--
-- --------------------------------------------------------
SET @skill_id = 1059;

--
-- Price: 40.000 Adena
--

SET @price = 40000;

SET @get_skill_name = (SELECT name FROM `skill_trees` WHERE skill_id=@skill_id LIMIT 1);
SET @get_skill_level = (SELECT MAX(`level`) FROM `skill_trees` WHERE skill_id=@skill_id);
SET @order = @order + 1;

INSERT INTO `buff_templates` (`id`, `name`,`skill_id`, `skill_name`,`skill_level`, `skill_force`, `skill_order`, `char_min_level`, `char_max_level`, `char_race`, `char_class`,  `price_adena`) 
VALUES 
(@tmpl_id,@tmpl_name,@skill_id , @get_skill_name ,@get_skill_level, 1, @order,@min_level,@max_level,0,@class,@price);
