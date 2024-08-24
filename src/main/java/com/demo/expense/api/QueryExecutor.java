package com.demo.expense.api;

public interface QueryExecutor<T extends Query, R> {
    R execute(T query);
}

