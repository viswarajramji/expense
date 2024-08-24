package com.demo.expense.executor;
import com.demo.expense.CommandExecutor;
import com.demo.expense.command.GetExpensesByExpenseTypeAndUserIdCommand;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExpensesByExpenseTypeAndUserIdCommandExecutor implements CommandExecutor<GetExpensesByExpenseTypeAndUserIdCommand, List<Expense>> {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public GetExpensesByExpenseTypeAndUserIdCommandExecutor(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> execute(GetExpensesByExpenseTypeAndUserIdCommand command) {
        return expenseRepository.findByUserIdAndExpenseType(command.getUserId(), command.getExpenseType());
    }
}
