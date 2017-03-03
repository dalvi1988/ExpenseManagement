ALTER TABLE `test`.`department_head` 
ADD COLUMN `status` CHAR(1) NOT NULL AFTER `employee_id`;


ALTER TABLE `test`.`department_head` 
ADD COLUMN `created_by` BIGINT(11) NULL AFTER `employee_id`,
ADD COLUMN `created_date` DATETIME NULL AFTER `created_by`,
ADD COLUMN `modified_by` BIGINT(11) NULL AFTER `created_date`,
ADD COLUMN `modified_date` DATETIME NULL AFTER `modified_by`,
ADD INDEX `created_by_dept_head_idx` (`created_by` ASC),
ADD INDEX `modified_by_dept_head_idx` (`modified_by` ASC);

ALTER TABLE `test`.`department_head` 
ADD CONSTRAINT `created_by_dept_head`
 FOREIGN KEY (`created_by`)
 REFERENCES `test`.`employee_details` (`employee_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION,
ADD CONSTRAINT `modified_by_dept_head`
 FOREIGN KEY (`modified_by`)
 REFERENCES `test`.`employee_details` (`employee_id`)
 ON DELETE NO ACTION
 ON UPDATE NO ACTION;
