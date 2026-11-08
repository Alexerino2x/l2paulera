ALTER TABLE `character_skills_save` ADD `systime` BIGINT UNSIGNED NOT NULL AFTER `reuse_delay`;
ALTER TABLE `npc` ADD `drop_herbs` enum('true','false') DEFAULT 'false' NOT NULL;
ALTER TABLE `custom_npc` ADD `drop_herbs` enum('true','false') DEFAULT 'false' NOT NULL;