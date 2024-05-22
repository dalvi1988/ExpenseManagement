package com.chaitanya.dashboard.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.dashboard.model.DashboardDTO;

@Repository
public class DashboardDAO implements IDashboardDAO {

	@Autowired
	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardDTO> totalAmountGroupByMonth(DashboardDTO dashboardDTO) {
		List<DashboardDTO> list = null;
		// @Todo
//		Session session=sessionFactory.getCurrentSession();
//	  	ProjectionList projection = Projections.projectionList();
//	  	projection.add(Projections.sqlGroupProjection(
//              "concat(monthname(date),' ', year(date)) as label, sum(amount) as amount", "label", new String[] {
//                      "label", "amount"}, new Type[] { StringType.INSTANCE
//                      		,DoubleType.INSTANCE }));
//      
//	  	list = (List<DashboardDTO>) session.createCriteria(ExpenseDetailJPA.class)    
//						.setProjection(projection)
//						.createAlias("expenseHeaderJPA", "expenseHeaderJPA",JoinType.INNER_JOIN)
//						.createAlias("expenseHeaderJPA.processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
//						.createAlias("processInstanceJPA.voucherStatusJPA", "voucherStatusJPA",JoinType.INNER_JOIN)
//						.createAlias("expenseHeaderJPA.employeeJPA", "employeeJPA",JoinType.INNER_JOIN)
//						.add(Restrictions.eq("employeeJPA.employeeId", dashboardDTO.getEmployeeDTO().getEmployeeId()))
//						.add(Restrictions.eq("voucherStatusJPA.voucherStatusId", 5))
//						 .setResultTransformer(new AliasToBeanResultTransformer(DashboardDTO.class))
//						.list();
		return list;
	}

}
