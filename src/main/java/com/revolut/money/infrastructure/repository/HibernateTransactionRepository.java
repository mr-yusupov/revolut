package com.revolut.money.infrastructure.repository;

import com.revolut.money.domain.transaction.Transaction;
import com.revolut.money.domain.transaction.TransactionRepository;
import org.hibernate.Session;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class HibernateTransactionRepository implements TransactionRepository {

    @Override
    public Optional<Transaction> get(String transactionId) {
        try (Session session = HibernateSessionProvider.get().getSession()) {
            Transaction transaction = session.find(Transaction.class, transactionId);
            return ofNullable(transaction);
        }
    }

    @Override
    public void save(Transaction transaction) {
        try (Session session = HibernateSessionProvider.get().getSession()) {
            org.hibernate.Transaction hibernateTransaction = session.beginTransaction();

            session.save(transaction);

            hibernateTransaction.commit();
        }
    }
}
