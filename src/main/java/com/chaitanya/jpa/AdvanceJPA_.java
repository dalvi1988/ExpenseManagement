package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-25T15:14:24.229+0530")
@StaticMetamodel(AdvanceJPA.class)
public class AdvanceJPA_ {
	public static volatile SingularAttribute<AdvanceJPA, Long> advanceDetailId;
	public static volatile SingularAttribute<AdvanceJPA, String> purpose;
	public static volatile SingularAttribute<AdvanceJPA, Double> amount;
	public static volatile SingularAttribute<AdvanceJPA, Character> isEvent;
	public static volatile SingularAttribute<AdvanceJPA, VoucherStatusJPA> voucherStatusJPA;
	public static volatile SingularAttribute<AdvanceJPA, Calendar> date;
}
