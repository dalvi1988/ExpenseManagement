package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2024-05-22T20:40:41.377+0100")
@StaticMetamodel(EventJPA.class)
public class EventJPA_ {
	public static volatile SingularAttribute<EventJPA, Long> eventId;
	public static volatile SingularAttribute<EventJPA, String> eventCode;
	public static volatile SingularAttribute<EventJPA, String> eventName;
	public static volatile SingularAttribute<EventJPA, BranchJPA> branchJPA;
	public static volatile SingularAttribute<EventJPA, Long> createdBy;
	public static volatile SingularAttribute<EventJPA, Long> modifiedBy;
	public static volatile SingularAttribute<EventJPA, Calendar> createdDate;
	public static volatile SingularAttribute<EventJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<EventJPA, Character> status;
}
