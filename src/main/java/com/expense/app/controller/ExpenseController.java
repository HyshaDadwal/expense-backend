package com.expense.app.controller;

import com.expense.app.model.Expense;
import com.expense.app.model.User;
import com.expense.app.repository.UserRepository;
import com.expense.app.repository.ExpenseRepository;
import com.expense.app.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseRepository expenseRepository;

    // Add Expense
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        String email = getLoggedInUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        expense.setUserId(user.getId());
        return expenseService.addExpense(expense);
    }

    @Autowired
    private UserRepository userRepository;

    // Get Expenses by User ID
    @GetMapping
    public List<Expense> getExpenses() {
        String email = getLoggedInUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return expenseService.getExpensesByUser(user.getId());
    }

    @PutMapping("/{id}")
    public Expense updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDescription(updatedExpense.getDescription());
        expense.setDate(updatedExpense.getDate());

        return expenseRepository.save(expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUserId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        expenseRepository.deleteById(id);

        return "Deleted successfully";
    }

    private String getLoggedInUserEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    // Delete Expense
    //@DeleteMapping("/{id}")
    //public String deleteExpense(@PathVariable Long id) {
    //    expenseService.deleteExpense(id);
    //    return "Expense deleted successfully";
    //}
}