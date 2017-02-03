-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.6.28

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
-- Table structure for table `department_details`
--

DROP TABLE IF EXISTS `department_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_details` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(45) DEFAULT NULL,
  `dept_code` varchar(45) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `dept_name_UNIQUE` (`dept_name`),
  UNIQUE KEY `dept_code_UNIQUE` (`dept_code`),
  KEY `created_by_dept_idx` (`created_by`),
  KEY `modified_by_dept_idx` (`modified_by`),
  CONSTRAINT `created_by_dept` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_dept` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_details`
--

LOCK TABLES `department_details` WRITE;
/*!40000 ALTER TABLE `department_details` DISABLE KEYS */;
INSERT INTO `department_details` VALUES (1,'IT','IIT',1,NULL,1,'2017-01-01 08:23:03','Y'),(43,'Human resource','HR',NULL,'2017-01-01 08:08:56',1,'2017-01-01 21:35:32','Y'),(44,'asdfa','asdfa',NULL,'2017-01-01 08:08:56',1,'2017-01-01 18:57:21','Y'),(47,'Sales','PPP',NULL,NULL,1,'2017-01-01 17:01:27','Y'),(48,'Support','asdfasd',NULL,'2017-01-01 08:25:22',1,'2017-01-01 21:39:48','Y'),(49,'kk','sadfaasdasdfasf',NULL,'2017-01-01 08:28:01',1,'2017-01-01 21:42:32','Y'),(50,'pp','pp',NULL,'2017-01-01 08:29:00',NULL,NULL,'N'),(51,'r1','r1',NULL,NULL,1,'2017-01-01 08:35:26','N'),(52,'bbbb','bbb',NULL,NULL,1,'2017-01-01 08:41:27','Y'),(53,'r','r',1,'2017-01-01 08:50:24',1,'2017-01-01 21:03:00','N'),(54,'rrrrrr','rrr',1,'2017-01-01 08:51:12',NULL,NULL,'N'),(55,'ttt','tttt',1,'2017-01-01 08:51:12',1,'2017-01-01 21:01:45','N'),(56,'eeeeeeeeee','eeeeeeeeeee',1,'2017-01-01 21:19:59',NULL,NULL,'N'),(57,'yyyyyyyyyyy','yyyyyyyyyyyy',1,'2017-01-01 21:20:15',NULL,NULL,'N'),(58,'Admin','Admin',1,'2017-01-01 21:49:57',NULL,NULL,'Y');
/*!40000 ALTER TABLE `department_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-03 18:54:55
