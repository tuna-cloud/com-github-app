/*
SQLyog Enterprise - MySQL GUI v7.02 
MySQL - 5.5.5-10.2.11-MariaDB : Database - mis
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`mis` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mis`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `account` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(80) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `is_enable` smallint(6) DEFAULT 0,
  `address` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `photo_url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `AK_ACCOUNT` (`account`),
  UNIQUE KEY `AK_MOBILE` (`mobile`),
  UNIQUE KEY `AK_EMAIL` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `account` */

insert  into `account`(`account_id`,`role_id`,`account`,`password`,`name`,`sex`,`email`,`mobile`,`is_enable`,`address`,`photo_url`,`remark`) values (1,1,'administrator','F6D7134164CF673274C70F37E6438F410E48A63786FCE2343662AF0D','超级管理员','男','xsy870712@163.com','15315086265',1,NULL,NULL,NULL);

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `log_id` bigint(20) unsigned NOT NULL,
  `code` varchar(80) DEFAULT NULL,
  `op_account` varchar(50) DEFAULT NULL,
  `op_name` varchar(50) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `idx_code_acc` (`code`,`op_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `log` */

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(60) CHARACTER SET utf8 DEFAULT NULL,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `show_order` int(11) DEFAULT 0,
  `icon` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `is_drop_down` int(2) DEFAULT 1 COMMENT '1:可展开 0：不可展开',
  `is_show` int(2) DEFAULT 0 COMMENT '1：显示 0：不显示',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `menu` */

insert  into `menu`(`menu_id`,`url`,`name`,`code`,`parent_id`,`show_order`,`icon`,`is_drop_down`,`is_show`) values (1,NULL,'删除--/log]','[DELETE][/api/log]',NULL,NULL,NULL,0,0),(2,NULL,'修改--/sysrestore]','[PUT][/api/sysrestore]',NULL,NULL,NULL,0,0),(3,NULL,'查询--/role]','[GET][/api/role]',NULL,NULL,NULL,0,0),(4,NULL,'删除--/role]','[DELETE][/api/role]',NULL,NULL,NULL,0,0),(5,NULL,'查询--/account]','[GET][/api/account]',NULL,NULL,NULL,0,0),(6,NULL,'删除--/menu]','[DELETE][/api/menu]',NULL,NULL,NULL,0,0),(7,NULL,'修改--/menu]','[PUT][/api/menu]',NULL,NULL,NULL,0,0),(8,NULL,'修改--/sysinstall]','[PUT][/api/sysinstall]',NULL,NULL,NULL,0,0),(9,NULL,'新增--/role]','[POST][/api/role]',NULL,NULL,NULL,0,0),(10,NULL,'修改--/sysdownload]','[PUT][/api/sysdownload]',NULL,NULL,NULL,0,0),(11,NULL,'修改--/account]','[PUT][/api/account]',NULL,NULL,NULL,0,0),(12,NULL,'删除--/account]','[DELETE][/api/account]',NULL,NULL,NULL,0,0),(13,NULL,'新增--/menu]','[POST][/api/menu]',NULL,NULL,NULL,0,0),(14,NULL,'新增--/account]','[POST][/api/account]',NULL,NULL,NULL,0,0),(15,NULL,'查询--/log]','[GET][/api/log]',NULL,NULL,NULL,0,0),(16,NULL,'修改--/sysbackup]','[PUT][/api/sysbackup]',NULL,NULL,NULL,0,0),(17,NULL,'查询--/menu]','[GET][/api/menu]',NULL,NULL,NULL,0,0),(18,NULL,'修改--/role]','[PUT][/api/role]',NULL,NULL,NULL,0,0);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `role` */

insert  into `role`(`role_id`,`name`) values (1,'超级管理员');

/*Table structure for table `role_popedom` */

DROP TABLE IF EXISTS `role_popedom`;

CREATE TABLE `role_popedom` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  UNIQUE KEY `role_id` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `role_popedom` */

insert  into `role_popedom`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
