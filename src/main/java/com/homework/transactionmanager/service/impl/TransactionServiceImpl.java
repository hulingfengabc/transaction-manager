package com.homework.transactionmanager.service.impl;

import com.homework.transactionmanager.config.TransactionMetrics;
import com.homework.transactionmanager.exception.ResourceNotFoundException;
import com.homework.transactionmanager.entity.Transaction;
import com.homework.transactionmanager.repository.TransactionRepository;
import com.homework.transactionmanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMetrics transactionMetrics;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMetrics transactionMetrics) {
        this.transactionRepository = transactionRepository;
        this.transactionMetrics = transactionMetrics;
    }

    @Override
    @Cacheable("transactions")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Cacheable(value = "transactions", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Transaction> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("transaction not exist，ID: " + id));
    }

    @Override
    @CacheEvict(value = {"transactions"}, allEntries = true)
    public Transaction createTransaction(Transaction transaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findByTypeAndAmountAndDateAndDescription(
                transaction.getType(), transaction.getAmount(), transaction.getDate(), transaction.getDescription());

        if (existingTransaction.isPresent()) {
            throw new IllegalArgumentException("same transaction records!");
        }

        transactionMetrics.incrementTransactionCounter();
        return transactionRepository.save(transaction);
    }

    @Override
    @CacheEvict(value = {"transactions"}, allEntries = true)
    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("transaction not exist，ID: " + id));

        transaction.setType(transactionDetails.getType());
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setDate(transactionDetails.getDate());
        transaction.setDescription(transactionDetails.getDescription());

        return transactionRepository.save(transaction);
    }

    @Override
    @CacheEvict(value = {"transactions"}, allEntries = true)
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("transaction not exist，ID: " + id);
        }
        transactionRepository.deleteById(id);
    }
}