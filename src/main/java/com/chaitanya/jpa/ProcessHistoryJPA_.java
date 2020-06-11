package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T18:04:14.863+0530")
@StaticMetamodel(ProcessHistoryJPA.class)
public class ProcessHistoryJPA_ {
	public static volatile SingularAttribute<ProcessHistoryJPA, Long> processHistoryId;
	public static volatile SingularAttribute<ProcessHistoryJPA, ExpenseHeaderJPA> expenseHeaderJPA;
	public static volatile SingularAttribute<ProcessHistoryJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile SingularAttribute<ProcessHistoryJPA, EmployeeJPA> processedBy;
	public static volatile SingularAttribute<ProcessHistoryJPA, Calendar> processDate;
	public static volatile SingularAttribute<ProcessHistoryJPA, String> comment;
}
