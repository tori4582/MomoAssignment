package com.momo.momoassignment.model;

import java.time.LocalDate;

public class Payment {
    private long amount;
    private LocalDate date;
    private String status;
    private long billId;

    public Payment(long amount, LocalDate date, String status, long billId) {
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.billId = billId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }
}
