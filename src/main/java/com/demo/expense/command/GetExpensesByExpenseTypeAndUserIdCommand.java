package com.demo.expense.command;

import com.demo.expense.Command;
import com.demo.expense.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExpensesByExpenseTypeAndUserIdCommand implements Command {
    private Long userId;
    private ExpenseType expenseType;
}
