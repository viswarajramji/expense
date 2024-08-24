package com.demo.expense.command;
import com.demo.expense.api.Command;
import com.demo.expense.enums.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExpenseCommand implements Command {
    private Long expenseId;          // The ID of the expense to update
    private Long userId;             // The ID of the user who owns the expense
    private String expenseName;      // The name of the expense
    private String expenseDescription; // A description of the expense
    private ExpenseType expenseType; // The type of the expense (e.g., FOOD, TRANSPORTATION, etc.)
    private Double amount;           // The amount of the expense
}
