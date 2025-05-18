package com.homework.transactionmanager.controller;

import com.homework.transactionmanager.entity.Transaction;
import com.homework.transactionmanager.enums.TransactionType;
import com.homework.transactionmanager.exception.DuplicateTransactionException;
import com.homework.transactionmanager.repository.TransactionRepository;
import com.homework.transactionmanager.service.TransactionService;
import com.homework.transactionmanager.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("TransactionServiceTest")
public class TransactionServiceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    @Transactional
//    public void testCreateTransaction_Success() throws DuplicateTransactionException {
//        Transaction transaction = new Transaction();
//        transaction.setType(TransactionType.DEPOSIT.name());
//        transaction.setAmount(BigDecimal.valueOf(1000));
//        transaction.setDate(LocalDateTime.now());
//
//        when(transactionRepository.findByTypeAndAmountAndDateAndDescription(
//                eq(TransactionType.DEPOSIT.name()),
//                eq(BigDecimal.valueOf(1000)),
//                eq(transaction.getDate()),
//                eq("test1"))
//        ).thenReturn(Optional.empty());
//
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
//
//        Transaction createdTransaction = transactionService.createTransaction(transaction);
//
//        assertNotNull(createdTransaction);
//        assertEquals(TransactionType.DEPOSIT, createdTransaction.getType());
//        assertEquals(BigDecimal.valueOf(1000), createdTransaction.getAmount());
//        verify(transactionRepository, times(1)).save(transaction);
//    }
//
//    @Test
//    @Transactional
//    public void testCreateTransaction_Sucess() {
//        Transaction transaction = new Transaction();
//        transaction.setType(TransactionType.DEPOSIT.name());
//        transaction.setAmount(BigDecimal.valueOf(1000));
//        transaction.setDate(LocalDateTime.now());
//
//        transaction = transactionService.createTransaction(transaction);
//
//        assertThat(transaction).isNotNull();
//        assertThat(transaction.getId()).isNotNull();
//
//    }


}
