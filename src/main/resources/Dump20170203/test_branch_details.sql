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
-- Table structure for table `branch_details`
--

DROP TABLE IF EXISTS `branch_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `branch_details` (
  `branch_id` int(11) NOT NULL AUTO_INCREMENT,
  `branch_code` varchar(45) NOT NULL,
  `branch_name` varchar(45) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`branch_id`),
  UNIQUE KEY `branch_code_UNIQUE` (`branch_code`),
  UNIQUE KEY `branch_name_UNIQUE` (`branch_name`),
  KEY `company_id_idx` (`company_id`),
  KEY `created_by_branch_idx` (`created_by`),
  KEY `modified_by_branch_idx` (`modified_by`),
  CONSTRAINT `company_id_branch` FOREIGN KEY (`company_id`) REFERENCES `company_details` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_branch` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_branch` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_details`
--

LOCK TABLES `branch_details` WRITE;
/*!40000 ALTER TABLE `branch_details` DISABLE KEYS */;
INSERT INTO `branch_details` VALUES (1,'brach code1','brach code1',1,1,NULL,1,'2017-01-01 20:09:36','Y'),(2,'brach code2','brach code2',2,1,'2017-01-01 19:48:35',1,'2017-01-01 19:48:57','N'),(3,'brach code3','brach code3',1,1,'2017-01-01 19:51:18',1,'2017-01-01 18:39:56','Y'),(4,'branch code 4','branch code 4',1,1,'2017-01-01 18:33:49',1,'2017-01-01 18:42:52','Y');
/*!40000 ALTER TABLE `branch_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-03 18:54:53
