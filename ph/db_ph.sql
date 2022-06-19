/*
MySQL Data Transfer
Source Host: localhost
Source Database: db_ph
Target Host: localhost
Target Database: db_ph
Date: 2022/5/1 17:19:41
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_pet
-- ----------------------------
DROP TABLE IF EXISTS `t_pet`;
CREATE TABLE `t_pet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `birthdate` varchar(16) NOT NULL,
  `photo` varchar(64) NOT NULL,
  `ownerId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_t_pet` (`ownerId`) USING BTREE,
  CONSTRAINT `FK_t_pet` FOREIGN KEY (`ownerId`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_speciality
-- ----------------------------
DROP TABLE IF EXISTS `t_speciality`;
CREATE TABLE `t_speciality` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(8) NOT NULL,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `tel` varchar(16) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_vet
-- ----------------------------
DROP TABLE IF EXISTS `t_vet`;
CREATE TABLE `t_vet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_vet_speciality
-- ----------------------------
DROP TABLE IF EXISTS `t_vet_speciality`;
CREATE TABLE `t_vet_speciality` (
  `vetId` int(11) NOT NULL,
  `specId` int(11) NOT NULL,
  KEY `FK_t_vet_specality` (`vetId`) USING BTREE,
  KEY `FK_t_vet_specality_2` (`specId`) USING BTREE,
  CONSTRAINT `FK_t_vet_specality` FOREIGN KEY (`vetId`) REFERENCES `t_vet` (`id`),
  CONSTRAINT `FK_t_vet_specality_2` FOREIGN KEY (`specId`) REFERENCES `t_speciality` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_visit
-- ----------------------------
DROP TABLE IF EXISTS `t_visit`;
CREATE TABLE `t_visit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `petId` int(11) NOT NULL,
  `vetId` int(11) NOT NULL,
  `visitdate` varchar(10) NOT NULL,
  `description` text NOT NULL,
  `treatment` text NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `FK_t_visit` (`petId`) USING BTREE,
  CONSTRAINT `FK_t_visit` FOREIGN KEY (`petId`) REFERENCES `t_pet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_pet` VALUES ('1', 'tom', '2015.03.25', 'photo/tom.jpg', '3');
INSERT INTO `t_pet` VALUES ('2', 'tony', '2016.02.17', 'photo/tony.jpg', '3');
INSERT INTO `t_pet` VALUES ('3', 'jerry', '2016.03.15', 'photo/jerry.jpg', '4');
INSERT INTO `t_speciality` VALUES ('1', '内科');
INSERT INTO `t_speciality` VALUES ('2', '外科');
INSERT INTO `t_speciality` VALUES ('3', '眼科');
INSERT INTO `t_speciality` VALUES ('4', '皮肤科');
INSERT INTO `t_user` VALUES ('1', 'admin', 'admin', '1234', '', null);
INSERT INTO `t_user` VALUES ('2', 'admin', '张三', 'zhangsan', null, null);
INSERT INTO `t_user` VALUES ('3', 'customer', 'tom', '123', '13812345678', '武汉市洪山区');
INSERT INTO `t_user` VALUES ('4', 'customer', '杰瑞', '123', '13915247419', '武汉市江夏区');
INSERT INTO `t_user` VALUES ('5', 'customer', '张思', '123456', '15145789521', '武汉市武昌区');
INSERT INTO `t_user` VALUES ('6', 'customer', '赵唯', '123456', '15821475890', '武汉');
INSERT INTO `t_vet` VALUES ('1', '刘松');
INSERT INTO `t_vet` VALUES ('2', '李斯');
INSERT INTO `t_vet` VALUES ('3', '王明');
INSERT INTO `t_vet_speciality` VALUES ('3', '2');
INSERT INTO `t_vet_speciality` VALUES ('2', '2');
INSERT INTO `t_vet_speciality` VALUES ('1', '3');
INSERT INTO `t_visit` VALUES ('2', '1', '3', '2019-01-18', '皮肤炎症', '局部涂抹药膏，涂3天');
INSERT INTO `t_visit` VALUES ('3', '1', '2', '2019-01-18', '肠胃不适', '健胃消食片，一天2次，一次1片');
