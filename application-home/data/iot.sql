CREATE DATABASE  IF NOT EXISTS `iot` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `iot`;
-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: 172.188.0.11    Database: iot
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `T_DEVICE`
--

DROP TABLE IF EXISTS `T_DEVICE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_DEVICE` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `secret` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `product_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FK_Reference_18` (`product_number`),
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`product_number`) REFERENCES `T_PRODUCT` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_DEVICE`
--

LOCK TABLES `T_DEVICE` WRITE;
/*!40000 ALTER TABLE `T_DEVICE` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_DEVICE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_FILE`
--

DROP TABLE IF EXISTS `T_FILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_FILE` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `path` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_FILE`
--

LOCK TABLES `T_FILE` WRITE;
/*!40000 ALTER TABLE `T_FILE` DISABLE KEYS */;
INSERT INTO `T_FILE` VALUES (1,'123','123');
/*!40000 ALTER TABLE `T_FILE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_MENU`
--

DROP TABLE IF EXISTS `T_MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_MENU` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `parent_number` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT '0',
  `icon` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `is_drop_down` int(2) DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_MENU`
--

LOCK TABLES `T_MENU` WRITE;
/*!40000 ALTER TABLE `T_MENU` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_OPERATION`
--

DROP TABLE IF EXISTS `T_OPERATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_OPERATION` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `filter_url` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `parent_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_OPERATION`
--

LOCK TABLES `T_OPERATION` WRITE;
/*!40000 ALTER TABLE `T_OPERATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_OPERATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_PAGE`
--

DROP TABLE IF EXISTS `T_PAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_PAGE` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_PAGE`
--

LOCK TABLES `T_PAGE` WRITE;
/*!40000 ALTER TABLE `T_PAGE` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_PAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_POPEDOM`
--

DROP TABLE IF EXISTS `T_POPEDOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_POPEDOM` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_POPEDOM`
--

LOCK TABLES `T_POPEDOM` WRITE;
/*!40000 ALTER TABLE `T_POPEDOM` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_POPEDOM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_PRODUCT`
--

DROP TABLE IF EXISTS `T_PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_PRODUCT` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_PRODUCT`
--

LOCK TABLES `T_PRODUCT` WRITE;
/*!40000 ALTER TABLE `T_PRODUCT` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_PRODUCT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_DEVICE_USER`
--

DROP TABLE IF EXISTS `T_REL_DEVICE_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_DEVICE_USER` (
  `device_number` int(11) DEFAULT NULL,
  `user_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_20` (`device_number`),
  KEY `FK_Reference_21` (`user_number`),
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`device_number`) REFERENCES `T_DEVICE` (`number`),
  CONSTRAINT `FK_Reference_21` FOREIGN KEY (`user_number`) REFERENCES `T_USER` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_DEVICE_USER`
--

LOCK TABLES `T_REL_DEVICE_USER` WRITE;
/*!40000 ALTER TABLE `T_REL_DEVICE_USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_DEVICE_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_POPEDOM_FILE`
--

DROP TABLE IF EXISTS `T_REL_POPEDOM_FILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_POPEDOM_FILE` (
  `popedom_number` int(11) DEFAULT NULL,
  `file_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_14` (`file_number`),
  KEY `FK_Reference_15` (`popedom_number`),
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`file_number`) REFERENCES `T_FILE` (`number`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`popedom_number`) REFERENCES `T_POPEDOM` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_POPEDOM_FILE`
--

LOCK TABLES `T_REL_POPEDOM_FILE` WRITE;
/*!40000 ALTER TABLE `T_REL_POPEDOM_FILE` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_POPEDOM_FILE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_POPEDOM_MENU`
--

DROP TABLE IF EXISTS `T_REL_POPEDOM_MENU`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_POPEDOM_MENU` (
  `popedom_number` int(11) DEFAULT NULL,
  `menu_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_10` (`menu_number`),
  KEY `FK_Reference_11` (`popedom_number`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`menu_number`) REFERENCES `T_MENU` (`number`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`popedom_number`) REFERENCES `T_POPEDOM` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_POPEDOM_MENU`
--

LOCK TABLES `T_REL_POPEDOM_MENU` WRITE;
/*!40000 ALTER TABLE `T_REL_POPEDOM_MENU` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_POPEDOM_MENU` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_POPEDOM_OPERATION`
--

DROP TABLE IF EXISTS `T_REL_POPEDOM_OPERATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_POPEDOM_OPERATION` (
  `popedom_number` int(11) DEFAULT NULL,
  `op_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_16` (`op_number`),
  KEY `FK_Reference_17` (`popedom_number`),
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`op_number`) REFERENCES `T_OPERATION` (`number`),
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`popedom_number`) REFERENCES `T_POPEDOM` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_POPEDOM_OPERATION`
--

LOCK TABLES `T_REL_POPEDOM_OPERATION` WRITE;
/*!40000 ALTER TABLE `T_REL_POPEDOM_OPERATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_POPEDOM_OPERATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_POPEDOM_PAGE`
--

DROP TABLE IF EXISTS `T_REL_POPEDOM_PAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_POPEDOM_PAGE` (
  `popedom_number` int(11) DEFAULT NULL,
  `page_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_12` (`page_number`),
  KEY `FK_Reference_13` (`popedom_number`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`page_number`) REFERENCES `T_PAGE` (`number`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`popedom_number`) REFERENCES `T_POPEDOM` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_POPEDOM_PAGE`
--

LOCK TABLES `T_REL_POPEDOM_PAGE` WRITE;
/*!40000 ALTER TABLE `T_REL_POPEDOM_PAGE` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_POPEDOM_PAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_PRODUCT_USER`
--

DROP TABLE IF EXISTS `T_REL_PRODUCT_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_PRODUCT_USER` (
  `product_number` int(11) DEFAULT NULL,
  `user_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_22` (`product_number`),
  KEY `FK_Reference_23` (`user_number`),
  CONSTRAINT `FK_Reference_22` FOREIGN KEY (`product_number`) REFERENCES `T_PRODUCT` (`number`),
  CONSTRAINT `FK_Reference_23` FOREIGN KEY (`user_number`) REFERENCES `T_USER` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_PRODUCT_USER`
--

LOCK TABLES `T_REL_PRODUCT_USER` WRITE;
/*!40000 ALTER TABLE `T_REL_PRODUCT_USER` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_PRODUCT_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_ROLE_POPEDOM`
--

DROP TABLE IF EXISTS `T_REL_ROLE_POPEDOM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_ROLE_POPEDOM` (
  `role_number` int(11) DEFAULT NULL,
  `popedom_number` int(11) DEFAULT NULL,
  KEY `FK_Reference_8` (`role_number`),
  KEY `FK_Reference_9` (`popedom_number`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`role_number`) REFERENCES `T_ROLE` (`number`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`popedom_number`) REFERENCES `T_POPEDOM` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_ROLE_POPEDOM`
--

LOCK TABLES `T_REL_ROLE_POPEDOM` WRITE;
/*!40000 ALTER TABLE `T_REL_ROLE_POPEDOM` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_REL_ROLE_POPEDOM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_REL_ROLE_USER`
--

DROP TABLE IF EXISTS `T_REL_ROLE_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_REL_ROLE_USER` (
  `role_number` int(11) DEFAULT NULL,
  `user_number` int(11) DEFAULT NULL,
  UNIQUE KEY `user_number` (`user_number`),
  KEY `FK_Reference_7` (`role_number`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`user_number`) REFERENCES `T_USER` (`number`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`role_number`) REFERENCES `T_ROLE` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_REL_ROLE_USER`
--

LOCK TABLES `T_REL_ROLE_USER` WRITE;
/*!40000 ALTER TABLE `T_REL_ROLE_USER` DISABLE KEYS */;
INSERT INTO `T_REL_ROLE_USER` VALUES (1,1);
/*!40000 ALTER TABLE `T_REL_ROLE_USER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_ROLE`
--

DROP TABLE IF EXISTS `T_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_ROLE` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_ROLE`
--

LOCK TABLES `T_ROLE` WRITE;
/*!40000 ALTER TABLE `T_ROLE` DISABLE KEYS */;
INSERT INTO `T_ROLE` VALUES (1,'系统管理员');
/*!40000 ALTER TABLE `T_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_TOPIC`
--

DROP TABLE IF EXISTS `T_TOPIC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_TOPIC` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `popedom` int(11) DEFAULT NULL,
  `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `device_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FK_Reference_19` (`device_number`),
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`device_number`) REFERENCES `T_DEVICE` (`number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_TOPIC`
--

LOCK TABLES `T_TOPIC` WRITE;
/*!40000 ALTER TABLE `T_TOPIC` DISABLE KEYS */;
/*!40000 ALTER TABLE `T_TOPIC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `T_USER`
--

DROP TABLE IF EXISTS `T_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `T_USER` (
  `number` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(80) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `is_enable` smallint(6) DEFAULT '0',
  `address` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `photo_url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `remark` text COLLATE utf8_bin,
  `parent_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `AK_MOBILE` (`mobile`),
  KEY `AK_EMAIL` (`email`),
  KEY `AK_ACCOUNT` (`account`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `T_USER`
--

LOCK TABLES `T_USER` WRITE;
/*!40000 ALTER TABLE `T_USER` DISABLE KEYS */;
INSERT INTO `T_USER` VALUES (1,'admin','A2EDD016141AC2E4CF1773A088239F230FB416E1335BCCDEBCCA0369','管理员','男','admin@163.com','15315086265',1,'china',NULL,NULL,NULL);
/*!40000 ALTER TABLE `T_USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-08 15:07:21
