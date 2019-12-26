package com.revolut.money.infrastructure.repository;

import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.account.AccountRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class HibernateAccountRepository implements AccountRepository {

    @Override
    public Optional<Account> get(long id) {
        try (Session session = HibernateSessionProvider.get().getSession()) {
            Account account = session.find(Account.class, id);

            return ofNullable(account);
        }
    }

    @Override
    public void save(Account account) {
        try (Session session = HibernateSessionProvider.get().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        }
    }
}
