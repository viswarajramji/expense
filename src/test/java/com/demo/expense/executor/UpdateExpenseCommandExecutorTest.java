package com.demo.expense.executor;
import com.demo.expense.command.UpdateExpenseCommand;
import com.demo.expense.enums.Category;
import com.demo.expense.event.UpdateExpenseEvent;
import com.demo.expense.kafka.KafkaProducer;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import com.demo.expense.repo.ExpenseRepository;
import com.demo.expense.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateExpenseCommandExecutorTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private UpdateExpenseCommandExecutor updateExpenseCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 2L;
        UpdateExpenseCommand command = new UpdateExpenseCommand(expenseId, userId, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 150.0);
        Expense existingExpense = new Expense();
        existingExpense.setExpenseId(expenseId);
        existingExpense.setUserId(userId);
        existingExpense.setExpenseName("Groceries");
        existingExpense.setExpenseDescription("Weekly groceries");
        existingExpense.setExpenseType(Category.ENTERTAINMENT);
        existingExpense.setAmount(100.0);

        when(userValidationService.isValidUser(userId)).thenReturn(true);
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(existingExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(existingExpense);

        // Act
        Expense result = updateExpenseCommandExecutor.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(command.getAmount(), result.getAmount());
        verify(kafkaProducer, times(1)).sendEvent(any(UpdateExpenseEvent.class));
    }

    @Test
    void testExecute_InvalidUser() {
        // Arrange
        Long userId = 2L;
        UpdateExpenseCommand command = new UpdateExpenseCommand(1L, userId, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 150.0);

        when(userValidationService.isValidUser(userId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateExpenseCommandExecutor.execute(command));
        assertEquals("Invalid userId: " + userId, exception.getMessage());
        verify(expenseRepository, never()).findById(anyLong());
        verify(expenseRepository, never()).save(any(Expense.class));
        verify(kafkaProducer, never()).sendEvent(any(UpdateExpenseEvent.class));
    }

    @Test
    void testExecute_ExpenseNotFound() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 2L;
        UpdateExpenseCommand command = new UpdateExpenseCommand(expenseId, userId, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 150.0);

        when(userValidationService.isValidUser(userId)).thenReturn(true);
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> updateExpenseCommandExecutor.execute(command));
        assertEquals("Expense not found with id: " + expenseId, exception.getMessage());
        verify(expenseRepository, times(1)).findById(expenseId);
        verify(expenseRepository, never()).save(any(Expense.class));
        verify(kafkaProducer, never()).sendEvent(any(UpdateExpenseEvent.class));
    }
}
