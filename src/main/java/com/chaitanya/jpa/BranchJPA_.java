package com.chaitanya.jpa;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2024-05-22T20:40:40.980+0100")
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
