package com.demo.expense.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class UserValidationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserValidationService userValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsValidUser_Success() {
        // Arrange
        Long userId = 1L;
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("User found");

        // Act
        boolean result = userValidationService.isValidUser(userId);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsValidUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        doThrow(HttpClientErrorException.NotFound.class).when(restTemplate).getForObject(anyString(), eq(String.class));

        // Act
        boolean result = userValidationService.isValidUser(userId);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsValidUser_ExceptionThrown() {
        // Arrange
        Long userId = 1L;
        doThrow(RuntimeException.class).when(restTemplate).getForObject(anyString(), eq(String.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userValidationService.isValidUser(userId));
        assertTrue(exception.getMessage().contains("User not found: " + userId));
    }
}
