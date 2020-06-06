package com.chaitanya.accountingGenerator;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaitanya.jpa.ExpenseHeaderJPA;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface IAccountingGenerator {
	public byte[] generate(List<ExpenseHeaderJPA> expenseHeaderJPAList) throws IOException;
}
