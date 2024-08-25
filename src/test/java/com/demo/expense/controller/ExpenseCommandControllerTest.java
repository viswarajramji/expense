package com.demo.expense.controller;
import com.demo.expense.command.CreateExpenseCommand;
import com.demo.expense.command.DeleteExpenseCommand;
import com.demo.expense.command.UpdateExpenseCommand;
import com.demo.expense.enums.Category;
import com.demo.expense.model.Expense;
import com.demo.expense.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseCommandControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseCommandController expenseCommandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense_Success() {
        // Arrange
        CreateExpenseCommand command = new CreateExpenseCommand(1L, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 100.0);
        Expense expense = new Expense();
        when(expenseService.executeCommand(any(CreateExpenseCommand.class))).thenReturn(expense);

        // Act
        ResponseEntity<Expense> response = expenseCommandController.createExpense(command);

        // Assert
        assertNotNull(response);
        assertEquals(expense, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(expenseService, times(1)).executeCommand(any(CreateExpenseCommand.class));
    }

    @Test
    void testDeleteExpense_Success() {
        // Arrange
        Long expenseId = 1L;
        // No need to use doNothing, just call the method
        when(expenseService.executeCommand(any(DeleteExpenseCommand.class))).thenReturn(null);

        // Act
        ResponseEntity<Void> response = expenseCommandController.deleteExpense(expenseId);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(expenseService, times(1)).executeCommand(any(DeleteExpenseCommand.class));
    }

    @Test
    void testUpdateExpense_Success() {
        // Arrange
        Long expenseId = 1L;
        UpdateExpenseCommand command = new UpdateExpenseCommand(expenseId, 1L, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 150.0);
        Expense updatedExpense = new Expense();
        when(expenseService.executeCommand(any(UpdateExpenseCommand.class))).thenReturn(updatedExpense);

        // Act
        ResponseEntity<Expense> response = expenseCommandController.updateExpense(expenseId, command);

        // Assert
        assertNotNull(response);
        assertEquals(updatedExpense, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(expenseService, times(1)).executeCommand(any(UpdateExpenseCommand.class));
    }
}
