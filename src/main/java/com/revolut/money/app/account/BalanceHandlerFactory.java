package com.revolut.money.app.account;

import com.revolut.money.infrastructure.repository.HibernateAccountRepository;

public class BalanceHandlerFactory {
    private static BalanceHandler handler;

    public synchronized static BalanceHandler create() {
        if (handler == null) {
            handler = new BalanceHandler(new HibernateAccountRepository());
        }
        return handler;
    }
}
