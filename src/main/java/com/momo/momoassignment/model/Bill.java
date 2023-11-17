package com.momo.momoassignment.model;

import java.time.LocalDate;

public class Bill {

    private long billId;
    private String type;
    private long amount;
    private LocalDate dueDate;
    private boolean isPaid;

    private String provider;

    public Bill(long billId, String type, long amount, LocalDate dueDate, boolean isPaid, String provider) {
        this.billId = billId;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.isPaid = isPaid;
        this.provider = provider;
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
