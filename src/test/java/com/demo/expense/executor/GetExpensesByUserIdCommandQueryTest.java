package com.demo.expense.executor;

import com.demo.expense.model.Expense;
import com.demo.expense.query.GetExpensesByUserIdQuery;
import com.demo.expense.repo.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GetExpensesByUserIdCommandQueryTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private GetExpensesByUserIdCommandQuery queryExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        Long userId = 1L;
        List<Expense> expenses = Arrays.asList(new Expense(), new Expense());

        when(expenseRepository.findByUserId(userId)).thenReturn(expenses);

        GetExpensesByUserIdQuery query = new GetExpensesByUserIdQuery(userId);

        // Act
        List<Expense> result = queryExecutor.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(expenses.size(), result.size());
        assertEquals(expenses, result);
    }
}
