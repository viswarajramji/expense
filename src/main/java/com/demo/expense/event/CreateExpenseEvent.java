package com.demo.expense.event;

import com.demo.expense.api.Command;
import com.demo.expense.api.Event;
import com.demo.expense.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseEvent implements Event {
    private Long expenseId;
    private Long userId;
    private String expenseName;
    private String expenseDescription;
    private ExpenseType expenseType;
    private Double amount;
}
