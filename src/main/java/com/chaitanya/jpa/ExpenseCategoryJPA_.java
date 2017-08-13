package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-08-13T17:15:52.072+0530")
@StaticMetamodel(ExpenseCategoryJPA.class)
public class ExpenseCategoryJPA_ {
	public static volatile SingularAttribute<ExpenseCategoryJPA, Long> expCategoryId;
	public static volatile SingularAttribute<ExpenseCategoryJPA, CompanyJPA> companyJPA;
	public static volatile SingularAttribute<ExpenseCategoryJPA, String> expenseName;
	public static volatile SingularAttribute<ExpenseCategoryJPA, String> glCode;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Character> locationRequired;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Character> unitRequired;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Double> amount;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Character> limitIncrease;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Long> createdBy;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Long> modifiedBy;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Calendar> createdDate;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<ExpenseCategoryJPA, Character> status;
}
