CREATE TABLE IF NOT EXISTS `couples` (
  `id` int(11) NOT NULL auto_increment,
  `player1Id` int(11) NOT NULL DEFAULT 0,
  `player2Id` int(11) NOT NULL DEFAULT 0,
  `maried` varchar(5) DEFAULT NULL,
  `affiancedDate` decimal(20,0) DEFAULT 0,
  `weddingDate` decimal(20,0) DEFAULT 0,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;
