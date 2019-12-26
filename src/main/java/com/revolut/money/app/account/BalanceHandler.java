package com.revolut.money.app.account;

import com.revolut.money.domain.account.AccountRepository;

public class BalanceHandler {
    private final AccountRepository accountRepository;

    BalanceHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getBalance(long accountId) {
        return accountRepository.get(accountId)
                .map(account -> account.getBalance().getAmount().toString())
                .orElse("Account with ID:" + accountId + " doesn't exist.");
    }
}
