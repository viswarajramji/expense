package com.demo.expense.command;

import com.demo.expense.api.Command;
import com.demo.expense.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpenseCommand implements Command {
    @NotNull
    private Long userId;
    @NotNull
    @NotBlank
    private String expenseName;

    @NotNull
    private String expenseDescription;

    @NotNull
    private Category expenseType;

    @NotNull
    @Positive
    private Double amount;
}
