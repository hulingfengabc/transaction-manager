package com.homework.transactionmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The transaction type cannot be empty.")
    @Size(max = 20, message = "The transaction type cannot exceed 20 characters.")
    private String type;

    @NotNull(message = "The amount cannot be empty.")
    @Positive(message = "The amount must be a positive number.")
    private BigDecimal amount;

//    @FutureOrPresent(message = "The date cannot be a past date.")
    private LocalDateTime date;

    @Size(max = 255, message = "description cannot exceed 255 characters")
    private String description;

    // 辅助方法，用于判断两个交易是否重复
    public boolean isDuplicate(Transaction other) {
        return this.type.equals(other.type)
                && this.amount.equals(other.amount)
                && this.date.equals(other.date)
                && this.description.equals(other.description);
    }
}