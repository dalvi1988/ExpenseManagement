CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;
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
  `date` datetime DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_details`
--

LOCK TABLES `advance_details` WRITE;
/*!40000 ALTER TABLE `advance_details` DISABLE KEYS */;
INSERT INTO `advance_details` VALUES (1,0,NULL,545,'sdfgsdf','Y',5,NULL,'2017-05-26 12:36:24',1,'2017-05-26 12:36:24',NULL,NULL),(2,0,NULL,2423,'asdfsad','Y',5,NULL,'2017-05-26 12:36:58',1,'2017-05-26 12:36:58',NULL,NULL),(3,0,NULL,334,'sfasd','Y',6,1,'2017-05-26 12:41:03',1,'2017-05-26 12:41:03',NULL,NULL),(4,0,NULL,334,'sfasd','N',NULL,1,'2017-05-26 12:41:36',1,'2017-05-26 12:41:36',NULL,NULL),(12,1,NULL,45,'jksjfdlas','N',NULL,2,'2017-05-29 13:53:36',1,'2017-05-29 13:53:36',NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_process_history`
--

LOCK TABLES `advance_process_history` WRITE;
/*!40000 ALTER TABLE `advance_process_history` DISABLE KEYS */;
INSERT INTO `advance_process_history` VALUES (37,12,2,1,'2017-05-29 13:53:36',NULL);
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
  `pending_at` bigint(10) NOT NULL,
  `approved_by` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`process_instance_id`),
  KEY `adv_id_adv_process_inst_idx` (`advance_detail_id`),
  KEY `voucher_status_adv_process_inst_idx` (`voucher_status_id`),
  KEY `pending_at_adv_process_inst_idx` (`pending_at`),
  KEY `approved_by_adv_process_inst_idx` (`approved_by`),
  CONSTRAINT `adv_id_adv_process_inst` FOREIGN KEY (`advance_detail_id`) REFERENCES `advance_details` (`advance_details_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `approved_by_adv_process_inst` FOREIGN KEY (`approved_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pending_at_adv_process_inst` FOREIGN KEY (`pending_at`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_adv_process_inst` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_process_instance`
--

LOCK TABLES `advance_process_instance` WRITE;
/*!40000 ALTER TABLE `advance_process_instance` DISABLE KEYS */;
INSERT INTO `advance_process_instance` VALUES (1,12,11,19,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_details`
--

LOCK TABLES `branch_details` WRITE;
/*!40000 ALTER TABLE `branch_details` DISABLE KEYS */;
INSERT INTO `branch_details` VALUES (1,'branch code1','brach code1',1,1,NULL,1,'2017-04-23 00:15:05','Y'),(2,'brach code2','brach code2',1,1,'2017-01-01 19:48:35',1,'2017-04-22 21:38:11','Y'),(3,'brach code3','brach code3',1,1,'2017-01-01 19:51:18',1,'2017-05-21 13:29:41','Y'),(4,'branch code 4','branch code 4',2,1,'2017-01-01 18:33:49',1,'2017-01-01 18:42:52','Y');
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
INSERT INTO `company_details` VALUES (1,'CJ','C&J company',NULL,NULL,NULL,NULL,'Y'),(2,'India','India',NULL,NULL,1,'2017-05-14 16:21:05','Y'),(3,'Oil Pvt Ldt','Oil Pvt Ldt',NULL,NULL,1,'2017-05-21 13:30:09','Y'),(4,'Infosys','Infosys',1,'2017-05-14 16:21:35',1,'2017-05-21 13:30:14','Y');
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
INSERT INTO `department_head` VALUES (1,48,1,1,NULL,NULL,1,'2017-01-01 19:29:12','Y'),(2,43,1,1,1,'2017-01-01 10:17:34',NULL,NULL,'Y'),(3,47,3,4,1,'2017-01-01 10:18:13',NULL,NULL,'Y'),(4,48,1,3,1,'2017-01-01 10:18:41',NULL,NULL,'Y'),(5,1,1,4,1,'2017-01-01 10:19:01',NULL,NULL,'Y'),(6,48,1,4,1,'2017-01-01 10:23:43',NULL,NULL,'Y'),(7,55,1,3,1,'2017-01-01 23:10:24',1,'2017-01-01 11:48:44','Y'),(8,43,1,4,1,'2017-01-01 09:09:49',NULL,NULL,'Y'),(9,48,3,2,1,'2017-01-01 10:17:28',NULL,NULL,'Y'),(10,43,4,1,1,'2017-01-01 11:49:07',NULL,NULL,'Y');
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
INSERT INTO `employee_details` VALUES (1,1,1,19,'Chaitanya',NULL,'Dalvi','chaitanya@gmail.com','M',NULL,NULL,1,'2017-05-14 18:04:05','Y'),(2,1,1,1,'Juilee',NULL,'Mathkar','juilee@gmail.com','F',NULL,NULL,1,'2017-01-01 11:59:35','Y'),(3,1,47,1,'Vinod',NULL,'Hollar','vinod@gmail.com','M',NULL,NULL,1,'2017-01-01 21:06:35','Y'),(4,1,43,1,'heena',NULL,'dere','heena@gmail.com','F',NULL,NULL,1,'2017-04-29 07:56:05','Y'),(5,4,1,1,'Amol',NULL,'jfj@3345','amol@gmail.com','M',NULL,'2017-01-01 20:16:42',1,NULL,'Y'),(6,3,43,2,'Rajiv',NULL,'1111','rajiv@gmail.com','M',NULL,'2017-02-26 11:55:54',1,'2017-01-01 11:55:54','Y'),(7,1,1,1,'sham',NULL,'bhatkar','sham@yahoo.com','M',NULL,'2017-04-29 10:52:31',1,NULL,'Y'),(8,1,43,2,'Anil',NULL,'Rathod','anil@gmail.com','M',NULL,'2017-04-29 10:59:15',1,'2017-05-19 20:35:38','Y'),(10,1,1,1,'pavan',NULL,'bhor','pavan.bhor@gmail.com','M',NULL,'2017-04-29 16:10:49',1,NULL,'Y'),(11,1,1,1,'Jayesh',NULL,'Gawali','Jayesh.gawali@gmail.com','M',NULL,'2017-04-29 17:40:19',1,NULL,'Y'),(12,1,1,1,'ashutoshh',NULL,'GAandage','ashutosh@gmail.com','M',NULL,'2017-04-29 17:42:15',1,NULL,'Y'),(15,1,1,1,'suraj',NULL,'abc','suraj@gmail.com','M',NULL,'2017-04-29 17:52:54',1,NULL,'Y'),(17,1,1,1,'Sandeep',NULL,'sakpal','sandeep@gmail.com','M',NULL,'2017-04-29 17:59:15',1,NULL,'Y'),(19,1,1,1,'kuastubh',NULL,'dalvi','kaustubh@gmail.com','M',NULL,'2017-04-29 18:23:37',1,NULL,'Y');
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
  `gl_code` varchar(45) DEFAULT NULL,
  `location_required` char(1) NOT NULL,
  `unit_required` char(1) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `created_by` bigint(11) DEFAULT NULL,
  `created_date` varchar(45) DEFAULT NULL,
  `modified_by` bigint(11) DEFAULT NULL,
  `modified_date` varchar(45) DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`expense_category_id`),
  UNIQUE KEY `category_name_UNIQUE` (`expense_name`),
  KEY `created_by_exp_cat_idx` (`created_by`),
  KEY `modified_by_exp_cat_idx` (`modified_by`),
  CONSTRAINT `created_by_exp_cat` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_exp_cat` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_category`
