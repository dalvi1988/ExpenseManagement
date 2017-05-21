package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-19T21:17:19.557+0530")
@StaticMetamodel(CompanyJPA.class)
public class CompanyJPA_ {
	public static volatile SingularAttribute<CompanyJPA, Long> companyId;
	public static volatile SingularAttribute<CompanyJPA, String> companyCode;
	public static volatile SingularAttribute<CompanyJPA, String> companyName;
	public static volatile SetAttribute<CompanyJPA, BranchJPA> branchJPA;
	public static volatile SingularAttribute<CompanyJPA, Long> createdBy;
	public static volatile SingularAttribute<CompanyJPA, Long> modifiedBy;
	public static volatile SingularAttribute<CompanyJPA, Calendar> createdDate;
	public static volatile SingularAttribute<CompanyJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<CompanyJPA, Character> status;
}
