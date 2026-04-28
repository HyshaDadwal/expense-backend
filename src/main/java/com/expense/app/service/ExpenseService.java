package com.expense.app.service;

import com.expense.app.model.Expense;
import com.expense.app.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    // Add Expense
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Get Expenses by User
    public List<Expense> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    // Delete Expense
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}