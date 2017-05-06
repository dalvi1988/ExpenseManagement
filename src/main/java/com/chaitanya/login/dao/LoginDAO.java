package com.chaitanya.login.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.LoginJPA;

@Repository
@EnableTransactionManagement
public class LoginDAO implements ILoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public LoginJPA findByUserName(String username) {

		
		List<LoginJPA> loginDetails = new ArrayList<LoginJPA>();
		loginDetails = sessionFactory.getCurrentSession().createQuery("from LoginJPA where userName=?").setParameter(0, username)
				.list();
		if (loginDetails.size() > 0) {
			return loginDetails.get(0);
		} else {
			return null;
		}
		

	}
	
	public LoginJPA saveLoginDetail(LoginJPA loginJPA){
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(loginJPA);
		return loginJPA;
	}

	@Override
	public int updatePassword(EmployeeDTO employeeDTO, String password) {
		Session session=sessionFactory.getCurrentSession();
	    Query qry = (Query) session.createQuery("update LoginJPA set password=:password where employeeJPA.employeeId=:employeeId");
			  	        qry.setParameter("employeeId",employeeDTO.getEmployeeId());
			  	      qry.setParameter("password",password);
	    int res = qry.executeUpdate();
		return res;
		
	}

}