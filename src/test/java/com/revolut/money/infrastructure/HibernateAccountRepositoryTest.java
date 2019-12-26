package com.revolut.money.infrastructure;

import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.money.Money;
import com.revolut.money.infrastructure.repository.HibernateAccountRepository;
import com.revolut.money.infrastructure.repository.HibernateSessionProvider;
import org.hibernate.Session;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.google.common.collect.ImmutableList.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HibernateAccountRepositoryTest {
    private HibernateAccountRepository repository = new HibernateAccountRepository();

    @Test
    public void shouldObtainAccountWhenItExistsInRepository() {
        Account givenAccount = new Account(new Random().nextLong(), new Money(new BigDecimal("10.00")));
        save(of(givenAccount));

        Optional<Account> result = repository.get(givenAccount.getId());

        assertTrue(result.isPresent());
        assertEquals(givenAccount.getId(), result.get().getId());
        assertEquals(givenAccount.getBalance(), result.get().getBalance());
    }

    private void save(List<Account> accounts) {
        try (Session session = HibernateSessionProvider.get().getSession()) {
            org.hibernate.Transaction transaction = session.beginTransaction();
            accounts.forEach(session::save);
            transaction.commit();
        }
    }
}
