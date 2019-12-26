package com.revolut.money.app.account;

import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.account.AccountRepository;
import com.revolut.money.domain.money.Money;
import com.revolut.money.domain.transaction.Transaction;
import com.revolut.money.domain.transaction.TransactionRepository;
import com.revolut.money.domain.transfer.Transfer;
import com.revolut.money.domain.transfer.TransferRepository;
import com.revolut.money.domain.transfer.TransferRequest;
import com.revolut.money.domain.transfer.TransferResult;

import java.math.BigDecimal;
import java.util.Optional;

import static com.revolut.money.domain.transfer.TransferResult.failed;

public class DefaultTransferHandler implements TransferHandler {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransferRepository transferRepository;

    public DefaultTransferHandler(AccountRepository accountRepository, TransactionRepository transactionRepository,
                                       TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public TransferResult transfer(String transactionId, long senderId, long receiverId, BigDecimal transferAmount) {
        Optional<Transaction> transaction = transactionRepository.get(transactionId);
        if (!transaction.isPresent()) {
            return failed(transactionId, "Transaction is not present. Please create transaction before transferring.");
        } else if (transaction.get().isCompleted()) {
            return failed(transactionId, "Transaction have been already completed.");
        }

        Optional<Account> senderAccount = accountRepository.get(senderId);
        Optional<Account> receiverAccount = accountRepository.get(receiverId);
        if (senderAccount.isPresent() && receiverAccount.isPresent()) {
            return transfer(transaction.get(), senderAccount.get(), receiverAccount.get(), new Money(transferAmount));
        } else {
            return failed(transactionId, "One of the accounts " + senderId + " or " + receiverId + " is not found");
        }
    }

    private TransferResult transfer(Transaction transaction, Account sender, Account receiver, Money transferAmount) {
        TransferRequest transferRequest = new TransferRequest(transaction, sender, receiver, transferAmount);

        TransferResult transferResult = sender.transferFromAccount(transferRequest);
        transaction.complete();

        transferRepository.save(new Transfer(transferRequest, transferResult));

        return transferResult;
    }
}
