package com.chaitanya.dashboard.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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
						 .setResultTransformer(new AliasToBeanResultTransformer(DashboardDTO.class))
						.list();
		return list;
	}


}
