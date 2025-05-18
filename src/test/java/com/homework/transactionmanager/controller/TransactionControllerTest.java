package com.homework.transactionmanager.controller;

import com.homework.transactionmanager.TransactionManagerApplication;
import com.homework.transactionmanager.config.TestConfig;
import com.homework.transactionmanager.entity.Transaction;
import com.homework.transactionmanager.enums.TransactionType;
import com.homework.transactionmanager.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import({TestConfig.class, TransactionManagerApplication.class})
@AutoConfigureWebTestClient
public class TransactionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TransactionService transactionService;

    private Transaction testTransaction;

    @BeforeEach
    void setUp() {
        testTransaction = new Transaction();
        testTransaction.setType(TransactionType.DEPOSIT.name());
        testTransaction.setAmount(new BigDecimal("100.00"));
        testTransaction.setDate(LocalDateTime.now());
        testTransaction.setDescription("Test");
    }

    @Test
    void createTransaction() {
        webTestClient.post().uri("/api/transactions/add")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testTransaction)
                .exchange()
                .expectStatus().isCreated()
                .expectBody();
    }

    @Test
    void updateTransaction_ShouldReturnUpdatedTransaction() throws Exception {

        webTestClient.post().uri("/api/transactions/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testTransaction)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(testTransaction.getId())
                .jsonPath("$.amount").isEqualTo(testTransaction.getAmount().doubleValue());
    }
}
