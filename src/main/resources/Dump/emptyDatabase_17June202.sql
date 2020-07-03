-- MySQL dump 10.13  Distrib 5.6.19, for Win32 (x86)
--
-- Host: ec2-3-7-207-0.ap-south-1.compute.amazonaws.com    Database: expensewala
-- ------------------------------------------------------
-- Server version	5.6.47

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
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_details`
--

LOCK TABLES `advance_details` WRITE;
/*!40000 ALTER TABLE `advance_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_process_history`
--

LOCK TABLES `advance_process_history` WRITE;
/*!40000 ALTER TABLE `advance_process_history` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advance_process_instance`
--

LOCK TABLES `advance_process_instance` WRITE;
/*!40000 ALTER TABLE `advance_process_instance` DISABLE KEYS */;
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
  UNIQUE KEY `branch_unique_idx` (`branch_code`,`branch_name`,`company_id`),
  KEY `company_id_idx` (`company_id`),
  KEY `created_by_branch_idx` (`created_by`),
  KEY `modified_by_branch_idx` (`modified_by`),
  CONSTRAINT `company_id_branch` FOREIGN KEY (`company_id`) REFERENCES `company_details` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_branch` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_branch` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch_details`
--

LOCK TABLES `branch_details` WRITE;
/*!40000 ALTER TABLE `branch_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_details`
--

LOCK TABLES `company_details` WRITE;
/*!40000 ALTER TABLE `company_details` DISABLE KEYS */;
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
  `branch_id` int(11) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`dept_id`),
  KEY `created_by_dept_idx` (`created_by`),
  KEY `modified_by_dept_idx` (`modified_by`),
  KEY `branch_id_dept_idx` (`branch_id`),
  CONSTRAINT `created_by_dept` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_dept` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_details`
--

LOCK TABLES `department_details` WRITE;
/*!40000 ALTER TABLE `department_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_head`
--

LOCK TABLES `department_head` WRITE;
/*!40000 ALTER TABLE `department_head` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_details`
--

LOCK TABLES `employee_details` WRITE;
/*!40000 ALTER TABLE `employee_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_details`
--

LOCK TABLES `event_details` WRITE;
/*!40000 ALTER TABLE `event_details` DISABLE KEYS */;
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
  KEY `created_by_exp_cat_idx` (`created_by`),
  KEY `modified_by_exp_cat_idx` (`modified_by`),
  KEY `company_id_exp_cat_idx` (`company_id`),
  CONSTRAINT `company_id_exp_cat` FOREIGN KEY (`company_id`) REFERENCES `company_details` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `created_by_exp_cat` FOREIGN KEY (`created_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `modified_by_exp_cat` FOREIGN KEY (`modified_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_category`
--

LOCK TABLES `expense_category` WRITE;
/*!40000 ALTER TABLE `expense_category` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
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
  `expense_type` varchar(20) NOT NULL,
  `voucher_number` varchar(50) DEFAULT NULL,
  `employee_id` bigint(10) NOT NULL,
  `advance_id` bigint(10) DEFAULT NULL,
  `event_id` int(11) DEFAULT NULL,
  `voucher_status` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `purpose` varchar(100) NOT NULL,
  `accounting_entry` char(1) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functional_flow`
--

LOCK TABLES `functional_flow` WRITE;
/*!40000 ALTER TABLE `functional_flow` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_details`
--

LOCK TABLES `login_details` WRITE;
/*!40000 ALTER TABLE `login_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_details`
--

LOCK TABLES `payment_details` WRITE;
/*!40000 ALTER TABLE `payment_details` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_history`
--

LOCK TABLES `process_history` WRITE;
/*!40000 ALTER TABLE `process_history` DISABLE KEYS */;
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
  `session_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`process_instance_id`),
  KEY `pending_at_process_inst_idx` (`pending_at`),
  KEY `voucher_status_process_inst_idx` (`voucher_status_id`),
  KEY `exp_header_process_inst_idx` (`expense_header_id`),
  KEY `processed_by_process_inst_idx` (`processed_by`),
  CONSTRAINT `exp_header_process_inst` FOREIGN KEY (`expense_header_id`) REFERENCES `expense_header` (`expense_header_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `pending_at_process_inst` FOREIGN KEY (`pending_at`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `processed_by_process_inst` FOREIGN KEY (`processed_by`) REFERENCES `employee_details` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `voucher_status_process_inst` FOREIGN KEY (`voucher_status_id`) REFERENCES `voucher_status` (`voucher_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `process_instance`
--

LOCK TABLES `process_instance` WRITE;
/*!40000 ALTER TABLE `process_instance` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
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
-- Dumping routines for database 'expensewala'
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

-- Dump completed on 2020-06-17 14:14:15
