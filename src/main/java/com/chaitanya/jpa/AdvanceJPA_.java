package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T18:04:14.642+0530")
@StaticMetamodel(AdvanceJPA.class)
public class AdvanceJPA_ {
	public static volatile SingularAttribute<AdvanceJPA, Long> advanceDetailId;
	public static volatile SingularAttribute<AdvanceJPA, EmployeeJPA> employeeJPA;
	public static volatile SingularAttribute<AdvanceJPA, String> advanceNumber;
	public static volatile SingularAttribute<AdvanceJPA, String> purpose;
	public static volatile SingularAttribute<AdvanceJPA, Double> amount;
	public static volatile SingularAttribute<AdvanceJPA, Character> isEvent;
	public static volatile SingularAttribute<AdvanceJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile ListAttribute<AdvanceJPA, AdvanceProcessHistoryJPA> processHistoryJPA;
	public static volatile SingularAttribute<AdvanceJPA, AdvanceProcessInstanceJPA> processInstanceJPA;
	public static volatile SingularAttribute<AdvanceJPA, EventJPA> eventJPA;
	public static volatile SingularAttribute<AdvanceJPA, Long> createdBy;
	public static volatile SingularAttribute<AdvanceJPA, Long> modifiedBy;
	public static volatile SingularAttribute<AdvanceJPA, Calendar> createdDate;
	public static volatile SingularAttribute<AdvanceJPA, Calendar> modifiedDate;
}
