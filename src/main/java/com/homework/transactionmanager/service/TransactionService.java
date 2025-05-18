package com.homework.transactionmanager.service;

import com.homework.transactionmanager.exception.DuplicateTransactionException;
import com.homework.transactionmanager.exception.TransactionNotFoundException;
import com.homework.transactionmanager.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    // 创建新交易
    Transaction createTransaction(Transaction transaction) throws DuplicateTransactionException;

    Page<Transaction> getAllTransactions(Pageable pageable);

    // 获取所有交易（带分页）
    List<Transaction> getAllTransactions();

    // 获取单个交易
    Transaction getTransactionById(Long id) throws TransactionNotFoundException;

    // 更新交易
    Transaction updateTransaction(Long id, Transaction transactionDetails) throws TransactionNotFoundException, DuplicateTransactionException;

    // 删除交易
    void deleteTransaction(Long id) throws TransactionNotFoundException;
}