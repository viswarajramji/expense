package com.demo.expense.executor;

import com.demo.expense.enums.Category;
import com.demo.expense.model.Expense;
import com.demo.expense.query.GetExpensesByExpenseTypeAndUserIdQuery;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetExpensesByExpenseTypeAndUserIdQueryExecutorTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private GetExpensesByExpenseTypeAndUserIdQueryExecutor queryExecutor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        Long userId = 1L;
        Category expenseType = Category.ENTERTAINMENT;
        List<Expense> expenses = Arrays.asList(new Expense(), new Expense());

        when(expenseRepository.findByUserIdAndExpenseType(userId, expenseType)).thenReturn(expenses);

        GetExpensesByExpenseTypeAndUserIdQuery query = new GetExpensesByExpenseTypeAndUserIdQuery(userId, expenseType);

        // Act
        List<Expense> result = queryExecutor.execute(query);

        // Assert
        assertNotNull(result);
        assertEquals(expenses.size(), result.size());
        assertEquals(expenses, result);
    }
}
