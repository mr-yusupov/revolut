package com.revolut.money.domain.account;

import com.revolut.money.domain.money.Money;
import com.revolut.money.domain.transaction.Transaction;
import com.revolut.money.domain.transfer.TransferRequest;
import com.revolut.money.domain.transfer.TransferResult;
import org.junit.Test;

import java.util.Random;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    @Test
    public void shouldConfirmAccountHasGivenBalance() {
        Money money = new Money(TEN);
        long givenAccountId = 1L;
        Account account = new Account(givenAccountId, money);

        assertEquals(account.getBalance(), money);
        assertEquals(account.getId(), givenAccountId);
    }

    @Test
    public void shouldBeAbleToTransferWhenAccountHasEnoughMoney() {
        Money sendersInitialBalance = new Money(TEN);
        Account sender = new Account(new Random().nextLong(), sendersInitialBalance);

        Money receiversInitialBalance = new Money(ZERO);
        Account receiver = new Account(new Random().nextLong(), receiversInitialBalance);

        Money transferAmount = new Money(TEN);
        TransferRequest request = new TransferRequest(new Transaction(randomUUID().toString()), sender, receiver, transferAmount);

        TransferResult result = sender.transferFromAccount(request);

        assertTrue(result.isSuccessful());
        assertEquals(sender.getBalance(), sendersInitialBalance.subtract(transferAmount));
        assertEquals(receiver.getBalance(), receiversInitialBalance.add(transferAmount));
    }

    @Test
    public void shouldNotBeAbleToTransferWhenAccountHasNotEnoughMoney() {
        Money sendersInitialBalance = new Money(ZERO);
        Account sender = new Account(new Random().nextLong(), sendersInitialBalance);

        Money receiversInitialBalance = new Money(TEN);
        Account receiver = new Account(new Random().nextLong(), receiversInitialBalance);

        Money amountOfTransfer = new Money(TEN);

        TransferRequest request = new TransferRequest(new Transaction(randomUUID().toString()), sender, receiver, amountOfTransfer);
        TransferResult result = sender.transferFromAccount(request);

        assertTrue(!result.isSuccessful());
        assertEquals(sender.getBalance(), sendersInitialBalance);
        assertEquals(receiver.getBalance(), receiversInitialBalance);
    }
}
