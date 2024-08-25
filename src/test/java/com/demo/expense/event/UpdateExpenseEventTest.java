package com.demo.expense.event;

import com.demo.expense.enums.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UpdateExpenseEventTest {

    @Test
    void testUpdateExpenseEventCreation() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 2L;
        String expenseName = "Grocery Shopping";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double newAmount = 200.0;
        Double diffAmount = 50.0;

        // Act
        UpdateExpenseEvent event = new UpdateExpenseEvent(expenseId, userId, expenseName, expenseDescription, expenseType, newAmount, diffAmount);

        // Assert
        assertNotNull(event);
        assertEquals(expenseId, event.getExpenseId());
        assertEquals(userId, event.getUserId());
        assertEquals(expenseName, event.getExpenseName());
        assertEquals(expenseDescription, event.getExpenseDescription());
        assertEquals(expenseType, event.getExpenseType());
        assertEquals(newAmount, event.getNewAmount());
        assertEquals(diffAmount, event.getDiffAmount());
    }

    @Test
    void testUpdateExpenseEventDefaultConstructor() {
        // Act
        UpdateExpenseEvent event = new UpdateExpenseEvent();

        // Assert
        assertNotNull(event);
        assertEquals(null, event.getExpenseId());
        assertEquals(null, event.getUserId());
        assertEquals(null, event.getExpenseName());
        assertEquals(null, event.getExpenseDescription());
        assertEquals(null, event.getExpenseType());
        assertEquals(null, event.getNewAmount());
        assertEquals(null, event.getDiffAmount());
    }
}
