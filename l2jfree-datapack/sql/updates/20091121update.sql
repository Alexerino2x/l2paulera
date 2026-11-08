UPDATE `characters` SET `vitality_points` = FLOOR(`vitality_points`);
ALTER TABLE `characters` MODIFY `vitality_points` SMALLINT UNSIGNED NOT NULL DEFAULT 0;