package com.demo.expense.service;
import com.demo.expense.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExecutionContextFactory executionContextFactory;

    @Mock
    private CommandExecutor<Command, Object> commandExecutor;

    @Mock
    private QueryExecutor<Query, Object> queryExecutor;

    @Mock
    private EventExecutor<Event> eventExecutor;

    @InjectMocks
    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteCommand_Success() {
        // Arrange
        Command command = mock(Command.class);
        Object expectedResult = new Object();
        when(executionContextFactory.getCommandExecutor(any(Class.class))).thenReturn(commandExecutor);
        when(commandExecutor.execute(any(Command.class))).thenReturn(expectedResult);

        // Act
        Object result = expenseService.executeCommand(command);

        // Assert
        assertEquals(expectedResult, result);
        verify(executionContextFactory, times(1)).getCommandExecutor(any(Class.class));
        verify(commandExecutor, times(1)).execute(any(Command.class));
    }

    @Test
    void testExecuteCommand_NoExecutorFound() {
        // Arrange
        Command command = mock(Command.class);
        when(executionContextFactory.getCommandExecutor(any(Class.class))).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> expenseService.executeCommand(command));
        assertEquals("No executor found for command: " + command.getClass().getName(), exception.getMessage());
        verify(executionContextFactory, times(1)).getCommandExecutor(any(Class.class));
        verify(commandExecutor, never()).execute(any(Command.class));
    }

    @Test
    void testExecuteQuery_Success() {
        // Arrange
        Query query = mock(Query.class);
        Object expectedResult = new Object();
        when(executionContextFactory.getQueryExecutor(any(Class.class))).thenReturn(queryExecutor);
        when(queryExecutor.execute(any(Query.class))).thenReturn(expectedResult);

        // Act
        Object result = expenseService.executeQuery(query);

        // Assert
        assertEquals(expectedResult, result);
        verify(executionContextFactory, times(1)).getQueryExecutor(any(Class.class));
        verify(queryExecutor, times(1)).execute(any(Query.class));
    }

    @Test
    void testExecuteQuery_NoExecutorFound() {
        // Arrange
        Query query = mock(Query.class);
        when(executionContextFactory.getQueryExecutor(any(Class.class))).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> expenseService.executeQuery(query));
        assertEquals("No executor found for query: " + query.getClass().getName(), exception.getMessage());
        verify(executionContextFactory, times(1)).getQueryExecutor(any(Class.class));
        verify(queryExecutor, never()).execute(any(Query.class));
    }

    @Test
    void testHandleEvent_Success() {
        // Arrange
        Event event = mock(Event.class);
        when(executionContextFactory.getEventExecutor(any(Class.class))).thenReturn(eventExecutor);

        // Act
        expenseService.handleEvent(event);

        // Assert
        verify(executionContextFactory, times(1)).getEventExecutor(any(Class.class));
        verify(eventExecutor, times(1)).execute(any(Event.class));
    }

    @Test
    void testHandleEvent_NoExecutorFound() {
        // Arrange
        Event event = mock(Event.class);
        when(executionContextFactory.getEventExecutor(any(Class.class))).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> expenseService.handleEvent(event));
        assertEquals("No executor found for event: " + event.getClass().getName(), exception.getMessage());
        verify(executionContextFactory, times(1)).getEventExecutor(any(Class.class));
        verify(eventExecutor, never()).execute(any(Event.class));
    }
}
