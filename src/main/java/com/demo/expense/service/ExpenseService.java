package com.demo.expense.service;

import com.demo.expense.Command;
import com.demo.expense.CommandExecutor;
import com.demo.expense.CommandExecutorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    private final CommandExecutorFactory commandExecutorFactory;

    @Autowired
    public ExpenseService(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public <T extends Command, R> R executeCommand(T command) {
        CommandExecutor<T, R> executor = commandExecutorFactory.getExecutor((Class<T>) command.getClass());
        if (executor == null) {
            throw new IllegalArgumentException("No executor found for command: " + command.getClass().getName());
        }
        return executor.execute(command);
    }
}
