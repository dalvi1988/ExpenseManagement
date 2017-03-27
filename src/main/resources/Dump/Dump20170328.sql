-- MySQL dump 10.13  Distrib 5.6.19, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.6.22-enterprise-commercial-advanced-log

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

--
-- Table structure for table `company_details`
--

DROP TABLE IF EXISTS `company_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_details` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_code` varchar(45) NOT NULL,
  `company_name` varchar(45) NOT NULL,
  `created_by` bigint(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`company_id`),
  UNIQUE KEY `company_code_UNIQUE` (`company_code`),
  UNIQUE KEY `company_name_UNIQUE` (`company_name`),
  KEY `created_by_company_idx` (`created_by`),
  KEY `modified_by_company_idx` (`modified_by`),
  CONSTRAINT `created_by_company` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_company` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_details`
--

LOCK TABLES `company_details` WRITE;
/*!40000 ALTER TABLE `company_details` DISABLE KEYS */;
INSERT INTO `company_details` VALUES (1,'CJ','C&J company',NULL,NULL,NULL,NULL,'Y'),(2,'India','India',NULL,NULL,1,'2017-01-01 19:12:49','N'),(3,'Oil Pvt Ldt','OlL',NULL,NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `company_details` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_details`
--

LOCK TABLES `department_details` WRITE;
/*!40000 ALTER TABLE `department_details` DISABLE KEYS */;
INSERT INTO `department_details` VALUES (1,'IT','IIT',1,NULL,1,'2017-01-01 11:56:32','Y'),(43,'Human resource','HR',NULL,'2017-01-01 08:08:56',1,'2017-01-01 21:35:32','Y'),(44,'asdfa','asdfa',NULL,'2017-01-01 08:08:56',1,'2017-01-01 18:57:21','Y'),(47,'Sales','PPP',NULL,NULL,1,'2017-01-01 11:56:53','Y'),(48,'Support','asdfasd',NULL,'2017-01-01 08:25:22',1,'2017-01-01 21:39:48','Y'),(49,'kk','sadfaasdasdfasf',NULL,'2017-01-01 08:28:01',1,'2017-01-01 21:42:32','Y'),(50,'pp','pp',NULL,'2017-01-01 08:29:00',NULL,NULL,'N'),(51,'r1','r1',NULL,NULL,1,'2017-01-01 08:35:26','N'),(52,'bbbb','bbb',NULL,NULL,1,'2017-01-01 08:41:27','Y'),(53,'r','r',1,'2017-01-01 08:50:24',1,'2017-01-01 21:03:00','N'),(54,'rrrrrr','rrr',1,'2017-01-01 08:51:12',NULL,NULL,'N'),(55,'ttt','tttt',1,'2017-01-01 08:51:12',1,'2017-01-01 21:01:45','N'),(56,'eeeeeeeeee','eeeeeeeeeee',1,'2017-01-01 21:19:59',NULL,NULL,'N'),(57,'yyyyyyyyyyy','yyyyyyyyyyyy',1,'2017-01-01 21:20:15',NULL,NULL,'N'),(58,'Admin','Admin',1,'2017-01-01 21:49:57',NULL,NULL,'Y'),(59,'C & E','C & E',1,'2017-01-01 11:57:34',NULL,NULL,'Y');
/*!40000 ALTER TABLE `department_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_head`
--

DROP TABLE IF EXISTS `department_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_head` (
  `department_head_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `employee_id` bigint(10) DEFAULT NULL,
  `created_by` bigint(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`department_head_id`),
  KEY `emp_id_dept_head_idx` (`employee_id`),
  KEY `branch_id_dept_head_idx` (`branch_id`),
  KEY `dept_id_dept_head_idx` (`department_id`),
  KEY `created_by_dept_head_idx` (`created_by`),
  KEY `modified_by_dept_head_idx` (`modified_by`),
  CONSTRAINT `branch_id_dept_head` FOREIGN KEY (`branch_id`) REFERENCES `branch_details` (`branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_dept_head` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dept_id_dept_head` FOREIGN KEY (`department_id`) REFERENCES `department_details` (`dept_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `emp_id_dept_head` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_dept_head` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_head`
--

LOCK TABLES `department_head` WRITE;
/*!40000 ALTER TABLE `department_head` DISABLE KEYS */;
INSERT INTO `department_head` VALUES (1,48,1,1,NULL,NULL,1,'2017-01-01 19:29:12','Y'),(2,43,1,1,1,'2017-01-01 10:17:34',NULL,NULL,'Y'),(3,47,3,4,1,'2017-01-01 10:18:13',NULL,NULL,'Y'),(4,48,1,3,1,'2017-01-01 10:18:41',NULL,NULL,'Y'),(5,44,1,4,1,'2017-01-01 10:19:01',NULL,NULL,'Y'),(6,48,1,4,1,'2017-01-01 10:23:43',NULL,NULL,'Y'),(7,55,1,3,1,'2017-01-01 23:10:24',1,'2017-01-01 11:48:44','Y'),(8,43,1,4,1,'2017-01-01 09:09:49',NULL,NULL,'Y'),(9,48,3,2,1,'2017-01-01 10:17:28',NULL,NULL,'Y'),(10,43,4,1,1,'2017-01-01 11:49:07',NULL,NULL,'Y');
/*!40000 ALTER TABLE `department_head` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_details`
--

DROP TABLE IF EXISTS `employee_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_details` (
  `employee_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `branch_id` int(11) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `reporting_mgr` bigint(11) DEFAULT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) NOT NULL,
  `email_id` varchar(45) NOT NULL,
  `gender` char(1) DEFAULT NULL,
  `created_by` bigint(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL DEFAULT 'A',
  PRIMARY KEY (`employee_id`),
  KEY `branch_id_emp_idx` (`branch_id`),
  KEY `dept_id_emp_idx` (`department_id`),
  KEY `created_by_emp_idx` (`created_by`),
  KEY `modified_by_emp_idx` (`modified_by`),
  KEY `reporting_mgr_emp_idx` (`reporting_mgr`),
  CONSTRAINT `branch_id_emp` FOREIGN KEY (`branch_id`) REFERENCES `branch_details` (`branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_emp` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dept_id_emp` FOREIGN KEY (`department_id`) REFERENCES `department_details` (`dept_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_emp` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reporting_mgr_emp` FOREIGN KEY (`reporting_mgr`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_details`
--

LOCK TABLES `employee_details` WRITE;
/*!40000 ALTER TABLE `employee_details` DISABLE KEYS */;
INSERT INTO `employee_details` VALUES (1,1,1,1,'Chaitanya',NULL,'Dalvi','chaitanya@gmail.com','F',NULL,NULL,1,'2017-01-01 11:52:38','Y'),(2,1,1,1,'Juilee',NULL,'Mathkar','juilee@gmail.com','F',NULL,NULL,1,'2017-01-01 11:59:35','Y'),(3,1,47,1,'Vinod',NULL,'Hollar','vinod@gmai.com','M',NULL,NULL,1,'2017-01-01 21:06:35','Y'),(4,1,43,1,'heena',NULL,'dere','heena@gmail.com','F',NULL,NULL,1,'2017-01-01 20:32:00','N'),(5,4,1,1,'2345',NULL,'jfj@3345','dfds@idfuiefdsjff','M',NULL,'2017-01-01 20:16:42',1,NULL,'Y'),(6,3,43,2,'      5555',NULL,'1111','rrrr@ggg.vv','M',NULL,'2017-02-26 11:55:54',1,'2017-01-01 11:55:54','Y');
/*!40000 ALTER TABLE `employee_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense_details`
--

DROP TABLE IF EXISTS `expense_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense_details` (
  `expense_detail_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `expense_header_id` bigint(10) DEFAULT NULL,
  `expense_name_id` int(10) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `from_location` varchar(45) DEFAULT NULL,
  `to_location` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `file_Name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`expense_detail_id`),
  KEY `exp_header_exp_details_idx` (`expense_header_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_details`
--

LOCK TABLES `expense_details` WRITE;
/*!40000 ALTER TABLE `expense_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense_header`
--

DROP TABLE IF EXISTS `expense_header`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense_header` (
  `expense_header_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(10) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `purpose` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`expense_header_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_header`
--

LOCK TABLES `expense_header` WRITE;
/*!40000 ALTER TABLE `expense_header` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_header` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `functional_flow`
--

DROP TABLE IF EXISTS `functional_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `functional_flow` (
  `flow_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_branch_flow` char(1) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `no_of_level` int(11) DEFAULT NULL,
  `level1` bigint(10) NOT NULL,
  `level2` bigint(10) DEFAULT NULL,
  `level3` bigint(10) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`flow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functional_flow`
--

LOCK TABLES `functional_flow` WRITE;
/*!40000 ALTER TABLE `functional_flow` DISABLE KEYS */;
INSERT INTO `functional_flow` VALUES (1,'N',1,1,2,-10,-1,NULL,NULL,NULL,NULL,NULL,'N'),(2,'N',1,43,2,-10,4,NULL,1,'2017-01-01 22:19:19',NULL,NULL,'N'),(3,'N',1,48,2,-10,-11,NULL,1,'2017-01-01 20:58:45',NULL,NULL,'N'),(4,'N',1,50,2,-10,-11,NULL,1,'2017-01-01 21:00:59',NULL,NULL,'Y'),(5,'N',1,54,3,-10,-11,-1,1,'2017-01-01 21:02:20',NULL,NULL,'Y'),(6,'N',1,58,1,-10,NULL,NULL,1,'2017-01-01 21:27:37',NULL,NULL,'Y'),(7,'N',1,44,1,-11,NULL,NULL,1,'2017-01-01 21:28:19',NULL,NULL,'Y'),(8,'N',1,50,1,-10,NULL,NULL,1,'2017-01-01 21:30:49',NULL,NULL,'Y'),(9,'N',1,55,1,-11,NULL,NULL,1,'2017-01-01 21:31:55',NULL,NULL,'Y'),(10,'N',1,56,1,-10,NULL,NULL,1,'2017-01-01 21:34:00',NULL,NULL,'Y'),(11,'N',1,53,1,-10,NULL,NULL,1,'2017-01-01 21:35:25',NULL,NULL,'Y'),(12,'N',1,52,1,-10,NULL,NULL,1,'2017-01-01 21:37:41',NULL,NULL,'Y'),(13,'N',1,50,1,-10,NULL,NULL,1,'2017-01-01 21:39:17',NULL,NULL,'Y'),(14,'N',1,52,1,-10,NULL,NULL,1,'2017-01-01 21:41:38',NULL,NULL,'Y'),(15,'N',3,43,1,-10,NULL,NULL,1,'2017-01-01 21:42:43',NULL,NULL,'Y'),(16,'N',4,1,1,-10,NULL,NULL,1,'2017-01-01 21:43:27',NULL,NULL,'Y'),(17,'Y',1,NULL,2,-11,-10,NULL,1,'2017-01-01 17:22:42',NULL,NULL,'N'),(18,'N',1,NULL,1,-10,NULL,NULL,1,'2017-01-01 17:35:06',NULL,NULL,'N'),(19,'N',1,NULL,1,-10,NULL,NULL,1,'2017-01-01 18:22:01',NULL,NULL,'Y'),(20,'N',1,NULL,1,-10,NULL,NULL,1,'2017-01-01 18:23:22',NULL,NULL,'Y'),(21,'N',1,NULL,1,-10,NULL,NULL,1,'2017-01-01 18:25:16',NULL,NULL,'Y'),(22,'Y',1,NULL,1,-10,NULL,NULL,1,'2017-01-01 18:29:00',NULL,NULL,'Y'),(23,'N',1,1,1,-10,NULL,NULL,1,'2017-01-01 19:41:29',NULL,NULL,'Y');
/*!40000 ALTER TABLE `functional_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `levels`
--

DROP TABLE IF EXISTS `levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `levels` (
  `level_id` int(11) NOT NULL,
  `level_name` varchar(45) NOT NULL,
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `levels`
--

LOCK TABLES `levels` WRITE;
/*!40000 ALTER TABLE `levels` DISABLE KEYS */;
INSERT INTO `levels` VALUES (-12,'Reporting Manager\'s RM'),(-11,'Reporting Manager'),(-1,'Department Head');
/*!40000 ALTER TABLE `levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_details`
--

DROP TABLE IF EXISTS `login_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_details` (
  `login_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `employee_id` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`login_id`),
  KEY `emp_id_idx` (`employee_id`),
  CONSTRAINT `emp_id` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_details`
--

LOCK TABLES `login_details` WRITE;
/*!40000 ALTER TABLE `login_details` DISABLE KEYS */;
INSERT INTO `login_details` VALUES (1,'chaitanya','$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y',1);
/*!40000 ALTER TABLE `login_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_role_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  `login_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `login_id_user_idx` (`login_id`),
  CONSTRAINT `login_id_user` FOREIGN KEY (`login_id`) REFERENCES `login_details` (`login_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ROLE_ADMIN',1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-28 19:50:18
