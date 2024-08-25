package com.demo.expense.executor;
import com.demo.expense.command.CreateExpenseCommand;
import com.demo.expense.enums.Category;
import com.demo.expense.event.CreateExpenseEvent;
import com.demo.expense.kafka.KafkaProducer;
import com.demo.expense.model.Expense;
import com.demo.expense.repo.ExpenseRepository;
import com.demo.expense.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateExpenseCommandExecutorTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private CreateExpenseCommandExecutor createExpenseCommandExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        CreateExpenseCommand command = new CreateExpenseCommand(1L, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 100.0);
        Expense expense = new Expense();
        expense.setExpenseId(1L);
        expense.setUserId(command.getUserId());
        expense.setExpenseName(command.getExpenseName());
        expense.setExpenseDescription(command.getExpenseDescription());
        expense.setExpenseType(command.getExpenseType());
        expense.setAmount(command.getAmount());

        when(userValidationService.isValidUser(command.getUserId())).thenReturn(true);
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // Act
        Expense result = createExpenseCommandExecutor.execute(command);

        // Assert
        assertNotNull(result);
        assertEquals(expense.getExpenseId(), result.getExpenseId());
        assertEquals(expense.getUserId(), result.getUserId());
        assertEquals(expense.getExpenseName(), result.getExpenseName());
        assertEquals(expense.getExpenseDescription(), result.getExpenseDescription());
        assertEquals(expense.getExpenseType(), result.getExpenseType());
        assertEquals(expense.getAmount(), result.getAmount());
        verify(kafkaProducer, times(1)).sendEvent(any(CreateExpenseEvent.class));
    }

    @Test
    void testExecute_InvalidUser() {
        // Arrange
        CreateExpenseCommand command = new CreateExpenseCommand(1L, "Groceries", "Weekly groceries", Category.ENTERTAINMENT, 100.0);

        when(userValidationService.isValidUser(command.getUserId())).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> createExpenseCommandExecutor.execute(command));
        assertEquals("Invalid userId: " + command.getUserId(), exception.getMessage());
        verify(expenseRepository, never()).save(any(Expense.class));
        verify(kafkaProducer, never()).sendEvent(any(CreateExpenseEvent.class));
    }
}
