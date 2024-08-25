package com.demo.expense.executor;

import com.demo.expense.event.DeleteUserEvent;
import com.demo.expense.repo.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteExpenseByUserIdEventExecutorTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private DeleteExpenseByUserIdEventExecutor deleteExpenseByUserIdEventExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        Long userId = 1L;
        DeleteUserEvent event = new DeleteUserEvent(userId);

        // Act
        deleteExpenseByUserIdEventExecutor.execute(event);

        // Assert
        verify(expenseRepository, times(1)).deleteByUserId(userId);
    }
}
