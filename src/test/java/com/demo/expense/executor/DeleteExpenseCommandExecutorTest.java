package com.demo.expense.executor;
import com.demo.expense.command.DeleteExpenseCommand;
import com.demo.expense.enums.Category;
import com.demo.expense.event.DeleteExpenseEvent;
import com.demo.expense.kafka.KafkaProducer;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteExpenseCommandExecutorTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private DeleteExpenseCommandExecutor deleteExpenseCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        Long expenseId = 1L;
        DeleteExpenseCommand command = new DeleteExpenseCommand(expenseId);
        Expense expense = new Expense();
        expense.setExpenseId(expenseId);
        expense.setUserId(2L);
        expense.setExpenseName("Grocery Shopping");
        expense.setExpenseDescription("Weekly groceries");
        expense.setExpenseType(Category.ENTERTAINMENT);
        expense.setAmount(100.0);

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        // Act
        deleteExpenseCommandExecutor.execute(command);

        // Assert
        verify(expenseRepository, times(1)).deleteById(expenseId);
        verify(kafkaProducer, times(1)).sendEvent(any(DeleteExpenseEvent.class));
    }

    @Test
    void testExecute_ExpenseNotFound() {
        // Arrange
        Long expenseId = 1L;
        DeleteExpenseCommand command = new DeleteExpenseCommand(expenseId);

        when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> deleteExpenseCommandExecutor.execute(command));
        assertEquals("Expense not found with id: " + expenseId, exception.getMessage());
        verify(expenseRepository, never()).deleteById(anyLong());
        verify(kafkaProducer, never()).sendEvent(any(DeleteExpenseEvent.class));
    }
}
