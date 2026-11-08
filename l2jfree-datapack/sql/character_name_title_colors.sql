CREATE TABLE IF NOT EXISTS `character_name_title_colors` (
  `char_id` int(11) NOT NULL,
  `name_color` varchar(6) NOT NULL,
  `title_color` varchar(6) NOT NULL,
  PRIMARY KEY  (`char_id`)
) DEFAULT CHARSET=utf8;