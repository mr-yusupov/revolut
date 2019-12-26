package com.revolut.money.app.account;

import com.revolut.money.domain.transfer.TransferResult;

import java.math.BigDecimal;

public interface TransferHandler {
    TransferResult transfer(String transactionId, long senderId, long receiverId, BigDecimal transferAmount);
}
