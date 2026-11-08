-- Recommendation system rework by Savormix
-- This script must be executed prior to launching the server!
INSERT INTO `character_recommend_data` (`charId`, `evaluationAble`, `evaluationPoints`, `lastUpdate`)
SELECT `charId`, `rec_left`, `rec_have`, `last_recom_date` FROM `characters`;
ALTER TABLE `characters` DROP `rec_left`, DROP `rec_have`, DROP `last_recom_date`;
DELETE FROM `global_tasks` WHERE `task` LIKE "Recom";