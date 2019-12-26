package com.revolut.money.infrastructure;

import com.revolut.money.domain.transaction.Transaction;
import com.revolut.money.infrastructure.repository.HibernateTransactionRepository;
import org.junit.Assert;
import org.junit.Test;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.UUID.randomUUID;

public class HibernateTransactionRepositoryTest {

    @Test
    public void shouldConfirmTransactionIsPresentInRepository() {
        HibernateTransactionRepository repository = new HibernateTransactionRepository();
        Transaction request = new Transaction(randomUUID().toString());

        repository.save(request);

        Assert.assertEquals(of(request), repository.get(request.getId()));
    }

    @Test
    public void shouldConfirmNoGivenTransactionExitsInRepository() {
        HibernateTransactionRepository repository = new HibernateTransactionRepository();

        Assert.assertEquals(empty(), repository.get(randomUUID().toString()));
    }
}
