package com.demo.expense;

public interface CommandExecutor<T extends Command, R> {
    R execute(T command);
}
