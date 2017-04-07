ALTER TABLE `test`.`expense_details` 
CHANGE COLUMN `expense_name_id` `expense_category_id` INT(11) NOT NULL ,
ADD INDEX `exp_category_exp_detail_idx` (`expense_category_id` ASC);

ALTER TABLE `test`.`expense_details` 
ADD CONSTRAINT `exp_category_exp_detail`
 FOREIGN KEY (`expense_category_id`)
 REFERENCES `test`.`expense_category` (`expense_category_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;

ALTER TABLE `test`.`expense_details` 
 ADD INDEX `exp_header_exp_detail_idx` (`expense_header_id` ASC),
 DROP INDEX `exp_header_exp_details_idx`;
 
 ALTER TABLE `test`.`expense_details` 
ADD CONSTRAINT `exp_header_exp_detail`
 FOREIGN KEY (`expense_header_id`)
 REFERENCES `test`.`expense_header` (`expense_header_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;

 ALTER TABLE `test`.`expense_details` 
ADD COLUMN `description` VARCHAR(45) NOT NULL AFTER `to_location`,
ADD COLUMN `unit` INT(5) NULL AFTER `description`;

CREATE TABLE `process_instance` (
  `process_instance_id` bigint(10) NOT NULL,
  `expense_header_id` bigint(10) DEFAULT NULL,
  `current_status` int(5) NOT NULL,
  `currently_pending_at` bigint(10) DEFAULT NULL,
  `previously_approved_by` bigint(10) DEFAULT NULL,
  `created_by` bigint(10) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_by` bigint(10) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`process_instance_id`)
) ;
