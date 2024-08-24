package com.demo.expense.command;

import com.demo.expense.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExpensesByUserIdCommand implements Command {
    private Long userId;
}
