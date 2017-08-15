CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `advance_details`
--

DROP TABLE IF EXISTS `advance_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advance_details` (
  `advance_details_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `employee_id` bigint(10) NOT NULL,
  `advance_number` varchar(50) DEFAULT NULL,
  `amount` double NOT NULL,
  `purpose` varchar(45) NOT NULL,
  `is_event` char(1) NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  `voucher_status_id` int(11) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`advance_details_id`),
  KEY `event_id_adv_details_idx` (`event_id`),
  KEY `voucher_id_adv_details_idx` (`voucher_status_id`),
  KEY `created_by_adv_details_idx` (`created_by`),
  KEY `modified_by_adv_details_idx` (`modified_by`),
  KEY `emp_id_adv_details_idx` (`employee_id`),
  CONSTRAINT `created_by_adv_details` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `emp_id_adv_details` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_id_adv_details` FOREIGN KEY (`event_id`) REFERENCES `event_details` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_adv_details` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_id_adv_details` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_details`
--

LOCK TABLES `advance_details` WRITE;
/*!40000 ALTER TABLE `advance_details` DISABLE KEYS */;
INSERT INTO `advance_details` VALUES (22,1,NULL,333,'asdfASDF','Y',5,1,NULL,NULL,1,'2017-08-05 20:12:50'),(23,1,NULL,23123,'ASDasd','N',NULL,1,1,'2017-06-19 10:46:16',NULL,NULL),(24,1,'Advance/19-June-2017/9',565,'sdfasdfasd','N',NULL,3,NULL,NULL,1,'2017-06-19 10:58:05'),(25,1,'Advance/19-June-2017/10',3433,'asdfasd','N',NULL,132,1,'2017-06-19 18:19:01',NULL,NULL),(26,1,'Advance/23-June-2017/11',2000,'askdjf','N',NULL,132,1,'2017-06-23 19:50:54',NULL,NULL),(27,1,'Advance/23-June-2017/12',32321,'ASDf','Y',5,132,1,'2017-06-23 19:51:03',NULL,NULL),(28,1,'Advance/23-June-2017/13',23423,'dsfasdf','Y',5,132,1,'2017-06-23 19:51:11',NULL,NULL),(29,1,'Advance/23-June-2017/14',2432,'Test1','N',NULL,132,1,'2017-06-23 19:51:23',NULL,NULL);
/*!40000 ALTER TABLE `advance_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advance_process_history`
--

DROP TABLE IF EXISTS `advance_process_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advance_process_history` (
  `process_history_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `advance_detail_id` bigint(10) NOT NULL,
  `voucher_status_id` int(11) DEFAULT NULL,
  `processed_by` bigint(10) DEFAULT NULL COMMENT 'Approved/rejected by ',
  `processed_date` datetime DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`process_history_id`),
  KEY `advance_id_adv_process_hist_idx` (`advance_detail_id`),
  KEY `voucher_status_adv_process_hist_idx` (`voucher_status_id`),
  KEY `process_by_adv_process_hist_idx` (`processed_by`),
  CONSTRAINT `advance_id_adv_process_hist` FOREIGN KEY (`advance_detail_id`) REFERENCES `advance_details` (`advance_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `process_by_adv_process_hist` FOREIGN KEY (`processed_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_adv_process_hist` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_process_history`
--

LOCK TABLES `advance_process_history` WRITE;
/*!40000 ALTER TABLE `advance_process_history` DISABLE KEYS */;
INSERT INTO `advance_process_history` VALUES (1,22,1,1,'2017-06-19 10:34:34',NULL),(2,22,2,1,NULL,NULL),(3,22,3,19,NULL,'ADFA'),(4,23,1,1,'2017-06-19 10:46:16',NULL),(5,24,1,1,'2017-06-19 10:57:46',NULL),(6,24,2,1,NULL,NULL),(7,24,3,19,NULL,'asdfas'),(8,25,2,1,'2017-06-19 18:19:01',NULL),(9,25,12,19,NULL,''),(10,25,22,4,NULL,''),(11,25,112,8,NULL,''),(12,25,122,2,NULL,''),(13,25,132,3,NULL,''),(14,26,2,1,'2017-06-23 19:50:54',NULL),(15,27,2,1,'2017-06-23 19:51:03',NULL),(16,28,2,1,'2017-06-23 19:51:11',NULL),(17,29,2,1,'2017-06-23 19:51:23',NULL),(18,26,12,19,NULL,''),(19,27,12,19,NULL,''),(20,28,12,19,NULL,''),(21,29,12,19,NULL,''),(22,26,22,4,NULL,''),(23,27,22,4,NULL,''),(24,29,22,4,NULL,''),(25,28,22,4,NULL,''),(26,26,112,8,NULL,''),(27,27,112,8,NULL,''),(28,29,112,8,NULL,''),(29,28,112,8,NULL,''),(30,26,122,2,NULL,''),(31,27,122,2,NULL,''),(32,29,122,2,NULL,''),(33,28,122,2,NULL,''),(34,26,132,3,NULL,''),(35,27,132,3,NULL,''),(36,28,132,3,NULL,''),(37,29,132,3,NULL,''),(38,22,1,1,NULL,NULL),(39,26,5,1,'2017-08-07 21:46:55',NULL);
/*!40000 ALTER TABLE `advance_process_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advance_process_instance`
--

DROP TABLE IF EXISTS `advance_process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advance_process_instance` (
  `process_instance_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `advance_detail_id` bigint(10) NOT NULL,
  `voucher_status_id` int(5) NOT NULL,
  `pending_at` bigint(10) DEFAULT NULL,
  `processed_by` bigint(10) DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`process_instance_id`),
  KEY `adv_id_adv_process_inst_idx` (`advance_detail_id`),
  KEY `voucher_status_adv_process_inst_idx` (`voucher_status_id`),
  KEY `pending_at_adv_process_inst_idx` (`pending_at`),
  KEY `approved_by_adv_process_inst_idx` (`processed_by`),
  CONSTRAINT `adv_id_adv_process_inst` FOREIGN KEY (`advance_detail_id`) REFERENCES `advance_details` (`advance_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `approved_by_adv_process_inst` FOREIGN KEY (`processed_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pending_at_adv_process_inst` FOREIGN KEY (`pending_at`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_adv_process_inst` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_process_instance`
--

LOCK TABLES `advance_process_instance` WRITE;
/*!40000 ALTER TABLE `advance_process_instance` DISABLE KEYS */;
INSERT INTO `advance_process_instance` VALUES (1,22,13,1,19,'asdfa'),(2,24,13,1,19,'asdfas'),(3,25,4,1,3,''),(4,26,5,NULL,1,''),(5,27,4,1,3,''),(6,28,4,1,3,''),(7,29,4,1,3,'');
/*!40000 ALTER TABLE `advance_process_instance` ENABLE KEYS */;
UNLOCK TABLES;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_details`
--

LOCK TABLES `branch_details` WRITE;
/*!40000 ALTER TABLE `branch_details` DISABLE KEYS */;
INSERT INTO `branch_details` VALUES (1,'Vashi','Vashi',1,1,NULL,1,'2017-08-13 17:39:38','Y'),(2,'Pune','Pune',1,1,'2017-01-01 19:48:35',1,'2017-08-13 17:39:48','Y'),(3,'Banglore','Banglore',1,1,'2017-01-01 19:51:18',1,'2017-08-13 17:39:58','Y'),(4,'Churchgate','Churchgate',4,1,'2017-01-01 18:33:49',1,'2017-01-01 18:42:52','Y');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_details`
--

LOCK TABLES `company_details` WRITE;
/*!40000 ALTER TABLE `company_details` DISABLE KEYS */;
INSERT INTO `company_details` VALUES (1,'CJ','C&J company',NULL,NULL,NULL,NULL,'Y'),(2,'India','India',NULL,NULL,1,'2017-05-14 16:21:05','Y'),(3,'Oil Pvt Ldt','Oil Pvt Ldt',NULL,NULL,1,'2017-05-21 13:30:09','Y'),(4,'HDFC Red','HDFC Red',1,'2017-05-14 16:21:35',1,'2017-08-15 20:51:05','Y');
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
INSERT INTO `department_head` VALUES (1,48,1,1,NULL,NULL,1,'2017-01-01 19:29:12','Y'),(2,43,1,1,1,'2017-01-01 10:17:34',NULL,NULL,'Y'),(3,47,3,4,1,'2017-01-01 10:18:13',NULL,NULL,'Y'),(4,48,1,3,1,'2017-01-01 10:18:41',NULL,NULL,'Y'),(5,1,1,4,1,'2017-01-01 10:19:01',NULL,NULL,'Y'),(6,48,1,4,1,'2017-01-01 10:23:43',NULL,NULL,'Y'),(7,55,1,3,1,'2017-01-01 23:10:24',1,'2017-01-01 11:48:44','Y'),(8,43,1,4,1,'2017-01-01 09:09:49',NULL,NULL,'Y'),(9,48,3,2,1,'2017-01-01 10:17:28',NULL,NULL,'Y');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_details`
--

LOCK TABLES `employee_details` WRITE;
/*!40000 ALTER TABLE `employee_details` DISABLE KEYS */;
INSERT INTO `employee_details` VALUES (1,1,1,19,'Chaitanya',NULL,'Dalvi','chaitanya@gmail.com','M',NULL,NULL,1,'2017-05-14 18:04:05','Y'),(2,1,1,1,'Juilee',NULL,'Mathkar','juilee@gmail.com','F',NULL,NULL,1,'2017-01-01 11:59:35','Y'),(3,1,47,1,'Vinod',NULL,'Hollar','vinod@gmail.com','M',NULL,NULL,1,'2017-01-01 21:06:35','Y'),(4,1,43,1,'heena',NULL,'dere','heena@gmail.com','F',NULL,NULL,1,'2017-04-29 07:56:05','Y'),(5,4,1,5,'Amit',NULL,'AMit','amit@hdfcred.z','M',NULL,'2017-01-01 20:16:42',1,NULL,'Y'),(6,3,43,2,'Rajiv',NULL,'1111','rajiv@gmail.com','M',NULL,'2017-02-26 11:55:54',1,'2017-01-01 11:55:54','Y'),(7,1,1,1,'sham',NULL,'bhatkar','sham@yahoo.com','M',NULL,'2017-04-29 10:52:31',1,NULL,'Y'),(8,1,43,2,'Anil',NULL,'Rathod','anil@gmail.com','M',NULL,'2017-04-29 10:59:15',1,'2017-05-19 20:35:38','Y'),(10,1,1,1,'pavan',NULL,'bhor','pavan.bhor@gmail.com','M',NULL,'2017-04-29 16:10:49',1,NULL,'Y'),(11,1,1,1,'Jayesh',NULL,'Gawali','Jayesh.gawali@gmail.com','M',NULL,'2017-04-29 17:40:19',1,NULL,'Y'),(12,1,1,1,'ashutoshh',NULL,'GAandage','ashutosh@gmail.com','M',NULL,'2017-04-29 17:42:15',1,NULL,'Y'),(15,1,1,1,'suraj',NULL,'abc','suraj@gmail.com','M',NULL,'2017-04-29 17:52:54',1,NULL,'Y'),(17,1,1,1,'Sandeep',NULL,'sakpal','sandeep@gmail.com','M',NULL,'2017-04-29 17:59:15',1,NULL,'Y'),(19,1,1,1,'kuastubh',NULL,'dalvi','kaustubh@gmail.com','M',NULL,'2017-04-29 18:23:37',1,NULL,'Y');
/*!40000 ALTER TABLE `employee_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_details`
--

DROP TABLE IF EXISTS `event_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event_details` (
  `event_id` int(11) NOT NULL AUTO_INCREMENT,
  `event_code` varchar(45) NOT NULL,
  `event_name` varchar(45) NOT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_code_UNIQUE` (`event_code`),
  UNIQUE KEY `event_name_UNIQUE` (`event_name`),
  KEY `created_by_event_idx` (`created_by`),
  KEY `modified_by_event_idx` (`modified_by`),
  KEY `branch_id_event_idx` (`branch_id`),
  CONSTRAINT `branch_id_event` FOREIGN KEY (`branch_id`) REFERENCES `branch_details` (`branch_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_event` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_event` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_details`
--

LOCK TABLES `event_details` WRITE;
/*!40000 ALTER TABLE `event_details` DISABLE KEYS */;
INSERT INTO `event_details` VALUES (5,'Anual Meeting','Annual Meeting',1,1,'2017-05-20 14:05:20',NULL,NULL,'Y'),(6,'Appraisale Meeting','Appraisal Meeting',1,1,'2017-05-21 12:13:24',NULL,NULL,'Y');
/*!40000 ALTER TABLE `event_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense_category`
--

DROP TABLE IF EXISTS `expense_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense_category` (
  `expense_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `expense_name` varchar(45) NOT NULL,
  `company_id` int(11) NOT NULL,
  `gl_code` varchar(45) DEFAULT NULL,
  `location_required` char(1) NOT NULL,
  `unit_required` char(1) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `limit_increase` char(1) DEFAULT NULL,
  `created_by` bigint(11) DEFAULT NULL,
  `created_date` varchar(45) DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` varchar(45) DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`expense_category_id`),
  UNIQUE KEY `category_name_UNIQUE` (`expense_name`),
  KEY `created_by_exp_cat_idx` (`created_by`),
  KEY `modified_by_exp_cat_idx` (`modified_by`),
  KEY `company_id_exp_cat_idx` (`company_id`),
  CONSTRAINT `company_id_exp_cat` FOREIGN KEY (`company_id`) REFERENCES `company_details` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_exp_cat` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_exp_cat` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_category`
--

LOCK TABLES `expense_category` WRITE;
/*!40000 ALTER TABLE `expense_category` DISABLE KEYS */;
INSERT INTO `expense_category` VALUES (1,'Meal',1,'A54545','N','Y',50,'N',1,'2017-04-08 10:42:59',1,'2017-04-08 19:46:24','Y'),(2,'Bus',1,'A5673','Y','N',NULL,'N',1,'2017-04-08 10:48:36',NULL,NULL,'Y'),(3,'Railways',1,'A5446','Y','N',200,'Y',1,'2017-04-08 10:50:41',1,'2017-08-13 17:10:03','Y'),(4,'Two wheeler',1,'A5445','Y','Y',100,'N',1,'2017-08-13 15:12:56',NULL,NULL,'Y'),(5,'Four Wheeler',1,'A7555','Y','Y',500,'N',1,'2017-08-13 15:15:12',NULL,NULL,'Y'),(18,'Ac CAb',1,'A400','Y','N',500,'N',NULL,'2017-08-13 17:46:00.159',1,'2017-08-13 17:46:00','Y'),(19,'Ola',1,'A8000','N','N',500,'N',NULL,'2017-08-13 18:24:26.511',1,'2017-08-13 18:24:26','Y');
/*!40000 ALTER TABLE `expense_category` ENABLE KEYS */;
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
  `expense_category_id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `from_location` varchar(45) DEFAULT NULL,
  `to_location` varchar(45) DEFAULT NULL,
  `description` varchar(45) NOT NULL,
  `unit` int(5) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `file_Name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`expense_detail_id`),
  KEY `exp_category_exp_detail_idx` (`expense_category_id`),
  KEY `exp_header_exp_detail_idx` (`expense_header_id`),
  CONSTRAINT `exp_category_exp_detail` FOREIGN KEY (`expense_category_id`) REFERENCES `expense_category` (`expense_category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `exp_header_exp_detail` FOREIGN KEY (`expense_header_id`) REFERENCES `expense_header` (`expense_header_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_details`
--

LOCK TABLES `expense_details` WRITE;
/*!40000 ALTER TABLE `expense_details` DISABLE KEYS */;
INSERT INTO `expense_details` VALUES (11,43,1,'2017-06-29','','','33',NULL,33,NULL),(12,45,1,'2017-06-30','','','ASDF',3,333,NULL),(14,47,1,'2017-06-30','','','33',3,33,NULL),(15,48,1,'2017-07-19','','','kajskdfj',6,300,NULL),(16,48,3,'2017-07-19','sjdfkj','kjdsfsk','kljsdlkf',NULL,50,NULL);
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
  `expense_type` varchar(20) NOT NULL,
  `voucher_number` varchar(50) DEFAULT NULL,
  `employee_id` bigint(10) NOT NULL,
  `advance_id` bigint(10) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `voucher_status` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `purpose` varchar(100) NOT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`expense_header_id`),
  KEY `emp_id_expe_header_idx` (`employee_id`),
  KEY `voucher_status_exp_header_idx` (`voucher_status`),
  KEY `advance_id_exp_header_idx` (`advance_id`),
  KEY `event_id_exp_header_idx` (`event_id`),
  CONSTRAINT `advance_id_exp_header` FOREIGN KEY (`advance_id`) REFERENCES `advance_details` (`advance_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_exp_header` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `emp_id_expe_header` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `event_id_exp_header` FOREIGN KEY (`event_id`) REFERENCES `event_details` (`event_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_exp_header` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_exp_header` FOREIGN KEY (`voucher_status`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_header`
--

LOCK TABLES `expense_header` WRITE;
/*!40000 ALTER TABLE `expense_header` DISABLE KEYS */;
INSERT INTO `expense_header` VALUES (43,'EmployeeExpense','Voucher/29-June-2017-29-June-2017/18',1,25,NULL,132,'2017-06-29','2017-06-29','asdfsd',1,'2017-06-29 15:44:21',NULL,NULL),(45,'EventExpense','Voucher/30-June-2017-30-June-2017/19',1,NULL,5,132,'2017-06-30','2017-06-30','asdAASDF',1,'2017-06-30 11:19:18',NULL,NULL),(47,'EmployeeExpense','Voucher/30-June-2017-30-June-2017/20',1,NULL,NULL,132,'2017-06-30','2017-06-30','safds',1,'2017-06-30 11:45:27',NULL,NULL),(48,'EmployeeExpense',NULL,1,NULL,NULL,1,'2017-07-19','2017-07-19','Client Meetin',NULL,NULL,1,'2017-07-19 21:42:35');
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
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functional_flow`
--

LOCK TABLES `functional_flow` WRITE;
/*!40000 ALTER TABLE `functional_flow` DISABLE KEYS */;
INSERT INTO `functional_flow` VALUES (1,'N',1,1,2,-10,-1,NULL,NULL,NULL,NULL,NULL,'Y'),(2,'N',1,43,2,-10,2,NULL,1,'2017-01-01 22:19:19',NULL,NULL,'N'),(3,'N',1,48,2,-10,-11,NULL,1,'2017-01-01 20:58:45',NULL,NULL,'N'),(4,'N',1,50,2,-10,-11,NULL,1,'2017-01-01 21:00:59',NULL,NULL,'Y'),(5,'N',1,54,3,-10,-11,-1,1,'2017-01-01 21:02:20',NULL,NULL,'Y'),(6,'N',1,58,1,-10,NULL,NULL,1,'2017-01-01 21:27:37',NULL,NULL,'Y'),(7,'N',1,44,1,-11,NULL,NULL,1,'2017-01-01 21:28:19',NULL,NULL,'Y'),(8,'N',1,50,1,-10,NULL,NULL,1,'2017-01-01 21:30:49',NULL,NULL,'Y'),(9,'N',1,55,1,-11,NULL,NULL,1,'2017-01-01 21:31:55',NULL,NULL,'Y'),(10,'N',1,56,1,-10,NULL,NULL,1,'2017-01-01 21:34:00',NULL,NULL,'Y'),(11,'N',1,53,1,-10,NULL,NULL,1,'2017-01-01 21:35:25',NULL,NULL,'Y'),(12,'N',1,52,1,-10,NULL,NULL,1,'2017-01-01 21:37:41',NULL,NULL,'Y'),(13,'N',1,50,1,-10,NULL,NULL,1,'2017-01-01 21:39:17',NULL,NULL,'Y'),(14,'N',1,52,1,-10,NULL,NULL,1,'2017-01-01 21:41:38',NULL,NULL,'Y'),(15,'N',3,43,1,-10,NULL,NULL,1,'2017-01-01 21:42:43',NULL,NULL,'Y'),(17,'Y',1,NULL,2,-11,-10,NULL,1,'2017-01-01 17:22:42',NULL,NULL,'Y'),(24,'N',1,NULL,2,-10,-1,NULL,1,'2017-04-15 10:04:12',NULL,NULL,'N'),(25,'N',1,NULL,3,8,2,3,1,'2017-05-07 19:57:11',NULL,NULL,'Y'),(26,'N',1,48,1,-10,NULL,NULL,1,'2017-08-13 11:47:59',NULL,NULL,'Y');
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
INSERT INTO `levels` VALUES (-11,'Reporting Manager\'s RM'),(-10,'Reporting Manager'),(-1,'Department Head');
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_details`
--

LOCK TABLES `login_details` WRITE;
/*!40000 ALTER TABLE `login_details` DISABLE KEYS */;
INSERT INTO `login_details` VALUES (1,'chaitanya@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',1),(2,'juilee@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',2),(3,'vinod@gmai.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',3),(4,'heena@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',4),(5,'amit@hdfcred.z','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',5),(6,'rajiv@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',6),(7,'sham@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',7),(8,'anil@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',8),(10,'pavan.bhor@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',10),(11,'Jayesh.gawali@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',11),(12,'ashutosh@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',12),(15,'Suraj@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',15),(17,'sandeep@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',17),(19,'kaustubh@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',19);
/*!40000 ALTER TABLE `login_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_details`
--

DROP TABLE IF EXISTS `payment_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_details` (
  `payment_detail_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(45) NOT NULL,
  `voucher_id` bigint(10) NOT NULL,
  `paid_by` bigint(10) NOT NULL,
  `amount` double NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`payment_detail_id`),
  KEY `paid_by_payment_idx` (`paid_by`),
  CONSTRAINT `paid_by_payment` FOREIGN KEY (`paid_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
INSERT INTO `payment_details` VALUES (1,'Expense',47,1,33,'2017-07-30 19:22:54'),(2,'Expense',47,1,33,'2017-07-30 21:11:22'),(3,'Expense',47,1,33,'2017-07-30 21:12:04'),(4,'Expense',47,1,33,'2017-07-30 21:13:10'),(9,'Expense',47,1,33,'2017-07-30 21:55:40'),(10,'Expense',47,1,33,'2017-07-30 21:57:05'),(13,'Expense',45,1,333,'2017-08-05 19:50:16'),(15,'Expense',47,1,33,'2017-08-05 19:59:58'),(16,'Expense',45,1,333,'2017-08-05 20:07:29'),(17,'Expense',43,1,3400,'2017-08-07 20:16:55'),(21,'Advance',26,1,2000,'2017-08-07 21:46:55');
/*!40000 ALTER TABLE `payment_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_history`
--

DROP TABLE IF EXISTS `process_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_history` (
  `process_history_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `expense_header_id` bigint(10) NOT NULL,
  `voucher_status_id` int(11) DEFAULT NULL,
  `processed_by` bigint(10) DEFAULT NULL COMMENT 'Approved/rejected by ',
  `processed_date` datetime DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`process_history_id`),
  KEY `process_inst_id_process_hist_idx` (`expense_header_id`),
  KEY `voucher_status_process_hist_idx` (`voucher_status_id`),
  KEY `process_by_process_hist_idx` (`processed_by`),
  CONSTRAINT `expense_header_process_hist` FOREIGN KEY (`expense_header_id`) REFERENCES `expense_header` (`expense_header_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `process_by_process_hist` FOREIGN KEY (`processed_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_process_hist` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_history`
--

LOCK TABLES `process_history` WRITE;
/*!40000 ALTER TABLE `process_history` DISABLE KEYS */;
INSERT INTO `process_history` VALUES (8,43,2,1,'2017-06-29 15:44:21',NULL),(9,45,2,1,'2017-06-30 11:19:18',NULL),(11,47,2,1,'2017-06-30 11:45:27',NULL),(12,45,12,19,NULL,NULL),(13,43,12,19,NULL,NULL),(14,47,12,19,NULL,NULL),(15,45,22,4,NULL,NULL),(16,43,22,4,NULL,NULL),(17,47,22,4,NULL,NULL),(18,45,112,8,NULL,NULL),(19,43,112,8,NULL,NULL),(20,47,112,8,NULL,NULL),(21,45,122,2,NULL,NULL),(22,43,122,2,NULL,NULL),(23,47,122,2,NULL,NULL),(24,45,132,3,NULL,NULL),(25,43,132,3,NULL,NULL),(26,47,132,3,NULL,NULL),(27,48,1,1,'2017-07-19 21:42:11',NULL),(28,48,1,1,NULL,NULL),(29,47,5,1,'2017-08-05 19:59:58',NULL),(30,45,5,1,'2017-08-05 20:07:29',NULL),(31,43,5,1,'2017-08-07 20:16:55',NULL);
/*!40000 ALTER TABLE `process_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_instance`
--

DROP TABLE IF EXISTS `process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_instance` (
  `process_instance_id` bigint(10) NOT NULL AUTO_INCREMENT,
  `expense_header_id` bigint(10) NOT NULL,
  `voucher_status_id` int(5) NOT NULL,
  `pending_at` bigint(10) DEFAULT NULL,
  `processed_by` bigint(10) DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`process_instance_id`),
  KEY `pending_at_process_inst_idx` (`pending_at`),
  KEY `voucher_status_process_inst_idx` (`voucher_status_id`),
  KEY `exp_header_process_inst_idx` (`expense_header_id`),
  KEY `processed_by_process_inst_idx` (`processed_by`),
  CONSTRAINT `exp_header_process_inst` FOREIGN KEY (`expense_header_id`) REFERENCES `expense_header` (`expense_header_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pending_at_process_inst` FOREIGN KEY (`pending_at`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `processed_by_process_inst` FOREIGN KEY (`processed_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_process_inst` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_instance`
--

LOCK TABLES `process_instance` WRITE;
/*!40000 ALTER TABLE `process_instance` DISABLE KEYS */;
INSERT INTO `process_instance` VALUES (1,43,5,NULL,1,''),(2,45,5,NULL,1,''),(3,47,5,NULL,1,'');
/*!40000 ALTER TABLE `process_instance` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ADMIN_ROLE',1),(2,'SUPER_ADMIN',1),(3,'ADMIN_ROLE',5),(4,'SUPER_ADMIN',5);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_identification`
--

DROP TABLE IF EXISTS `voucher_identification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher_identification` (
  `module` varchar(20) NOT NULL,
  `voucher_number` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`module`,`voucher_number`),
  KEY `company_id_voucher_identfy_idx` (`company_id`),
  CONSTRAINT `company_id_voucher_identfy` FOREIGN KEY (`company_id`) REFERENCES `company_details` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_identification`
--

LOCK TABLES `voucher_identification` WRITE;
/*!40000 ALTER TABLE `voucher_identification` DISABLE KEYS */;
INSERT INTO `voucher_identification` VALUES ('ADVANCE_EXPENSE',14,1),('EMPLOYEE_EXPENSE',20,1);
/*!40000 ALTER TABLE `voucher_identification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher_status`
--

DROP TABLE IF EXISTS `voucher_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voucher_status` (
  `voucher_status_id` int(11) NOT NULL,
  `voucher_status` varchar(45) NOT NULL,
  `text_to_display` varchar(45) NOT NULL,
  `next_status_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`voucher_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_status`
--

LOCK TABLES `voucher_status` WRITE;
/*!40000 ALTER TABLE `voucher_status` DISABLE KEYS */;
INSERT INTO `voucher_status` VALUES (1,'Save As Draft','Save As Draft',NULL),(2,'Send For Approval','Send For Approval',NULL),(3,'Rejected','Rejected',NULL),(4,'Completely Approved','Completely Approved',NULL),(5,'Paid','Paid',NULL),(11,'Pending At 1st Level','Pending At 1st Level',NULL),(12,'Approved At 1st Level','Approved At 1st Level',NULL),(13,'Rejected At 1st Level','Rejected At 1st Level',NULL),(21,'Pending At 2nd Level','Pending At 2nd Level',NULL),(22,'Approved At 2nd Level','Approved At 2nd Level',NULL),(23,'Rejected At 2nd Level','Rejected At 2nd Level',NULL),(31,'Pending At 3rd Level','Pending At 3rd Level',NULL),(32,'Approved At 3rd Level','Approved At 3rd Level',NULL),(33,'Rejected At 3rd Level','Rejected At 3rd Level',NULL),(111,'Fin Pending At 1st Level','Fin Pending At 1st Level',NULL),(112,'Fin Appoved At 1st Level','Fin Appoved At 1st Level',NULL),(113,'Fin Rejected At 1st Level','Fin Rejected At 1st Level',NULL),(121,'Fin Pending At 2nd Level','Fin Pending At 2nd Level',NULL),(122,'Fin Appoved At 2nd Level','Fin Appoved At 2nd Level',NULL),(123,'Fin Rejected At 2nd Level','Fin Rejected At 2nd Level',NULL),(131,'Fin Pending At 3rd Level','Fin Pending At 3rd Level',NULL),(132,'Fin Appoved At 3rd Level','Fin Appoved At 3rd Level',NULL),(133,'Fin Rejected At 3rd Level','Fin Rejected At 3rd Level',NULL);
/*!40000 ALTER TABLE `voucher_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'test'
--
/*!50003 DROP PROCEDURE IF EXISTS `voucher_number` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `voucher_number`(
IN moduleName varchar(20), 
IN companyId int,
OUT voucherNumber varchar(30)
)
BEGIN
    DECLARE number int;
    
    set @number=(select voucher_number from voucher_identification where module like moduleName and company_id=companyId);

	SELECT @number;
	if(@number is null) then
        set @number=1;
		insert into voucher_identification values(moduleName,@number,companyId);
	else
        set @number=(@number+1);
		update voucher_identification set voucher_number=@number where module= moduleName and company_id=companyId;
	end if;
    
    set voucherNumber = @number;
    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-15 21:09:56
