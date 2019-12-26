package com.revolut.money.app.transaction;

import com.revolut.money.domain.transaction.Transaction;
import com.revolut.money.domain.transaction.TransactionRepository;

import static java.util.UUID.randomUUID;

public class TransactionHandler {
    private final TransactionRepository repository;

    public TransactionHandler(TransactionRepository repository) {
        this.repository = repository;
    }

    public String createNewTransaction() {
        Transaction transaction = new Transaction(randomUUID().toString());
        repository.save(transaction);
        return transaction.getId();
    }
}
