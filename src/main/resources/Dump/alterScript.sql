// Add Accounting entry
ALTER TABLE `expense_header` 
ADD COLUMN `accounting_entry` CHAR(1) NULL AFTER `purpose`;

// add branch into department
ALTER TABLE `department_details` 
ADD COLUMN `branch_id` INT(11) NULL AFTER `dept_code`,
ADD INDEX `branch_id_dept_idx` (`branch_id` ASC);
ALTER TABLE `department_details` 
ADD CONSTRAINT `branch_id_dept`
  FOREIGN KEY (`branch_id`)
  REFERENCES `branch_details` (`branch_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
