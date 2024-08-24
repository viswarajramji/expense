package com.demo.expense.query;

import com.demo.expense.api.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExpensesByUserIdQuery implements Query {
    private Long userId;
}
