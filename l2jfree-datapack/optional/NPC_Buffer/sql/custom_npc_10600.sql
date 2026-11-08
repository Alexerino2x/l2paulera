-- --------------------------------------------------------
--
-- SQL Template for NPC Buffer (by G1ta0) for L2j-Free
--
-- --------------------------------------------------------

REPLACE INTO `custom_npc`
(`id`,`idTemplate`,`Name`,`ServerSideName`,`title`,`ServerSideTitle`,`class`,`collision_radius`,`collision_height`,`level`,sex,`type`,`attackrange`,`hp`,`mp`,`str`,`con`,`dex`,`int`,`wit`,`men`,`exp`,`sp`,`patk`,`pdef`,`matk`,`mdef`,`atkspd`,`aggro`,`matkspd`,`rhand`,`lhand`,`armor`,`walkspd`,`runspd`)
SELECT 10600,`idTemplate`,'Ephesus',1,'Deluxe Buffer',1,`class`,`collision_radius`,`collision_height`,`level`,sex,'L2Npc',`attackrange`,99999,9999,`str`,`con`,`dex`,`int`,`wit`,`men`,`exp`,`sp`,`patk`,`pdef`,`matk`,`mdef`,`atkspd`,`aggro`,`matkspd`,`rhand`,`lhand`,`armor`,`walkspd`,`runspd` 
From `npc` WHERE `id`=22128;
