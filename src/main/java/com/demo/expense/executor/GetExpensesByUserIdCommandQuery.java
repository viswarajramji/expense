package com.demo.expense.executor;

import com.demo.expense.api.QueryExecutor;
import com.demo.expense.model.Expense;
import com.demo.expense.query.GetExpensesByUserIdQuery;
import com.demo.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExpensesByUserIdCommandQuery implements QueryExecutor<GetExpensesByUserIdQuery, List<Expense>> {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public GetExpensesByUserIdCommandQuery(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> execute(GetExpensesByUserIdQuery query) {
        return expenseRepository.findByUserId(query.getUserId());
    }
}
