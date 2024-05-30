package com.chaitanya.jpa;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2024-05-22T20:40:41.778+0100")
@StaticMetamodel(LoginJPA.class)
public class LoginJPA_ {
	public static volatile SingularAttribute<LoginJPA, Long> loginId;
	public static volatile SingularAttribute<LoginJPA, EmployeeJPA> employeeJPA;
	public static volatile SingularAttribute<LoginJPA, String> password;
	public static volatile SingularAttribute<LoginJPA, String> userName;
	public static volatile SetAttribute<LoginJPA, UserRoleJPA> userRole;
}
