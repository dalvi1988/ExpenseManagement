package com.chaitanya.jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-06-11T18:04:14.760+0530")
@StaticMetamodel(AdvanceProcessInstanceJPA.class)
public class AdvanceProcessInstanceJPA_ {
	public static volatile SingularAttribute<AdvanceProcessInstanceJPA, Long> processInstanceId;
	public static volatile SingularAttribute<AdvanceProcessInstanceJPA, AdvanceJPA> advanceJPA;
	public static volatile SingularAttribute<AdvanceProcessInstanceJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile SingularAttribute<AdvanceProcessInstanceJPA, EmployeeJPA> pendingAt;
	public static volatile SingularAttribute<AdvanceProcessInstanceJPA, EmployeeJPA> processedBy;
	public static volatile SingularAttribute<AdvanceProcessInstanceJPA, String> comment;
}
