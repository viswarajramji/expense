package com.demo.expense.executor;

import com.demo.expense.CommandExecutor;
import com.demo.expense.command.DeleteExpenseCommand;
import com.demo.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteExpenseCommandExecutor implements CommandExecutor<DeleteExpenseCommand, Void> {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public DeleteExpenseCommandExecutor(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Void execute(DeleteExpenseCommand command) {
        expenseRepository.deleteById(command.getExpenseId());
        return null;
    }
}
