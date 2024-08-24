package com.demo.expense.api;

public interface EventExecutor<T extends Event> {
    void execute(T event);
}
