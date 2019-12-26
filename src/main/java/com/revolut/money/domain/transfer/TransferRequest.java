package com.revolut.money.domain.transfer;

import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.money.Money;
import com.revolut.money.domain.transaction.Transaction;

public class TransferRequest {
    private Transaction transaction;
    private Account sender;
    private Account receiver;
    private Money amount;

    public TransferRequest(Transaction transaction, Account sender, Account receiver, Money amount) {
        this.transaction = transaction;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public Money getAmount() {
        return amount;
    }

    public String getId() {
        return transaction.getId();
    }
}
