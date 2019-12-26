package com.revolut.money.app.account;

import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.money.Money;
import com.revolut.money.domain.transaction.Transaction;
import com.revolut.money.domain.transfer.TransferResult;
import com.revolut.money.infrastructure.repository.HibernateAccountRepository;
import com.revolut.money.infrastructure.repository.HibernateSessionProvider;
import com.revolut.money.infrastructure.repository.HibernateTransactionRepository;
import com.revolut.money.infrastructure.repository.HibernateTransferRepository;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;

public class DefaultTransferHandlerTest {

    @Test
    public void shouldTransferMoneyWhenBothAccountsExist() {
        Account sender = new Account(new Random().nextLong(), new Money(TEN));
        Account receiver = new Account(new Random().nextLong(), new Money(ZERO));
        save(asList(sender, receiver));

        HibernateTransactionRepository  transactionRepository = new HibernateTransactionRepository();
        Transaction transaction = new Transaction(randomUUID().toString());
        transactionRepository.save(transaction);

        HibernateAccountRepository accountRepository = new HibernateAccountRepository();
        DefaultTransferHandler handler = new DefaultTransferHandler(accountRepository, transactionRepository, new HibernateTransferRepository());

        TransferResult result = handler.transfer(transaction.getId(), sender.getId(), receiver.getId(), TEN);

        Assert.assertTrue(result.isSuccessful());
    }

    private void save(List<Account> accounts) {
        try (Session session = HibernateSessionProvider.get().getSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            accounts.forEach(session::save);
            transaction.commit();
        }
    }
}
