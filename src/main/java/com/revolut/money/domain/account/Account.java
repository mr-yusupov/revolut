package com.revolut.money.domain.account;

import com.revolut.money.domain.money.Money;
import com.revolut.money.domain.transfer.TransferRequest;
import com.revolut.money.domain.transfer.TransferResult;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

import static com.revolut.money.domain.transfer.TransferResult.failed;
import static com.revolut.money.domain.transfer.TransferResult.success;

@Entity
public class Account {
    @Id
    private long id;
    @Embedded
    private Money balance;

    protected Account() {}

    public Account(long id, Money balance) {
        this.id = id;
        this.balance = balance;
    }

    public TransferResult transferFromAccount(TransferRequest request) {
        if (request.getSender().getId() != id) {
            return failed(request.getId(), "Request: " + request.getId() +
                    ". Failed. Wrong sender ID. " + "There was an attempt to transfer it from different account id: " + id);
        }
        if (balance.isLessThan(request.getAmount())) {
            return failed(request.getId(), "Request: " + request.getId() +
                    ". Failed. Insufficient funds. " + "Can't transfer " + request.getAmount() +
                    ". Current balance is " + balance);
        }

        balance = balance.subtract(request.getAmount());
        request.getReceiver().balance = request.getReceiver().getBalance().add(request.getAmount());

        return success(request.getId());
    }

    public Money getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance);
    }
}
