CREATE TABLE `test`.`advance_details` (
 `advance_details_id` BIGINT(10) NOT NULL,
 `amount` DOUBLE NOT NULL,
 `purpose` VARCHAR(45) NOT NULL,
 `isEvent` CHAR(1) NOT NULL,
 `eventId` INT NULL,
 `voucher_status` INT(11) NULL,
 `date` DATETIME NULL,
 PRIMARY KEY (`advance_details_id`));
