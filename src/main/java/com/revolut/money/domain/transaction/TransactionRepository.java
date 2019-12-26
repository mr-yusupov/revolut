package com.revolut.money.domain.transaction;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> get(String transactionId);

    void save(Transaction transaction);
}
