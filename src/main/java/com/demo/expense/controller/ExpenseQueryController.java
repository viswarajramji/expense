package com.demo.expense.controller;

import com.demo.expense.enums.Category;
import com.demo.expense.model.Expense;
import com.demo.expense.query.GetExpensesByExpenseTypeAndUserIdQuery;
import com.demo.expense.query.GetExpensesByUserIdQuery;
import com.demo.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/expenses/api/query")
public class ExpenseQueryController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseQueryController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // 1. Read Expenses by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable Long userId) {
        GetExpensesByUserIdQuery query = new GetExpensesByUserIdQuery(userId);
        List<Expense> expenses = expenseService.executeQuery(query);
        return ResponseEntity.ok(expenses);
    }

    // 2. Read Expenses by expenseType and userId
    @GetMapping("/user/{userId}/type/{expenseType}")
    public ResponseEntity<List<Expense>> getExpensesByExpenseTypeAndUserId(
            @PathVariable Long userId,
            @Valid @PathVariable Category expenseType) {
        GetExpensesByExpenseTypeAndUserIdQuery query = new GetExpensesByExpenseTypeAndUserIdQuery(userId, expenseType);
        List<Expense> expenses = expenseService.executeQuery(query);
        return ResponseEntity.ok(expenses);
    }
}
