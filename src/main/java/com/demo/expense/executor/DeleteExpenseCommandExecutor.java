package com.demo.expense.executor;

import com.demo.expense.api.CommandExecutor;
import com.demo.expense.command.DeleteExpenseCommand;
import com.demo.expense.event.CreateExpenseEvent;
import com.demo.expense.event.DeleteExpenseEvent;
import com.demo.expense.kafka.KafkaProducer;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteExpenseCommandExecutor implements CommandExecutor<DeleteExpenseCommand, Void> {

    private final ExpenseRepository expenseRepository;
    private final KafkaProducer kafkaProducer;

    @Autowired
    public DeleteExpenseCommandExecutor(ExpenseRepository expenseRepository, KafkaProducer kafkaProducer) {
        this.expenseRepository = expenseRepository;
        this.kafkaProducer=kafkaProducer;
    }

    @Override
    public Void execute(DeleteExpenseCommand command) {
        Optional<Expense> expenseOpt = expenseRepository.findById(command.getExpenseId());
        if (expenseOpt.isEmpty()) {
            throw new IllegalArgumentException("Expense not found with id: " + command.getExpenseId());
        }
        Expense expense = expenseOpt.get();
        expenseRepository.deleteById(command.getExpenseId());
        kafkaProducer.sendEvent(createEvent(expense));
        return null;
    }

    public DeleteExpenseEvent createEvent(Expense expense) {
        DeleteExpenseEvent event = new DeleteExpenseEvent();
        event.setExpenseId(expense.getExpenseId());  // Assuming the expenseId is set after saving to the database
        event.setUserId(expense.getUserId());
        event.setExpenseName(expense.getExpenseName());
        event.setExpenseDescription(expense.getExpenseDescription());
        event.setExpenseType(expense.getExpenseType());
        event.setAmount(expense.getAmount());
        return event;
    }
}
