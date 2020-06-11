package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T18:04:14.833+0530")
@StaticMetamodel(ExpenseDetailJPA.class)
public class ExpenseDetailJPA_ {
	public static volatile SingularAttribute<ExpenseDetailJPA, Long> expenseDetailId;
	public static volatile SingularAttribute<ExpenseDetailJPA, ExpenseHeaderJPA> expenseHeaderJPA;
	public static volatile SingularAttribute<ExpenseDetailJPA, ExpenseCategoryJPA> expenseCategoryJPA;
	public static volatile SingularAttribute<ExpenseDetailJPA, Calendar> date;
	public static volatile SingularAttribute<ExpenseDetailJPA, String> fromLocation;
	public static volatile SingularAttribute<ExpenseDetailJPA, String> toLocation;
	public static volatile SingularAttribute<ExpenseDetailJPA, String> description;
	public static volatile SingularAttribute<ExpenseDetailJPA, Integer> unit;
	public static volatile SingularAttribute<ExpenseDetailJPA, Double> amount;
	public static volatile SingularAttribute<ExpenseDetailJPA, String> fileName;
}
