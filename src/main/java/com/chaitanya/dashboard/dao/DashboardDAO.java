package com.chaitanya.dashboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chaitanya.dashboard.model.DashboardDTO;
import com.chaitanya.jpa.ExpenseDetailJPA;

@Repository
public class DashboardDAO implements IDashboardDAO{

	@Autowired
	SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardDTO> totalAmountGroupByMonth(DashboardDTO dashboardDTO){
		List<DashboardDTO> list = null;
		
		Session session=sessionFactory.getCurrentSession();
	  	ProjectionList projection = Projections.projectionList();
	  	projection.add(Projections.sqlGroupProjection(
              "concat(monthname(date),' ', year(date)) as label, sum(amount) as amount", "label", new String[] {
                      "label", "amount"}, new Type[] { StringType.INSTANCE
                      		,DoubleType.INSTANCE }));
      
	  	list = (List<DashboardDTO>) session.createCriteria(ExpenseDetailJPA.class)    
						.setProjection(projection)
						.createAlias("expenseHeaderJPA", "expenseHeaderJPA",JoinType.INNER_JOIN)
						.createAlias("expenseHeaderJPA.processInstanceJPA", "processInstanceJPA",JoinType.INNER_JOIN)
						.createAlias("processInstanceJPA.voucherStatusJPA", "voucherStatusJPA",JoinType.INNER_JOIN)
						.createAlias("expenseHeaderJPA.employeeJPA", "employeeJPA",JoinType.INNER_JOIN)
						.add(Restrictions.eq("employeeJPA.employeeId", dashboardDTO.getEmployeeDTO().getEmployeeId()))
						.add(Restrictions.eq("voucherStatusJPA.voucherStatusId", 5))
						 .setResultTransformer(new AliasToBeanResultTransformer(DashboardDTO.class))
						.list();
		return list;
	}


}
