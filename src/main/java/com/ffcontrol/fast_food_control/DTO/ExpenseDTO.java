package com.ffcontrol.fast_food_control.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Long expenseId;
    private LocalDateTime expenseDate;
    private String type;
    private String concept;
    private Double totalPrice;
}
