package com.chaitanya.utility.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.VoucherStatusJPA;

@Repository
@Transactional
public class CommonDAO implements ICommonDAO{

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public List<VoucherStatusJPA> getVoucherStatus() {
		Session session=sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<VoucherStatusJPA> voucherStatusJPAList = session.createCriteria(VoucherStatusJPA.class)
						.list();
		return voucherStatusJPAList;
	}
	
}
