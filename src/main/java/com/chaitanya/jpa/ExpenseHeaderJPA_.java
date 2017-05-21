package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-19T21:17:19.613+0530")
@StaticMetamodel(ExpenseHeaderJPA.class)
public class ExpenseHeaderJPA_ {
	public static volatile SingularAttribute<ExpenseHeaderJPA, Long> expenseHeaderId;
	public static volatile SingularAttribute<ExpenseHeaderJPA, String> voucherNumber;
	public static volatile ListAttribute<ExpenseHeaderJPA, ExpenseDetailJPA> expenseDetailJPA;
	public static volatile ListAttribute<ExpenseHeaderJPA, ProcessHistoryJPA> processHistoryJPA;
	public static volatile SingularAttribute<ExpenseHeaderJPA, ProcessInstanceJPA> processInstanceJPA;
	public static volatile SingularAttribute<ExpenseHeaderJPA, EmployeeJPA> employeeJPA;
	public static volatile SingularAttribute<ExpenseHeaderJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile SingularAttribute<ExpenseHeaderJPA, Calendar> startDate;
	public static volatile SingularAttribute<ExpenseHeaderJPA, Calendar> endDate;
	public static volatile SingularAttribute<ExpenseHeaderJPA, String> title;
	public static volatile SingularAttribute<ExpenseHeaderJPA, String> purpose;
}
