package com.revolut.money.app.account;

import com.revolut.money.domain.transfer.TransferResult;
import de.jkeylockmanager.manager.KeyLockManager;

import java.math.BigDecimal;

import static de.jkeylockmanager.manager.KeyLockManagers.newLock;

public class ThreadSafeTransferHandlerDecorator implements TransferHandler {
    private final TransferHandler delegate;
    private final KeyLockManager lockManager = newLock();

    ThreadSafeTransferHandlerDecorator(TransferHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public TransferResult transfer(String transactionId, long senderId, long receiverId, BigDecimal transferAmount) {
        long firstLockKey = senderId > receiverId ? senderId : receiverId;
        long secondLockKey = firstLockKey == senderId ? receiverId : senderId;

        return lockManager.executeLocked(firstLockKey, () -> lockManager
                .executeLocked(secondLockKey, () -> delegate
                        .transfer(transactionId, senderId, receiverId, transferAmount))
        );
    }
}
