package com.demo.expense.repo;

import com.demo.expense.enums.ExpenseType;
import com.demo.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    List<Expense> findByUserIdAndExpenseType(Long userId, ExpenseType expenseType);
}
