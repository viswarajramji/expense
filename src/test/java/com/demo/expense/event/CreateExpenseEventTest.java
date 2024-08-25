package com.demo.expense.event;
import com.demo.expense.enums.Category;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateExpenseEventTest {

    @Test
    void testCreateExpenseEventCreation() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 2L;
        String expenseName = "Grocery Shopping";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double amount = 150.0;

        // Act
        CreateExpenseEvent event = new CreateExpenseEvent(expenseId, userId, expenseName, expenseDescription, expenseType, amount);

        // Assert
        assertNotNull(event);
        assertEquals(expenseId, event.getExpenseId());
        assertEquals(userId, event.getUserId());
        assertEquals(expenseName, event.getExpenseName());
        assertEquals(expenseDescription, event.getExpenseDescription());
        assertEquals(expenseType, event.getExpenseType());
        assertEquals(amount, event.getAmount());
    }

    @Test
    void testCreateExpenseEventDefaultConstructor() {
        // Act
        CreateExpenseEvent event = new CreateExpenseEvent();

        // Assert
        assertNotNull(event);
        assertEquals(null, event.getExpenseId());
        assertEquals(null, event.getUserId());
        assertEquals(null, event.getExpenseName());
        assertEquals(null, event.getExpenseDescription());
        assertEquals(null, event.getExpenseType());
        assertEquals(null, event.getAmount());
    }
}
