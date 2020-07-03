package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-07-03T20:33:03.296+0530")
@StaticMetamodel(AdvanceProcessHistoryJPA.class)
public class AdvanceProcessHistoryJPA_ {
	public static volatile SingularAttribute<AdvanceProcessHistoryJPA, Long> processHistoryId;
	public static volatile SingularAttribute<AdvanceProcessHistoryJPA, AdvanceJPA> advanceJPA;
	public static volatile SingularAttribute<AdvanceProcessHistoryJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile SingularAttribute<AdvanceProcessHistoryJPA, EmployeeJPA> processedBy;
	public static volatile SingularAttribute<AdvanceProcessHistoryJPA, Calendar> processDate;
	public static volatile SingularAttribute<AdvanceProcessHistoryJPA, String> comment;
}
