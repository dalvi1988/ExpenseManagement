package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T19:15:12.776+0530")
@StaticMetamodel(ApprovalFlowJPA.class)
public class ApprovalFlowJPA_ {
	public static volatile SingularAttribute<ApprovalFlowJPA, Long> flowId;
	public static volatile SingularAttribute<ApprovalFlowJPA, BranchJPA> branchJPA;
	public static volatile SingularAttribute<ApprovalFlowJPA, DepartmentJPA> departmentJPA;
	public static volatile SingularAttribute<ApprovalFlowJPA, Character> isBranchFlow;
	public static volatile SingularAttribute<ApprovalFlowJPA, Integer> noOfLevel;
	public static volatile SingularAttribute<ApprovalFlowJPA, Long> level1;
	public static volatile SingularAttribute<ApprovalFlowJPA, Long> level2;
	public static volatile SingularAttribute<ApprovalFlowJPA, Long> level3;
	public static volatile SingularAttribute<ApprovalFlowJPA, Long> createdBy;
	public static volatile SingularAttribute<ApprovalFlowJPA, Long> modifiedBy;
	public static volatile SingularAttribute<ApprovalFlowJPA, Calendar> createdDate;
	public static volatile SingularAttribute<ApprovalFlowJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<ApprovalFlowJPA, Character> status;
}
