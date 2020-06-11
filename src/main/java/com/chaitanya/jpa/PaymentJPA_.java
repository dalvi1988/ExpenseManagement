package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T18:04:14.856+0530")
@StaticMetamodel(PaymentJPA.class)
public class PaymentJPA_ {
	public static volatile SingularAttribute<PaymentJPA, Long> paymentDetailId;
	public static volatile SingularAttribute<PaymentJPA, String> moduleName;
	public static volatile SingularAttribute<PaymentJPA, Long> voucherId;
	public static volatile SingularAttribute<PaymentJPA, EmployeeJPA> paidByEmployeeJPA;
	public static volatile SingularAttribute<PaymentJPA, Calendar> date;
	public static volatile SingularAttribute<PaymentJPA, Double> amount;
}
