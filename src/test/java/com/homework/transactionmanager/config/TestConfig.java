package com.homework.transactionmanager.config;

import com.homework.transactionmanager.service.TransactionService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public TransactionService transactionService() {
        return Mockito.mock(TransactionService.class);
    }
}
