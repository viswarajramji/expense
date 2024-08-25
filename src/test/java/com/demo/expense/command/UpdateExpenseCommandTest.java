package com.demo.expense.command;
import com.demo.expense.enums.Category;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UpdateExpenseCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testUpdateExpenseCommand_Valid() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 1L;
        String expenseName = "Grocery Shopping";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double amount = 100.0;

        UpdateExpenseCommand command = new UpdateExpenseCommand(
                expenseId, userId, expenseName, expenseDescription, expenseType, amount
        );

        // Act
        Set<ConstraintViolation<UpdateExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testUpdateExpenseCommand_NullFields() {
        // Arrange
        UpdateExpenseCommand command = new UpdateExpenseCommand(null, null, null, null, null, null);

        // Act
        Set<ConstraintViolation<UpdateExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(7, violations.size());  // 6 violations expected: all fields are null
    }

    @Test
    void testUpdateExpenseCommand_InvalidAmount() {
        // Arrange
        Long expenseId = 1L;
        Long userId = 1L;
        String expenseName = "Grocery Shopping";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double amount = -50.0;  // Invalid negative amount

        UpdateExpenseCommand command = new UpdateExpenseCommand(
                expenseId, userId, expenseName, expenseDescription, expenseType, amount
        );

        // Act
        Set<ConstraintViolation<UpdateExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<UpdateExpenseCommand> violation = violations.iterator().next();
        assertEquals("must be greater than 0", violation.getMessage());
        assertEquals("amount", violation.getPropertyPath().toString());
    }
}
