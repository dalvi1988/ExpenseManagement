CREATE TABLE test.levels (
 level_id INT NOT NULL,
 level_name VARCHAR(45) NOT NULL,
 PRIMARY KEY (level_id)
 );
  
CREATE TABLE functional_flow (
  flow_id int(11) NOT NULL AUTO_INCREMENT,
  branch_id int(11) NOT NULL,
  department_id int(11) DEFAULT NULL,
  no_of_level int(11) DEFAULT NULL,
  level1 bigint(10) NOT NULL,
  level2 bigint(10) DEFAULT NULL,
  level3 bigint(10) DEFAULT NULL,
  created_by bigint(10) DEFAULT NULL,
  created_date datetime DEFAULT NULL,
  modified_by bigint(10) DEFAULT NULL,
  modified_date datetime DEFAULT NULL,
  status char(1) NOT NULL,
  PRIMARY KEY (flow_id)
);


ALTER TABLE test.functional_flow 
CHANGE COLUMN is_branch_flow is_branch_flow CHAR(1) NOT NULL AFTER flow_id;


