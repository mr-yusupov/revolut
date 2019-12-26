package com.revolut.money.app.account;

import com.revolut.money.infrastructure.repository.HibernateAccountRepository;
import com.revolut.money.infrastructure.repository.HibernateTransactionRepository;
import com.revolut.money.infrastructure.repository.HibernateTransferRepository;

public class TransferHandlerFactory {
    private static TransferHandler handler;

    public static synchronized TransferHandler create() {
        if (handler == null) {
            handler = new ThreadSafeTransferHandlerDecorator(new DefaultTransferHandler(
                    new HibernateAccountRepository(),
                    new HibernateTransactionRepository(),
                    new HibernateTransferRepository())
            );
        }

        return handler;
    }
}
