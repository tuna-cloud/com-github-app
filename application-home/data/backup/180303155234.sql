-- MySQL dump 10.16  Distrib 10.2.11-MariaDB, for Win64 (AMD64)
--
-- Host: mysql1.net.com    Database: mis
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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,1,'administrator','DAF26241372A1CF729830F66F70D52968A5A4AF7C28E6D3FB2557AF1','超级管理员','男','xsy870712@163.com','15315086265',1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1520063525353000,'登录成功','administrator','administrator',NULL,NULL,'[Y]-->token:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8'),(1520063525397001,'帐号查询单个','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/account/current/login||null||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528'),(1520063528530002,'查询所有角色','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/role||null||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528/;Accept-Encoding'),(1520063528531003,'帐号查询所有','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56611||/api/account||offset=0&rows=20&keyword=&roleId=||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http:/'),(1520063531856004,'查询所有角色','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56611||/api/role||null||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528/;Accept-Encoding'),(1520063531856005,'帐号查询单个','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/account/current/login||null||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528'),(1520063536500006,'帐号查询单个','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/account/current/login||null||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528'),(1520063536502007,'查询所有角色','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56611||/api/role||null||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528/;Accept-Encoding'),(1520063538801008,'日志查询','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/log||offset=0&rows=20&account=||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9'),(1520063540119009,'查询备份','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/sysbackup||offset=0&rows=20||Host=127.0.0.1:8080;Connection=keep-alive;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost:9528'),(1520063554560010,'系统备份','administrator','超级管理员',1,'超级管理员','[Y]127.0.0.1:56586||/api/sysbackup||null||Host=127.0.0.1:8080;Connection=keep-alive;Content-Length=0;Accept=application/json, text/plain, */*;Origin=http://localhost:9528;X-Token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiYWRtaW5pc3RyYXRvciIsImlhdCI6MTUyMDA2MzUyNSwiZXhwIjoxNTIwMDc0MzI1fQ.yWa5yR8LYxi_2p4C7QEkLY05Il3xOnoIfiajeazy1N8;User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36;Referer=http://localhost');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `popedom`
--

DROP TABLE IF EXISTS `popedom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `popedom` (
  `popedom_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) CHARACTER SET utf8 DEFAULT NULL,
  `code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`popedom_id`),
  UNIQUE KEY `idx_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `popedom`
--

LOCK TABLES `popedom` WRITE;
/*!40000 ALTER TABLE `popedom` DISABLE KEYS */;
INSERT INTO `popedom` VALUES (1,'帐号添加','/.+/account/POST','管理员的功能，可以添加各种角色的帐号'),(2,'账号删除','/.+/account/.+/DELETE','管理员的功能，可以删除系统中的所有帐号'),(3,'账号修改','/.+/account/PUT','基本功能，修改帐号的基本信息，如姓名、电话、邮箱等'),(4,'账号停启用','/.+/account/enable/PUT','管理员的功能，可以停用或者启用某个帐号'),(5,'帐号查询单个','/.+/account/.+/GET','管理员的功能，查询特定帐号的信息'),(6,'帐号查询所有','/.+/account/GET','管理员的功能，查询系统中的所有帐号信息'),(7,'当前登录账号','/.+/account/current/login/GET','基本功能，查询当前登录帐号的基本信息'),(8,'帐号密码重置','/.+/account/resetpwd/.+/PUT','管理员的功能，某个帐号密码忘记后，可以通过此接口对密码进行重置'),(9,'修改帐号密码','/.+/account/editpwd/PUT','基本功能，自助修改自己帐号的密码'),(10,'退出帐号登录','/.+/account/logout/PUT','基本功能，清除服务器session信息，并退出登录'),(11,'在线用户','/.+/account/session/GET','查询系统当前活跃用户'),(12,'日志查询','/.+/log/GET','查询系统的访问日志'),(13,'日志删除','/.+/log/DELETE','清空系统的访问日志'),(14,'增加角色','/.+/role/POST','管理员功能，创建系统角色'),(15,'删除角色','/.+/role/.+/DELETE','管理员功能，删除系统角色'),(16,'更新角色','/.+/role/PUT','管理员功能，更新系统角色信息'),(17,'查询角色','/.+/role/.+/GET','查询系统的单个角色信息'),(18,'查询所有角色','/.+/role/GET','查询系统的所有角色信息'),(19,'权限修改','/.+/role/popedom/PUT','管理员功能，分配每个系统角色的操作权限'),(20,'权限查询','/.+/role/popedom/.+/GET','管理员功能，查询每个角色拥有的操作权限集合'),(21,'界面权限查询','/.+/role/popedom/vue/.+/GET','基本功能，查询角色拥有的前端界面集合(可见)'),(22,'接口权限查询','/.+/role/popedom/api/.+/GET','管理员功能，查询角色拥有的后台接口权限'),(23,'查询备份','/.+/sysbackup/GET','查询系统中的数据库备份文件'),(24,'上传备份','/.+/sysbackup/POST','从本地电脑上传备份文件到服务器'),(25,'系统备份','/.+/sysbackup/PUT','执行一次服务器数据库备份'),(26,'删除备份','/.+/sysbackup/DELETE','删除系统中的某一个数据库备份文件'),(27,'系统恢复','/.+/sysrestore/PUT','恢复某一个备份文件到数据库中'),(28,'vue[主面板]','vuedashboard','网页权限，系统首页'),(29,'vue[系统管理]','vuesystemmgr','网页权限，系统管理菜单'),(30,'vue[账号管理]','vueaccountmgr','网页权限，帐号管理页面'),(31,'vue[在线用户]','vueonlineaccount','网页权限，在线用户页面'),(32,'vue[密码修改]','vueaccountpwd','网页权限，密码修改页面'),(33,'vue[账号信息]','vueaccountinfo','网页权限，当前登录帐号信息页面'),(34,'vue[角色管理]','vuerolemgr','网页权限，角色管理页面'),(35,'vue[数据备份]','vuedbback','网页权限，数据备份页面'),(36,'vue[日志管理]','vuelogmgr','网页权限，日志管理页面');
/*!40000 ALTER TABLE `popedom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'超级管理员');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_popedom`
--

DROP TABLE IF EXISTS `role_popedom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_popedom` (
  `role_id` int(11) DEFAULT NULL,
  `popedom_id` int(11) DEFAULT NULL,
  UNIQUE KEY `role_id` (`role_id`,`popedom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_popedom`
--

LOCK TABLES `role_popedom` WRITE;
/*!40000 ALTER TABLE `role_popedom` DISABLE KEYS */;
INSERT INTO `role_popedom` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36);
/*!40000 ALTER TABLE `role_popedom` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-03 15:52:34
