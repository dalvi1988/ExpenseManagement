package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2024-05-22T20:40:41.059+0100")
@StaticMetamodel(DepartmentJPA.class)
public class DepartmentJPA_ {
	public static volatile SingularAttribute<DepartmentJPA, Long> departmentId;
	public static volatile SingularAttribute<DepartmentJPA, String> deptName;
	public static volatile SingularAttribute<DepartmentJPA, String> deptCode;
	public static volatile SingularAttribute<DepartmentJPA, BranchJPA> branchJPA;
	public static volatile SingularAttribute<DepartmentJPA, Long> createdBy;
	public static volatile SingularAttribute<DepartmentJPA, Long> modifiedBy;
	public static volatile SingularAttribute<DepartmentJPA, Calendar> createdDate;
	public static volatile SingularAttribute<DepartmentJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<DepartmentJPA, Character> status;
}
