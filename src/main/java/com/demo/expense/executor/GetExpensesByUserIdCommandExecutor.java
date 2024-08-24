package com.demo.expense.executor;
import com.demo.expense.CommandExecutor;
import com.demo.expense.command.GetExpensesByUserIdCommand;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExpensesByUserIdCommandExecutor implements CommandExecutor<GetExpensesByUserIdCommand, List<Expense>> {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public GetExpensesByUserIdCommandExecutor(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> execute(GetExpensesByUserIdCommand command) {
        return expenseRepository.findByUserId(command.getUserId());
    }
}

