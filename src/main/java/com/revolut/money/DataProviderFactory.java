package com.revolut.money;

import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.money.Money;
import com.revolut.money.infrastructure.repository.HibernateAccountRepository;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;

public class DataProviderFactory implements DataProvider {

    @Override
    public void prepareSampleData() {
        HibernateAccountRepository repository = new HibernateAccountRepository();
        repository.save(new Account(1L, new Money(ZERO)));
        repository.save(new Account(2L, new Money(TEN)));
    }
}
