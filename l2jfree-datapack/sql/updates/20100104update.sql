-- Fix for Skill 627 Enchant Routes

UPDATE `character_skills` SET `skill_level` = (`skill_level` - 500) WHERE `skill_id` = 627 AND `skill_level` > 600;
UPDATE `character_skills` SET `skill_level` = (`skill_level` - 400) WHERE `skill_id` = 627 AND `skill_level` > 500;
UPDATE `character_skills` SET `skill_level` = (`skill_level` - 300) WHERE `skill_id` = 627 AND `skill_level` > 400;
UPDATE `character_skills` SET `skill_level` = (`skill_level` - 200) WHERE `skill_id` = 627 AND `skill_level` > 300;