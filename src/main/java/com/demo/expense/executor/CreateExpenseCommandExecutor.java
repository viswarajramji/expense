package com.demo.expense.executor;


import com.demo.expense.CommandExecutor;
import com.demo.expense.command.CreateExpenseCommand;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import com.demo.expense.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateExpenseCommandExecutor implements CommandExecutor<CreateExpenseCommand, Expense> {

    private final ExpenseRepository expenseRepository;
    private final UserValidationService userValidationService;

    @Autowired
    public CreateExpenseCommandExecutor(ExpenseRepository expenseRepository , UserValidationService userValidationService) {
        this.expenseRepository = expenseRepository;
        this.userValidationService=userValidationService;
    }

    @Override
    public Expense execute(CreateExpenseCommand command) {
        if (!userValidationService.isValidUser(command.getUserId())) {
            throw new IllegalArgumentException("Invalid userId: " + command.getUserId());
        }
        Expense expense = new Expense();
        expense.setUserId(command.getUserId());
        expense.setExpenseName(command.getExpenseName());
        expense.setExpenseDescription(command.getExpenseDescription());
        expense.setExpenseType(command.getExpenseType());
        expense.setAmount(command.getAmount());
        return expenseRepository.save(expense);
    }
}

