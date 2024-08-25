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

class CreateExpenseCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCreateExpenseCommand_Valid() {
        // Arrange
        Long userId = 1L;
        String expenseName = "Grocery Shopping";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double amount = 100.0;

        CreateExpenseCommand command = new CreateExpenseCommand(userId, expenseName, expenseDescription, expenseType, amount);

        // Act
        Set<ConstraintViolation<CreateExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testCreateExpenseCommand_NullFields() {
        // Arrange
        CreateExpenseCommand command = new CreateExpenseCommand(null, null, null, null, null);

        // Act
        Set<ConstraintViolation<CreateExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(6, violations.size());  // 5 violations expected: all fields are null
    }

    @Test
    void testCreateExpenseCommand_InvalidAmount() {
        // Arrange
        Long userId = 1L;
        String expenseName = "Grocery Shopping";
        String expenseDescription = "Weekly groceries";
        Category expenseType = Category.ENTERTAINMENT;
        Double amount = -50.0;  // Invalid negative amount

        CreateExpenseCommand command = new CreateExpenseCommand(userId, expenseName, expenseDescription, expenseType, amount);

        // Act
        Set<ConstraintViolation<CreateExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<CreateExpenseCommand> violation = violations.iterator().next();
        assertEquals("must be greater than 0", violation.getMessage());
        assertEquals("amount", violation.getPropertyPath().toString());
    }
}
