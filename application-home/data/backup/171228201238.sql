-- MySQL dump 10.16  Distrib 10.2.11-MariaDB, for Win64 (AMD64)
--
-- Host: mysql1.net.com    Database: iot
-- ------------------------------------------------------
-- Server version	10.2.11-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_device`
--

DROP TABLE IF EXISTS `t_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_device` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `secret` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `product_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FK_Reference_18` (`product_number`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`product_number`) REFERENCES `t_product` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_device`
--

LOCK TABLES `t_device` WRITE;
/*!40000 ALTER TABLE `t_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_file`
--

DROP TABLE IF EXISTS `t_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_file` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `path` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_file`
--

LOCK TABLES `t_file` WRITE;
/*!40000 ALTER TABLE `t_file` DISABLE KEYS */;
INSERT INTO `t_file` VALUES (1,'123','123');
/*!40000 ALTER TABLE `t_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_menu`
--

DROP TABLE IF EXISTS `t_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_menu` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `parent_number` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT 0,
  `icon` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `is_drop_down` int(2) DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_menu`
--

LOCK TABLES `t_menu` WRITE;
/*!40000 ALTER TABLE `t_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_operation`
--

DROP TABLE IF EXISTS `t_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_operation` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `filter_url` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `parent_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_operation`
--

LOCK TABLES `t_operation` WRITE;
/*!40000 ALTER TABLE `t_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_page`
--

DROP TABLE IF EXISTS `t_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_page` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_page`
--

LOCK TABLES `t_page` WRITE;
/*!40000 ALTER TABLE `t_page` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_popedom`
--

DROP TABLE IF EXISTS `t_popedom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_popedom` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_popedom`
--

LOCK TABLES `t_popedom` WRITE;
/*!40000 ALTER TABLE `t_popedom` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_popedom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_product`
--

DROP TABLE IF EXISTS `t_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_product` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_product`
--

LOCK TABLES `t_product` WRITE;
/*!40000 ALTER TABLE `t_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_device_user`
--

DROP TABLE IF EXISTS `t_rel_device_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_device_user` (
  `device_number` int(11) DEFAULT NULL,
  `user_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_20` (`device_number`),
  KEY `FK_Reference_21` (`user_number`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`device_number`) REFERENCES `t_device` (`number`),
  CONSTRAINT `FK_Reference_21` FOREIGN KEY (`user_number`) REFERENCES `t_user` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_device_user`
--

LOCK TABLES `t_rel_device_user` WRITE;
/*!40000 ALTER TABLE `t_rel_device_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_device_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_popedom_file`
--

DROP TABLE IF EXISTS `t_rel_popedom_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_popedom_file` (
  `popedom_number` int(11) DEFAULT NULL,
  `file_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_14` (`file_number`),
  KEY `FK_Reference_15` (`popedom_number`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`file_number`) REFERENCES `t_file` (`number`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`popedom_number`) REFERENCES `t_popedom` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_popedom_file`
--

LOCK TABLES `t_rel_popedom_file` WRITE;
/*!40000 ALTER TABLE `t_rel_popedom_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_popedom_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_popedom_menu`
--

DROP TABLE IF EXISTS `t_rel_popedom_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_popedom_menu` (
  `popedom_number` int(11) DEFAULT NULL,
  `menu_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_10` (`menu_number`),
  KEY `FK_Reference_11` (`popedom_number`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`menu_number`) REFERENCES `t_menu` (`number`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`popedom_number`) REFERENCES `t_popedom` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_popedom_menu`
--

LOCK TABLES `t_rel_popedom_menu` WRITE;
/*!40000 ALTER TABLE `t_rel_popedom_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_popedom_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_popedom_operation`
--

DROP TABLE IF EXISTS `t_rel_popedom_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_popedom_operation` (
  `popedom_number` int(11) DEFAULT NULL,
  `op_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_16` (`op_number`),
  KEY `FK_Reference_17` (`popedom_number`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`op_number`) REFERENCES `t_operation` (`number`),
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`popedom_number`) REFERENCES `t_popedom` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_popedom_operation`
--

LOCK TABLES `t_rel_popedom_operation` WRITE;
/*!40000 ALTER TABLE `t_rel_popedom_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_popedom_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_popedom_page`
--

DROP TABLE IF EXISTS `t_rel_popedom_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_popedom_page` (
  `popedom_number` int(11) DEFAULT NULL,
  `page_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_12` (`page_number`),
  KEY `FK_Reference_13` (`popedom_number`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`page_number`) REFERENCES `t_page` (`number`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`popedom_number`) REFERENCES `t_popedom` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_popedom_page`
--

LOCK TABLES `t_rel_popedom_page` WRITE;
/*!40000 ALTER TABLE `t_rel_popedom_page` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_popedom_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_product_user`
--

DROP TABLE IF EXISTS `t_rel_product_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_product_user` (
  `product_number` int(11) DEFAULT NULL,
  `user_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_22` (`product_number`),
  KEY `FK_Reference_23` (`user_number`),
  CONSTRAINT `FK_Reference_22` FOREIGN KEY (`product_number`) REFERENCES `t_product` (`number`),
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`user_number`) REFERENCES `t_user` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_product_user`
--

LOCK TABLES `t_rel_product_user` WRITE;
/*!40000 ALTER TABLE `t_rel_product_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_product_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_role_popedom`
--

DROP TABLE IF EXISTS `t_rel_role_popedom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_role_popedom` (
  `role_number` int(11) DEFAULT NULL,
  `popedom_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_8` (`role_number`),
  KEY `FK_Reference_9` (`popedom_number`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`role_number`) REFERENCES `t_role` (`number`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`popedom_number`) REFERENCES `t_popedom` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_role_popedom`
--

LOCK TABLES `t_rel_role_popedom` WRITE;
/*!40000 ALTER TABLE `t_rel_role_popedom` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_rel_role_popedom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_rel_role_user`
--

DROP TABLE IF EXISTS `t_rel_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_rel_role_user` (
  `role_number` int(11) DEFAULT NULL,
  `user_number` int(11) DEFAULT NULL,
  UNIQUE KEY `user_number` (`user_number`),
  KEY `FK_Reference_7` (`role_number`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`user_number`) REFERENCES `t_user` (`number`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`role_number`) REFERENCES `t_role` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_rel_role_user`
--

LOCK TABLES `t_rel_role_user` WRITE;
/*!40000 ALTER TABLE `t_rel_role_user` DISABLE KEYS */;
INSERT INTO `t_rel_role_user` VALUES (1,1);
/*!40000 ALTER TABLE `t_rel_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_role`
--

DROP TABLE IF EXISTS `t_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_role` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_role`
--

LOCK TABLES `t_role` WRITE;
/*!40000 ALTER TABLE `t_role` DISABLE KEYS */;
INSERT INTO `t_role` VALUES (1,'系统管理员');
/*!40000 ALTER TABLE `t_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_topic`
--

DROP TABLE IF EXISTS `t_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_topic` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `popedom` int(11) DEFAULT NULL,
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `device_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FK_Reference_19` (`device_number`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`device_number`) REFERENCES `t_device` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_topic`
--

LOCK TABLES `t_topic` WRITE;
/*!40000 ALTER TABLE `t_topic` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(80) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `is_enable` smallint(6) DEFAULT 0,
  `address` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `photo_url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin DEFAULT NULL,
  `parent_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `AK_MOBILE` (`mobile`),
  KEY `AK_EMAIL` (`email`),
  KEY `AK_ACCOUNT` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','A2EDD016141AC2E4CF1773A088239F230FB416E1335BCCDEBCCA0369','管理员','男','admin@163.com','15315086265',1,'china',NULL,NULL,NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-28 20:12:38
