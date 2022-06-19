/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.21-log : Database - db_stu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_stu` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_stu`;

/*Table structure for table `t_major` */

DROP TABLE IF EXISTS `t_major`;

CREATE TABLE `t_major` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `t_major` */

insert  into `t_major`(`id`,`name`) values (1,'生物工程'),(2,'风景园林'),(3,'电子商务'),(4,'汉语言文学'),(5,'应用数学');

/*Table structure for table `t_stu` */

DROP TABLE IF EXISTS `t_stu`;

CREATE TABLE `t_stu` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `gender` varchar(20) NOT NULL,
  `birthday` varchar(32) NOT NULL,
  `tel` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Data for the table `t_stu` */

insert  into `t_stu`(`id`,`name`,`gender`,`birthday`,`tel`) values (1,'张尧','男','2001.10.30','15623456789'),(2,'李斯','男','2002.4.7','15098765432'),(4,'江雪','女','2002.7.26','14723456789'),(5,'林依一','女','2001.9.17','13598765432'),(6,'周扬','男','2000.8.15','16523456789'),(7,'李景元','男','2002.5.18','18723456789'),(8,'王希月','女','2002.9.21','13723456789'),(9,'张雨','男','2001.3.19','17123456789');

/*Table structure for table `t_stu_major` */

DROP TABLE IF EXISTS `t_stu_major`;

CREATE TABLE `t_stu_major` (
  `stuId` int(11) DEFAULT NULL,
  `majorId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_stu_major` */

insert  into `t_stu_major`(`stuId`,`majorId`) values (1,1),(2,2),(3,3),(4,4),(5,1),(6,2),(7,3),(8,4),(9,1);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`role`,`name`,`pwd`) values (1,'admin','admin','123456'),(2,'student','student','123456'),(3,'tom','student','123456'),(4,'jerry','student','123456');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
