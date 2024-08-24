package com.demo.expense.controller;

import com.demo.expense.command.CreateExpenseCommand;
import com.demo.expense.command.DeleteExpenseCommand;
import com.demo.expense.command.GetExpensesByExpenseTypeAndUserIdCommand;
import com.demo.expense.command.GetExpensesByUserIdCommand;
import com.demo.expense.enums.ExpenseType;
import com.demo.expense.model.Expense;
import com.demo.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // 1. Create an Expense
    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody CreateExpenseCommand command) {
        // Use the service to execute the create expense command
        Expense expense = expenseService.executeCommand(command);
        return ResponseEntity.ok(expense);
    }

    // 2. Delete an Expense by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        // Create a delete command with the expenseId
        DeleteExpenseCommand command = new DeleteExpenseCommand(id);
        // Use the service to execute the delete expense command
        expenseService.executeCommand(command);
        return ResponseEntity.noContent().build();
    }

    // 3. Read Expenses by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable Long userId) {
        // Create a command to get expenses by userId
        GetExpensesByUserIdCommand command = new GetExpensesByUserIdCommand(userId);
        // Use the service to execute the command
        List<Expense> expenses = expenseService.executeCommand(command);
        return ResponseEntity.ok(expenses);
    }

    // 4. Read Expenses by expenseType and userId
    @GetMapping("/user/{userId}/type/{expenseType}")
    public ResponseEntity<List<Expense>> getExpensesByExpenseTypeAndUserId(
            @PathVariable Long userId,
            @PathVariable ExpenseType expenseType) {
        // Create a command to get expenses by expenseType and userId
        GetExpensesByExpenseTypeAndUserIdCommand command = new GetExpensesByExpenseTypeAndUserIdCommand(userId, expenseType);
        // Use the service to execute the command
        List<Expense> expenses = expenseService.executeCommand(command);
        return ResponseEntity.ok(expenses);
    }
}
