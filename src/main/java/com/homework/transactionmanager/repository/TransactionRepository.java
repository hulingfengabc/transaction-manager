package com.homework.transactionmanager.repository;

import com.homework.transactionmanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTypeAndAmountAndDateAndDescription(
            String type, BigDecimal amount, LocalDateTime datetime, String description);
}