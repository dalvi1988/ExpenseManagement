package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T18:04:14.795+0530")
@StaticMetamodel(DepartmentHeadJPA.class)
public class DepartmentHeadJPA_ {
	public static volatile SingularAttribute<DepartmentHeadJPA, Long> deptHeadId;
	public static volatile SingularAttribute<DepartmentHeadJPA, BranchJPA> branchJPA;
	public static volatile SingularAttribute<DepartmentHeadJPA, DepartmentJPA> departmentJPA;
	public static volatile SingularAttribute<DepartmentHeadJPA, EmployeeJPA> employeeJPA;
	public static volatile SingularAttribute<DepartmentHeadJPA, Long> createdBy;
	public static volatile SingularAttribute<DepartmentHeadJPA, Long> modifiedBy;
	public static volatile SingularAttribute<DepartmentHeadJPA, Calendar> createdDate;
	public static volatile SingularAttribute<DepartmentHeadJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<DepartmentHeadJPA, Character> status;
}
