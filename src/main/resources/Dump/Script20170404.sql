CREATE TABLE expense_category (
  expense_category_id int(11) NOT NULL AUTO_INCREMENT,
  expense_name varchar(45) NOT NULL,
  gl_code varchar(45) DEFAULT NULL,
  location_required char(1) NOT NULL,
  unit_required char(1) DEFAULT NULL,
  amount double DEFAULT NULL,
  created_by bigint(11) DEFAULT NULL,
  created_date varchar(45) DEFAULT NULL,
  modified_by bigint(11) DEFAULT NULL,
  modified_date varchar(45) DEFAULT NULL,
  status char(1) NOT NULL,
  PRIMARY KEY (expense_category_id),
  UNIQUE KEY category_name_UNIQUE (expense_name),
  KEY created_by_exp_cat_idx (created_by),
  KEY modified_by_exp_cat_idx (modified_by),
  CONSTRAINT created_by_exp_cat FOREIGN KEY (created_by) REFERENCES employee_details (employee_id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT modified_by_exp_cat FOREIGN KEY (modified_by) REFERENCES employee_details (employee_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) 
