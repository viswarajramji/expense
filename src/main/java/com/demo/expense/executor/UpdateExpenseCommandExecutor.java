package com.demo.expense.executor;

import com.demo.expense.api.CommandExecutor;
import com.demo.expense.command.UpdateExpenseCommand;
import com.demo.expense.event.UpdateExpenseEvent;
import com.demo.expense.kafka.KafkaProducer;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import com.demo.expense.service.UserValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UpdateExpenseCommandExecutor implements CommandExecutor<UpdateExpenseCommand, Expense> {

    private final ExpenseRepository expenseRepository;
    private final UserValidationService userValidationService;
    private final KafkaProducer kafkaProducer; // Assuming you have an event publisher to handle the event

    @Autowired
    public UpdateExpenseCommandExecutor(ExpenseRepository expenseRepository, UserValidationService userValidationService,  KafkaProducer kafkaProducer) {
        this.expenseRepository = expenseRepository;
        this.userValidationService = userValidationService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public Expense execute(UpdateExpenseCommand command) {
        if (!userValidationService.isValidUser(command.getUserId())) {
            throw new RuntimeException("Invalid userId: " + command.getUserId());
        }

        Optional<Expense> existingExpenseOpt = expenseRepository.findById(command.getExpenseId());
        if (existingExpenseOpt.isEmpty()) {
            throw new RuntimeException("Expense not found with id: " + command.getExpenseId());
        }

        Expense existingExpense = existingExpenseOpt.get();

        // Calculate the difference in amount
        Double oldAmount = existingExpense.getAmount();
        Double newAmount = command.getAmount();
        Double diffAmount = newAmount - oldAmount;

        // Update the expense details
        existingExpense.setExpenseName(command.getExpenseName());
        existingExpense.setExpenseDescription(command.getExpenseDescription());
        existingExpense.setExpenseType(command.getExpenseType());
        existingExpense.setAmount(newAmount);

        // Save the updated expense to the repository
        Expense updatedExpense = expenseRepository.save(existingExpense);

        // Create and trigger the UpdateExpenseEvent
        UpdateExpenseEvent event = createUpdateExpenseEvent(updatedExpense, newAmount, diffAmount);
        kafkaProducer.sendEvent(event);

        return updatedExpense;
    }

    private UpdateExpenseEvent createUpdateExpenseEvent(Expense updatedExpense, Double newAmount, Double diffAmount) {
        UpdateExpenseEvent event = new UpdateExpenseEvent();
        event.setExpenseId(updatedExpense.getExpenseId());
        event.setUserId(updatedExpense.getUserId());
        event.setExpenseName(updatedExpense.getExpenseName());
        event.setExpenseDescription(updatedExpense.getExpenseDescription());
        event.setExpenseType(updatedExpense.getExpenseType());
        event.setNewAmount(newAmount);
        event.setDiffAmount(diffAmount);
        return event;
    }
}
