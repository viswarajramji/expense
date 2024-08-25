package com.demo.expense.executor;

import com.demo.expense.api.EventExecutor;
import com.demo.expense.event.DeleteUserEvent;
import com.demo.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteExpenseByUserIdEventExecutor implements EventExecutor<DeleteUserEvent> {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public DeleteExpenseByUserIdEventExecutor(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void execute(DeleteUserEvent command) {
        expenseRepository.deleteByUserId(command.getUserId());
    }
}
