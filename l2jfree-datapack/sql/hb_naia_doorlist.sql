-- Tower Of Naia door list
-- author hex1r0
DROP TABLE IF EXISTS `hb_naia_doorlist`;
CREATE TABLE `hb_naia_doorlist` (
  `door_id` int(11) NOT NULL,
  `action_order` tinyint(1) NOT NULL,
  `room_id` int(11) NOT NULL,
  UNIQUE KEY `door_id` (`door_id`,`action_order`,`room_id`)
) DEFAULT CHARSET=utf8;

INSERT INTO `hb_naia_doorlist` VALUES (18250001, 0, 1);
INSERT INTO `hb_naia_doorlist` VALUES (18250001, 1, 1);
INSERT INTO `hb_naia_doorlist` VALUES (18250002, 0, 2);
INSERT INTO `hb_naia_doorlist` VALUES (18250002, 1, 2);
INSERT INTO `hb_naia_doorlist` VALUES (18250003, 0, 2);
INSERT INTO `hb_naia_doorlist` VALUES (18250003, 1, 2);
INSERT INTO `hb_naia_doorlist` VALUES (18250004, 0, 3);
INSERT INTO `hb_naia_doorlist` VALUES (18250004, 1, 3);
INSERT INTO `hb_naia_doorlist` VALUES (18250005, 0, 3);
INSERT INTO `hb_naia_doorlist` VALUES (18250005, 1, 3);
INSERT INTO `hb_naia_doorlist` VALUES (18250006, 0, 4);
INSERT INTO `hb_naia_doorlist` VALUES (18250006, 1, 4);
INSERT INTO `hb_naia_doorlist` VALUES (18250007, 0, 4);
INSERT INTO `hb_naia_doorlist` VALUES (18250007, 1, 4);
INSERT INTO `hb_naia_doorlist` VALUES (18250008, 0, 5);
INSERT INTO `hb_naia_doorlist` VALUES (18250008, 1, 5);
INSERT INTO `hb_naia_doorlist` VALUES (18250009, 0, 5);
INSERT INTO `hb_naia_doorlist` VALUES (18250009, 1, 5);
INSERT INTO `hb_naia_doorlist` VALUES (18250010, 0, 6);
INSERT INTO `hb_naia_doorlist` VALUES (18250011, 0, 6);
INSERT INTO `hb_naia_doorlist` VALUES (18250011, 1, 6);
INSERT INTO `hb_naia_doorlist` VALUES (18250013, 0, 7);
INSERT INTO `hb_naia_doorlist` VALUES (18250013, 1, 7);
INSERT INTO `hb_naia_doorlist` VALUES (18250014, 0, 8);
INSERT INTO `hb_naia_doorlist` VALUES (18250014, 1, 8);
INSERT INTO `hb_naia_doorlist` VALUES (18250015, 0, 8);
INSERT INTO `hb_naia_doorlist` VALUES (18250015, 1, 8);
INSERT INTO `hb_naia_doorlist` VALUES (18250017, 0, 9);
INSERT INTO `hb_naia_doorlist` VALUES (18250017, 1, 9);
INSERT INTO `hb_naia_doorlist` VALUES (18250018, 0, 10);
INSERT INTO `hb_naia_doorlist` VALUES (18250018, 1, 10);
INSERT INTO `hb_naia_doorlist` VALUES (18250019, 0, 10);
INSERT INTO `hb_naia_doorlist` VALUES (18250019, 1, 10);
INSERT INTO `hb_naia_doorlist` VALUES (18250021, 0, 11);
INSERT INTO `hb_naia_doorlist` VALUES (18250021, 1, 11);
INSERT INTO `hb_naia_doorlist` VALUES (18250022, 0, 12);
INSERT INTO `hb_naia_doorlist` VALUES (18250022, 1, 12);
INSERT INTO `hb_naia_doorlist` VALUES (18250023, 0, 12);
INSERT INTO `hb_naia_doorlist` VALUES (18250023, 1, 12);
INSERT INTO `hb_naia_doorlist` VALUES (18250101, 0, 7);
INSERT INTO `hb_naia_doorlist` VALUES (18250101, 1, 7);
INSERT INTO `hb_naia_doorlist` VALUES (18250102, 0, 9);
INSERT INTO `hb_naia_doorlist` VALUES (18250102, 1, 9);
INSERT INTO `hb_naia_doorlist` VALUES (18250103, 0, 11);
INSERT INTO `hb_naia_doorlist` VALUES (18250103, 1, 11);