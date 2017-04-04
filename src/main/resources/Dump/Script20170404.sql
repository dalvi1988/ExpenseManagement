CREATE TABLE test.expense_category (
  expense_category_id int(11) NOT NULL AUTO_INCREMENT,
  expense_name varchar(45) NOT NULL,
  gl_code varchar(45) DEFAULT NULL,
  isLocationAllowed char(1) NOT NULL,
  unit int(11) DEFAULT NULL,
  amount double DEFAULT NULL,
  status char(1) NOT NULL,
  PRIMARY KEY (expense_category_id),
  UNIQUE KEY category_name_UNIQUE (expense_name)
)
