package com.demo.expense.executor;


import com.demo.expense.api.CommandExecutor;
import com.demo.expense.api.Event;
import com.demo.expense.command.CreateExpenseCommand;
import com.demo.expense.event.CreateExpenseEvent;
import com.demo.expense.kafka.KafkaProducer;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import com.demo.expense.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateExpenseCommandExecutor implements CommandExecutor<CreateExpenseCommand, Expense> {

    private final ExpenseRepository expenseRepository;
    private final UserValidationService userValidationService;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public CreateExpenseCommandExecutor(ExpenseRepository expenseRepository , UserValidationService userValidationService, KafkaProducer kafkaProducer) {
        this.expenseRepository = expenseRepository;
        this.userValidationService=userValidationService;
        this.kafkaProducer=kafkaProducer;
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
        expense=expenseRepository.save(expense);
        kafkaProducer.sendEvent(createEvent(expense));
        return expense;
    }

    public CreateExpenseEvent createEvent(Expense expense) {
        CreateExpenseEvent event = new CreateExpenseEvent();
        event.setExpenseId(expense.getExpenseId());  // Assuming the expenseId is set after saving to the database
        event.setUserId(expense.getUserId());
        event.setExpenseName(expense.getExpenseName());
        event.setExpenseDescription(expense.getExpenseDescription());
        event.setExpenseType(expense.getExpenseType());
        event.setAmount(expense.getAmount());
        return event;
    }

}

