package com.demo.expense.api;

public interface CommandExecutor<T extends Command, R> {
    R execute(T command);
}

