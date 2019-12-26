package com.revolut.money.domain.transfer;

import org.junit.Test;

import static com.revolut.money.domain.transfer.TransferResult.failed;
import static com.revolut.money.domain.transfer.TransferResult.success;
import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransferResultTest {

    @Test
    public void shouldCreateSuccessfulTransferResultWithNoDescription() {
        String givenRequestId = randomUUID().toString();

        TransferResult transferResult = success(givenRequestId);

        assertTrue(transferResult.isSuccessful());
        assertEquals(transferResult.getRequestId(), givenRequestId);
        assertEquals(transferResult.getDetails(), "");
    }

    @Test
    public void shouldCreateFailedTransferResultWithDescription() {
        String givenRequestId = randomUUID().toString();
        String givenDetails = "Not enough founds";

        TransferResult transferResult = failed(givenRequestId, givenDetails);

        assertTrue(!transferResult.isSuccessful());
        assertEquals(transferResult.getRequestId(), givenRequestId);
        assertEquals(transferResult.getDetails(), givenDetails);
    }
}
