package com.revolut.money.controller.transferDto;

import com.google.gson.Gson;

import java.math.BigDecimal;

public class TransferRequestDto {
    private String requestId;
    private long senderId;
    private long receiverId;
    private BigDecimal amount;

    public TransferRequestDto() {}

    public TransferRequestDto(String requestId, long senderId, long receiverId, BigDecimal amount) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
