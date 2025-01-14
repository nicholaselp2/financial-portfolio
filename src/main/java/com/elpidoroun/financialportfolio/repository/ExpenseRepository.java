package com.elpidoroun.financialportfolio.repository;

import com.elpidoroun.financialportfolio.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByExpense(String expense);
}
