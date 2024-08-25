package com.demo.expense.controller;

import com.demo.expense.enums.Category;
import com.demo.expense.model.Expense;
import com.demo.expense.query.GetExpensesByExpenseTypeAndUserIdQuery;
import com.demo.expense.query.GetExpensesByUserIdQuery;
import com.demo.expense.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseQueryControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseQueryController expenseQueryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExpensesByUserId_Success() {
        // Arrange
        Long userId = 1L;
        List<Expense> expenses = Arrays.asList(new Expense(), new Expense());
        when(expenseService.executeQuery(any(GetExpensesByUserIdQuery.class))).thenReturn(expenses);

        // Act
        ResponseEntity<List<Expense>> response = expenseQueryController.getExpensesByUserId(userId);

        // Assert
        assertNotNull(response);
        assertEquals(expenses, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(expenseService, times(1)).executeQuery(any(GetExpensesByUserIdQuery.class));
    }

    @Test
    void testGetExpensesByExpenseTypeAndUserId_Success() {
        // Arrange
        Long userId = 1L;
        Category expenseType = Category.ENTERTAINMENT;
        List<Expense> expenses = Arrays.asList(new Expense(), new Expense());
        when(expenseService.executeQuery(any(GetExpensesByExpenseTypeAndUserIdQuery.class))).thenReturn(expenses);

        // Act
        ResponseEntity<List<Expense>> response = expenseQueryController.getExpensesByExpenseTypeAndUserId(userId, expenseType);

        // Assert
        assertNotNull(response);
        assertEquals(expenses, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(expenseService, times(1)).executeQuery(any(GetExpensesByExpenseTypeAndUserIdQuery.class));
    }
}
