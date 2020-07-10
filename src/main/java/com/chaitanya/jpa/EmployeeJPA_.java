package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-07-10T13:19:35.062+0530")
@StaticMetamodel(EmployeeJPA.class)
public class EmployeeJPA_ {
	public static volatile SingularAttribute<EmployeeJPA, Long> employeeId;
	public static volatile SingularAttribute<EmployeeJPA, String> firstName;
	public static volatile SingularAttribute<EmployeeJPA, String> middleName;
	public static volatile SingularAttribute<EmployeeJPA, String> lastName;
	public static volatile SingularAttribute<EmployeeJPA, String> emailId;
	public static volatile SingularAttribute<EmployeeJPA, EmployeeJPA> reportingMgr;
	public static volatile SetAttribute<EmployeeJPA, EmployeeJPA> subordinates;
	public static volatile SingularAttribute<EmployeeJPA, DepartmentJPA> departmentJPA;
	public static volatile SingularAttribute<EmployeeJPA, BranchJPA> branchJPA;
	public static volatile SingularAttribute<EmployeeJPA, Character> gender;
	public static volatile SingularAttribute<EmployeeJPA, Long> createdBy;
	public static volatile SingularAttribute<EmployeeJPA, Long> modifiedBy;
	public static volatile SingularAttribute<EmployeeJPA, Calendar> createdDate;
	public static volatile SingularAttribute<EmployeeJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<EmployeeJPA, Character> status;
	public static volatile SingularAttribute<EmployeeJPA, String> employeeCode;
}
