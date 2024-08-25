package com.demo.expense.query;

import com.demo.expense.api.Query;
import com.demo.expense.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExpensesByExpenseTypeAndUserIdQuery implements Query {
    private Long userId;
    private Category expenseType;
}
