package com.demo.expense.model;


import com.demo.expense.enums.Category;
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

    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String expenseName;

    private String expenseDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category expenseType;

    @Column(nullable = false)
    private Double amount;

}

