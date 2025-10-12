package com.ffcontrol.fast_food_control.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ffcontrol.fast_food_control.DTO.ExpenseDTO;
import com.ffcontrol.fast_food_control.service.ExpensesService;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {
    @Autowired
    private ExpensesService expensesService;

    @PostMapping("/create-expense")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO request) {
        return ResponseEntity.ok(expensesService.createExpense(request));
    }

    @PutMapping("/edit-expense")
    public ResponseEntity<ExpenseDTO> editExpense(@RequestParam Long expenseId, @RequestBody ExpenseDTO request) {
        return ResponseEntity.ok(expensesService.editExpense(expenseId, request));
    }

    @GetMapping("/get-all-expenses")
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expensesService.getAllExpenses());
    }

    @GetMapping("/get-expense-by-id")
    public ResponseEntity<ExpenseDTO> getExpenseById(@RequestParam Long expenseId) { 
        return ResponseEntity.ok(expensesService.getExpenseById(expenseId));
    }

    @DeleteMapping("/delete-expense")
    public ResponseEntity<String> deleteExpense(@RequestParam Long expenseId) {
        expensesService.deleteExpense(expenseId);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/get-expenses-by-day")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDay(@RequestParam LocalDate date) {
        return ResponseEntity.ok(expensesService.getExpensesByDay(date));
    }

    @GetMapping("/get-expenses-by-date-range")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByDateRange(@RequestParam LocalDateTime startDate, 
                                                                    @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(expensesService.getExpensesByDateRange(startDate, endDate));
    }
}
