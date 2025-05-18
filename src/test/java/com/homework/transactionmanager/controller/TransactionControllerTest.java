package com.homework.transactionmanager.controller;

import com.homework.transactionmanager.entity.Transaction;
import com.homework.transactionmanager.enums.TransactionType;
import com.homework.transactionmanager.exception.ResourceNotFoundException;
import com.homework.transactionmanager.repository.TransactionRepository;
import com.homework.transactionmanager.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class TransactionControllerTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Test
    void createTransaction_ValidData_Success() {
        // 准备
        Transaction dto = new Transaction();
        dto.setDescription("TEST");
        dto.setType(TransactionType.WITHDRAWAL.name());
        dto.setDate(LocalDateTime.now());
        dto.setAmount(new BigDecimal("10.00"));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        // 执行
        Transaction result = transactionService.createTransaction(dto);

        // 验证
        assertNotNull(result);
        assertEquals(new BigDecimal("10.00"), result.getAmount());
        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    void getAllTransaction_NotFound_ThrowsException() {
        // 准备
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        // 验证
        assertThrows(ResourceNotFoundException.class,
                () -> transactionService.getTransactionById(99L));
    }

    @Test
    void updateTransaction_ValidData_Success() {
        Transaction dto = new Transaction();
        dto.setDescription("TEST");
        dto.setType(TransactionType.WITHDRAWAL.name());
        dto.setDate(LocalDateTime.now());
        dto.setAmount(new BigDecimal("10.00"));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        Transaction result = transactionService.updateTransaction(1L, dto);

        assertNotNull(result);
        assertEquals(new BigDecimal("10.00"), result.getAmount());
        verify(transactionRepository, times(1)).save(any());
    }

}
