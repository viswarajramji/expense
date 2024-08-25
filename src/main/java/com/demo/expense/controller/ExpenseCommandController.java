package com.demo.expense.controller;

import com.demo.expense.command.CreateExpenseCommand;
import com.demo.expense.command.DeleteExpenseCommand;
import com.demo.expense.command.UpdateExpenseCommand;
import com.demo.expense.model.Expense;
import com.demo.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses/api/command")
public class ExpenseCommandController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseCommandController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // 1. Create an Expense
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid  @RequestBody CreateExpenseCommand command) {
        Expense expense = expenseService.executeCommand(command);
        return ResponseEntity.ok(expense);
    }

    // 2. Delete an Expense by ID
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        DeleteExpenseCommand command = new DeleteExpenseCommand(expenseId);
        expenseService.executeCommand(command);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long expenseId,@Valid @RequestBody UpdateExpenseCommand command) {
        // Set the expenseId from the path variable
        command.setExpenseId(expenseId);
        // Execute the update command
        Expense updatedExpense = expenseService.executeCommand(command);
        return ResponseEntity.ok(updatedExpense);
    }
}
