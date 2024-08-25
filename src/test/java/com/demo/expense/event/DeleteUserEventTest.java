package com.demo.expense.event;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeleteUserEventTest {

    @Test
    void testDeleteUserEventCreation() {
        // Arrange
        Long userId = 1L;

        // Act
        DeleteUserEvent event = new DeleteUserEvent(userId);

        // Assert
        assertNotNull(event);
        assertEquals(userId, event.getUserId());
    }

    @Test
    void testDeleteUserEventDefaultConstructor() {
        // Act
        DeleteUserEvent event = new DeleteUserEvent();

        // Assert
        assertNotNull(event);
        assertEquals(null, event.getUserId());
    }
}

