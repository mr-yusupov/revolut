package com.revolut.money.infrastructure.repository;

import com.revolut.money.domain.transfer.Transfer;
import com.revolut.money.domain.transfer.TransferRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTransferRepository implements TransferRepository {

    @Override
    public void save(Transfer transfer) {
        try (Session session = HibernateSessionProvider.get().getSession()) {

            Transaction transaction = session.beginTransaction();

            session.update(transfer.getRequest().getTransaction());

            if (transfer.getResult().isSuccessful()) {
                session.update(transfer.getRequest().getSender());
                session.update(transfer.getRequest().getReceiver());
            }

            transaction.commit();
        }
    }
}
