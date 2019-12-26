package com.revolut.money.domain.transfer;

public class Transfer {
    private final TransferRequest request;
    private final TransferResult result;

    public Transfer(TransferRequest request, TransferResult result) {
        this.request = request;
        this.result = result;
    }

    public TransferRequest getRequest() {
        return request;
    }

    public TransferResult getResult() {
        return result;
    }
}
