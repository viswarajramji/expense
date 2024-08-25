package com.demo.expense.command;
import com.demo.expense.api.Command;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteExpenseCommand implements Command {
    @NotNull
    private Long expenseId;
}

