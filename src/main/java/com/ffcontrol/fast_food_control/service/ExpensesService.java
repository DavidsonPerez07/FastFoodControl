package com.ffcontrol.fast_food_control.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.ExpenseDTO;
import com.ffcontrol.fast_food_control.entity.Expense;
import com.ffcontrol.fast_food_control.repository.ExpenseRepository;

@Service
public class ExpensesService {
    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ExpenseDTO createExpense(ExpenseDTO request) {
        Expense expense = convertToEntity(request);

        if (expense.getTotalPrice() <= 0) {
            throw new RuntimeException("Total price must be greater than zero");
        }

        expense = expenseRepository.save(expense);
        return convertToDTO(expense);
    }

    public ExpenseDTO editExpense(Long expenseId, ExpenseDTO request) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (request.getExpenseDate() != null) {
            expense.setExpenseDate(request.getExpenseDate());
        }
        if (request.getType() != null && !request.getType().isEmpty()) {
            expense.setType(request.getType());
        }
        if (request.getConcept() != null && !request.getConcept().isEmpty()) {
            expense.setConcept(request.getConcept());
        }
        if (request.getTotalPrice() != null && request.getTotalPrice() > 0) {
            expense.setTotalPrice(request.getTotalPrice());
        }

        expense = expenseRepository.save(expense);

        return convertToDTO(expense);
    }

    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAllByOrderByExpenseDateDesc()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ExpenseDTO getExpenseById(Long expenseId) {
        return convertToDTO(expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found")));
    }

    public void deleteExpense(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new RuntimeException("Expense not found");
        }

        expenseRepository.deleteById(expenseId);
    }

    public List<ExpenseDTO> getExpensesByDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return expenseRepository.findByExpenseDateBetween(startOfDay, endOfDay)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ExpenseDTO> getExpensesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findByExpenseDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public ExpenseDTO convertToDTO(Expense expense) {
        return modelMapper.map(expense, ExpenseDTO.class);
    }

    public Expense convertToEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, Expense.class);
    }
}
