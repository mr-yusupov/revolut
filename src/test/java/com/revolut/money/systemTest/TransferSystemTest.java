package com.revolut.money.systemTest;

import com.revolut.money.AppStarter;
import com.revolut.money.controller.transferDto.TransferRequestDto;
import com.revolut.money.controller.transferDto.TransferResponseDto;
import com.revolut.money.domain.account.Account;
import com.revolut.money.domain.money.Money;
import com.revolut.money.infrastructure.repository.HibernateAccountRepository;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

import static com.revolut.money.AppStarter.PORT;
import static com.revolut.money.controller.transferDto.TransferResponseDto.fromJson;
import static com.revolut.money.systemTest.HttpUtils.*;
import static java.math.BigDecimal.TEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

public class TransferSystemTest {
    private static final String ACCOUNT_BASE_URL = "http://localhost:" + PORT + "/account/";

    private AppStarter application = new AppStarter();

    private Account accountWithMoney = new Account(new Random().nextLong(), new Money(new BigDecimal("15.00")));
    private Account accountWithoutMoney = new Account(new Random().nextLong(), new Money(new BigDecimal("0.00")));

    @Before
    public void setUp() throws Exception {
        application.start(PORT, () -> {
            new HibernateAccountRepository().save(accountWithMoney);
            new HibernateAccountRepository().save(accountWithoutMoney);
        });
    }

    @After
    public void shutdown() throws Exception {
        application.stop();
    }

    @Test
    public void shouldQueryAccountBalance() throws IOException {
        HttpResponse response = get(ACCOUNT_BASE_URL + accountWithMoney.getId() + "/balance");

        assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        assertEquals(new BigDecimal(contentOf(response)), accountWithMoney.getBalance().getAmount());
    }

    @Test
    public void shouldSuccessfullyTransferMoneyWhenEnoughAmountIsAvailable() throws Exception {
        String transactionId = contentOf(post("http://localhost:" + PORT + "/transaction"));

        BigDecimal transferAmount = TEN;
        String request = new TransferRequestDto(
                transactionId, accountWithMoney.getId(), accountWithoutMoney.getId(), transferAmount
        ).toJson();

        System.out.println(request);

        HttpResponse response = post(ACCOUNT_BASE_URL + "transfer", request);

        assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        TransferResponseDto transferResponseDto = fromJson(contentOf(response));
        assertTrue(transferResponseDto.isSuccessful());

        String balanceOfSender = contentOf(get(ACCOUNT_BASE_URL + accountWithMoney.getId() + "/balance"));
        assertEquals(new BigDecimal(balanceOfSender), accountWithMoney.getBalance().getAmount().subtract(transferAmount));

        String balanceOfReceiver = contentOf(get(ACCOUNT_BASE_URL + accountWithoutMoney.getId() + "/balance"));
        assertEquals(new BigDecimal(balanceOfReceiver), accountWithoutMoney.getBalance().getAmount().add(transferAmount));
    }

    @Test
    public void shouldFailToTransferWhenAmountIsNotEnoughInSendersAccount() throws IOException {
        String transactionId = contentOf(post("http://localhost:" + PORT + "/transaction"));

        String request = new TransferRequestDto(
                transactionId, accountWithoutMoney.getId(), accountWithMoney.getId(), TEN
        ).toJson();

        HttpResponse response = post(ACCOUNT_BASE_URL + "transfer", request);

        assertEquals(response.getStatusLine().getStatusCode(), SC_OK);

        TransferResponseDto transferResponseDto = fromJson(contentOf(response));
        assertFalse(transferResponseDto.isSuccessful());
        assertTrue(transferResponseDto.getDetails().contains("Insufficient funds"));
    }

    @Test
    public void shouldFailToTransferWithoutCorrectTransaction() throws IOException {
        String request = new TransferRequestDto(
                UUID.randomUUID().toString(), accountWithMoney.getId(), accountWithoutMoney.getId(), TEN
        ).toJson();

        HttpResponse response = post(ACCOUNT_BASE_URL + "transfer", request);

        assertEquals(response.getStatusLine().getStatusCode(), SC_OK);

        TransferResponseDto transferResponseDto = fromJson(contentOf(response));
        assertFalse(transferResponseDto.isSuccessful());
        assertTrue(transferResponseDto.getDetails().contains("Transaction is not present. Please create transaction before transferring."));
    }
}
