package com.demo.expense.model;


import com.demo.expense.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private Long userId;
    private String expenseName;
    private String expenseDescription;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    private Double amount;

}