--

LOCK TABLES `expense_category` WRITE;
/*!40000 ALTER TABLE `expense_category` DISABLE KEYS */;
INSERT INTO `expense_category` VALUES (1,'Meal','A54545','N','Y',50,1,'2017-04-08 10:42:59',1,'2017-04-08 19:46:24','Y'),(2,'Bus','A5673','Y','N',NULL,1,'2017-04-08 10:48:36',NULL,NULL,'Y'),(3,'Railways','A5446','Y','N',NULL,1,'2017-04-08 10:50:41',1,'2017-04-08 11:02:30','Y');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_details`
--

LOCK TABLES `expense_details` WRITE;
/*!40000 ALTER TABLE `expense_details` DISABLE KEYS */;
INSERT INTO `expense_details` VALUES (1,28,1,'2017-05-02','','','meeting',20,50,'avatar5.png'),(5,32,2,'2017-05-02','office','vashi','Office Meeting',NULL,120,NULL),(6,33,1,'2017-05-03','','','Meting',4,500,NULL),(7,34,2,'2017-05-19','sdf','afsdf','dsaf',NULL,500,NULL),(8,35,1,'2017-05-29','','','asf',33,2432,NULL);
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
  `voucher_number` varchar(50) DEFAULT NULL,
  `employee_id` bigint(10) NOT NULL,
  `voucher_status` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `title` varchar(45) NOT NULL,
  `purpose` varchar(100) NOT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`expense_header_id`),
  KEY `emp_id_expe_header_idx` (`employee_id`),
  KEY `voucher_status_exp_header_idx` (`voucher_status`),
  CONSTRAINT `created_by_exp_header` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `emp_id_expe_header` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_exp_header` FOREIGN KEY (`employee_id`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_exp_header` FOREIGN KEY (`voucher_status`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_header`
--

LOCK TABLES `expense_header` WRITE;
/*!40000 ALTER TABLE `expense_header` DISABLE KEYS */;
INSERT INTO `expense_header` VALUES (28,'Voucher/02-May-2017-02-May-2017/16',1,23,'2017-05-02','2017-05-02','Test11','Test1',NULL,NULL,NULL,NULL),(32,'Voucher/02-May-2017-02-May-2017/17',1,123,'2017-05-02','2017-05-02','DAily Expense','Daily Expense',NULL,NULL,NULL,NULL),(33,NULL,1,1,'2017-05-02','2017-05-03','Test2','dTesd',NULL,NULL,NULL,NULL),(34,NULL,1,1,'2017-05-19','2017-05-19','testing','ateadfa',NULL,NULL,NULL,NULL),(35,NULL,1,1,'2017-05-29','2017-05-29','Tessting','testing',1,'2017-05-29 11:39:30',NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functional_flow`
--

LOCK TABLES `functional_flow` WRITE;
/*!40000 ALTER TABLE `functional_flow` DISABLE KEYS */;
INSERT INTO `functional_flow` VALUES (1,'N',1,1,2,-10,-1,NULL,NULL,NULL,NULL,NULL,'Y'),(2,'N',1,43,2,-10,4,NULL,1,'2017-01-01 22:19:19',NULL,NULL,'N'),(3,'N',1,48,2,-10,-11,NULL,1,'2017-01-01 20:58:45',NULL,NULL,'N'),(4,'N',1,50,2,-10,-11,NULL,1,'2017-01-01 21:00:59',NULL,NULL,'Y'),(5,'N',1,54,3,-10,-11,-1,1,'2017-01-01 21:02:20',NULL,NULL,'Y'),(6,'N',1,58,1,-10,NULL,NULL,1,'2017-01-01 21:27:37',NULL,NULL,'Y'),(7,'N',1,44,1,-11,NULL,NULL,1,'2017-01-01 21:28:19',NULL,NULL,'Y'),(8,'N',1,50,1,-10,NULL,NULL,1,'2017-01-01 21:30:49',NULL,NULL,'Y'),(9,'N',1,55,1,-11,NULL,NULL,1,'2017-01-01 21:31:55',NULL,NULL,'Y'),(10,'N',1,56,1,-10,NULL,NULL,1,'2017-01-01 21:34:00',NULL,NULL,'Y'),(11,'N',1,53,1,-10,NULL,NULL,1,'2017-01-01 21:35:25',NULL,NULL,'Y'),(12,'N',1,52,1,-10,NULL,NULL,1,'2017-01-01 21:37:41',NULL,NULL,'Y'),(13,'N',1,50,1,-10,NULL,NULL,1,'2017-01-01 21:39:17',NULL,NULL,'Y'),(14,'N',1,52,1,-10,NULL,NULL,1,'2017-01-01 21:41:38',NULL,NULL,'Y'),(15,'N',3,43,1,-10,NULL,NULL,1,'2017-01-01 21:42:43',NULL,NULL,'Y'),(16,'N',4,1,1,-10,NULL,NULL,1,'2017-01-01 21:43:27',NULL,NULL,'Y'),(17,'Y',1,NULL,2,-11,-10,NULL,1,'2017-01-01 17:22:42',NULL,NULL,'Y'),(24,'N',1,NULL,2,-10,-1,NULL,1,'2017-04-15 10:04:12',NULL,NULL,'N'),(25,'N',1,NULL,3,8,2,3,1,'2017-05-07 19:57:11',NULL,NULL,'Y');
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
INSERT INTO `login_details` VALUES (1,'chaitanya@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',1),(2,'juilee@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',2),(3,'vinod@gmai.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',3),(4,'heena@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',4),(5,'amol@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',5),(6,'rajiv@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',6),(7,'sham@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',7),(8,'anil@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',8),(10,'pavan.bhor@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',10),(11,'Jayesh.gawali@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',11),(12,'ashutosh@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',12),(15,'Suraj@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',15),(17,'sandeep@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',17),(19,'kaustubh@gmail.com','$2a$10$/bJ9Q8VDazT5/o1ESKleie9.9bUbrNer68IPx4nuMjV0pC28MpzCi',19);
/*!40000 ALTER TABLE `login_details` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_history`
--

LOCK TABLES `process_history` WRITE;
/*!40000 ALTER TABLE `process_history` DISABLE KEYS */;
INSERT INTO `process_history` VALUES (1,28,2,1,NULL,NULL),(8,28,13,NULL,NULL,NULL),(12,32,2,1,NULL,NULL),(13,32,13,NULL,NULL,NULL),(20,32,23,NULL,NULL,NULL),(22,32,23,NULL,NULL,NULL),(23,32,113,NULL,NULL,NULL),(24,32,123,NULL,NULL,NULL),(26,28,23,NULL,NULL,NULL),(27,28,23,NULL,NULL,NULL),(28,33,1,1,NULL,NULL),(29,34,1,1,NULL,NULL),(30,35,1,1,NULL,NULL);
/*!40000 ALTER TABLE `process_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `process_instance`
--

DROP TABLE IF EXISTS `process_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `process_instance` (
  `process_instance_id` bigint(10) NOT NULL,
  `expense_header_id` bigint(10) NOT NULL,
  `voucher_status_id` int(5) NOT NULL,
  `pending_at` bigint(10) NOT NULL,
  `approved_by` bigint(10) DEFAULT NULL,
  PRIMARY KEY (`process_instance_id`),
  KEY `pending_at_process_inst_idx` (`pending_at`),
  KEY `voucher_status_process_inst_idx` (`voucher_status_id`),
  KEY `exp_header_process_inst_idx` (`expense_header_id`),
  KEY `approved_by_process_inst_idx` (`approved_by`),
  CONSTRAINT `approved_by_process_inst` FOREIGN KEY (`approved_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `exp_header_process_inst` FOREIGN KEY (`expense_header_id`) REFERENCES `expense_header` (`expense_header_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pending_at_process_inst` FOREIGN KEY (`pending_at`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_process_inst` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_instance`
--

LOCK TABLES `process_instance` WRITE;
/*!40000 ALTER TABLE `process_instance` DISABLE KEYS */;
INSERT INTO `process_instance` VALUES (7,28,111,8,3),(9,32,131,3,8);
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,'ADMIN_ROLE',1),(2,'SUPER_ADMIN',1);
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
  PRIMARY KEY (`module`,`voucher_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher_identification`
--

LOCK TABLES `voucher_identification` WRITE;
/*!40000 ALTER TABLE `voucher_identification` DISABLE KEYS */;
INSERT INTO `voucher_identification` VALUES ('EMPLOYEE_EXPENSE',17);
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
INSERT INTO `voucher_status` VALUES (1,'Save As Draft','Send For Approval',NULL),(2,'Send For Approval','Send For Approval',NULL),(3,'Completely Approved','Completely Approved',NULL),(4,'Paid','Paid',NULL),(11,'Pending At 1st Level','Pending At 1st Level',NULL),(12,'Rejected At 1st Level','Rejected At 1st Level',NULL),(13,'Approved At 1st Level','Approved At 1st Level',NULL),(21,'Pending At 2nd Level','Pending At 2nd Level',NULL),(22,'Rejected At 2nd Level','Rejected At 2nd Level',NULL),(23,'Approved At 2nd Level','Approved At 2nd Level',NULL),(31,'Pending At 3rd Level','Pending At 3rd Level',NULL),(32,'Rejected At 3rd Level','Rejected At 3rd Level',NULL),(33,'Approved At 3rd Level','Approved At 3rd Level',NULL),(111,'Fin Pending At 1st Level','Fin Pending At 1st Level',NULL),(112,'Fin Rejected At 1st Level','Fin Rejected At 1st Level',NULL),(113,'Fin Appoved At 1st Level','Fin Appoved At 1st Level',NULL),(121,'Fin Pending At 2nd Level','Fin Pending At 2nd Level',NULL),(122,'Fin Rejected At 2nd Level','Fin Rejected At 2nd Level',NULL),(123,'Fin Appoved At 2nd Level','Fin Appoved At 2nd Level',NULL),(131,'Fin Pending At 3rd Level','Fin Pending At 3rd Level',NULL),(132,'Fin Rejected At 3rd Level','Fin Rejected At 3rd Level',NULL),(133,'Fin Appoved At 3rd Level','Fin Appoved At 3rd Level',NULL);
/*!40000 ALTER TABLE `voucher_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'test'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-30  6:25:21
