package com.demo.expense.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeleteExpenseCommandTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDeleteExpenseCommand_Valid() {
        // Arrange
        Long expenseId = 1L;
        DeleteExpenseCommand command = new DeleteExpenseCommand(expenseId);

        // Act
        Set<ConstraintViolation<DeleteExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertNotNull(command);
        assertEquals(0, violations.size());  // No validation violations should occur
    }

    @Test
    void testDeleteExpenseCommand_NullExpenseId() {
        // Arrange
        DeleteExpenseCommand command = new DeleteExpenseCommand(null);

        // Act
        Set<ConstraintViolation<DeleteExpenseCommand>> violations = validator.validate(command);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<DeleteExpenseCommand> violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("expenseId", violation.getPropertyPath().toString());
    }
}
