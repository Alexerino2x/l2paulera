DROP TABLE IF EXISTS `ctf_teams`;
CREATE TABLE `ctf_teams` (
  `teamId` int(4) NOT NULL DEFAULT 0,
  `teamName` varchar(255) NOT NULL DEFAULT '',
  `teamX` int(11) NOT NULL DEFAULT 0,
  `teamY` int(11) NOT NULL DEFAULT 0,
  `teamZ` int(11) NOT NULL DEFAULT 0,
  `teamColor` int(11) NOT NULL DEFAULT 0,
  `flagX` int(11) NOT NULL DEFAULT 0,
  `flagY` int(11) NOT NULL DEFAULT 0,
  `flagZ` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`teamId`)
) DEFAULT CHARSET=utf8;