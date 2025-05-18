package com.homework.transactionmanager.service;

import com.homework.transactionmanager.exception.DuplicateTransactionException;
import com.homework.transactionmanager.exception.TransactionNotFoundException;
import com.homework.transactionmanager.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    /**
     * create transaction
     * @param transaction
     * @return
     * @throws DuplicateTransactionException
     */
    Transaction createTransaction(Transaction transaction) throws DuplicateTransactionException;

    /**
     * transaction query with page
     * @return
     */
    Page<Transaction> getAllTransactions(Pageable pageable);


    /**
     * query single transaction By id
     * @param id
     * @return
     * @throws TransactionNotFoundException
     */
    Transaction getTransactionById(Long id) throws TransactionNotFoundException;

    /**
     * update transaction
     * @param id
     * @param transactionDetails
     * @return
     * @throws TransactionNotFoundException
     * @throws DuplicateTransactionException
     */
    Transaction updateTransaction(Long id, Transaction transactionDetails) throws TransactionNotFoundException, DuplicateTransactionException;

    /**
     * delete transaction
     * @param id
     * @throws TransactionNotFoundException
     */
    void deleteTransaction(Long id) throws TransactionNotFoundException;
}