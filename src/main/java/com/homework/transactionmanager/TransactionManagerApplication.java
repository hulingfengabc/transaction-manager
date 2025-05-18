package com.homework.transactionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TransactionManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionManagerApplication.class, args);
	}

}
