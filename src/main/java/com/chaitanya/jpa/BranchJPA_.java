package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-05-19T21:17:19.548+0530")
@StaticMetamodel(BranchJPA.class)
public class BranchJPA_ {
	public static volatile SingularAttribute<BranchJPA, Long> branchId;
	public static volatile SingularAttribute<BranchJPA, String> branchCode;
	public static volatile SingularAttribute<BranchJPA, String> branchName;
	public static volatile SingularAttribute<BranchJPA, CompanyJPA> companyJPA;
	public static volatile SingularAttribute<BranchJPA, Long> createdBy;
	public static volatile SingularAttribute<BranchJPA, Long> modifiedBy;
	public static volatile SingularAttribute<BranchJPA, Calendar> createdDate;
	public static volatile SingularAttribute<BranchJPA, Calendar> modifiedDate;
	public static volatile SingularAttribute<BranchJPA, Character> status;
}
