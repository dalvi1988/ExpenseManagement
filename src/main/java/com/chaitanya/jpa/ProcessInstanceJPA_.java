package com.chaitanya.jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-20T18:57:28.167+0530")
@StaticMetamodel(ProcessInstanceJPA.class)
public class ProcessInstanceJPA_ {
	public static volatile SingularAttribute<ProcessInstanceJPA, Long> processInstanceId;
	public static volatile SingularAttribute<ProcessInstanceJPA, ExpenseHeaderJPA> expenseHeaderJPA;
	public static volatile SingularAttribute<ProcessInstanceJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile SingularAttribute<ProcessInstanceJPA, EmployeeJPA> pendingAt;
	public static volatile SingularAttribute<ProcessInstanceJPA, EmployeeJPA> processedBy;
	public static volatile SingularAttribute<ProcessInstanceJPA, String> comment;
}
