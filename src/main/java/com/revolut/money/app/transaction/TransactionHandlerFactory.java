package com.revolut.money.app.transaction;

import com.revolut.money.infrastructure.repository.HibernateTransactionRepository;

public class TransactionHandlerFactory {

    public static synchronized TransactionHandler create() {
        return new TransactionHandler(new HibernateTransactionRepository());
    }
}
