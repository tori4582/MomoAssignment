package com.momo.momoassignment.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Account {

    private static Account INSTANCE;

    private long depositAmount;

    private List<Bill> bills;

    private List<Payment> paymentHistory;

    private Account() {
        this.depositAmount = 0;
        this.bills = List.of(
                new Bill(1, "ELECTRIC", 200000, LocalDate.of(2020, 10, 25), false, "EVN HCMC"),
                new Bill(2, "WATER", 175000, LocalDate.of(2020, 10, 30), false, "EVN HCMC"),
                new Bill(3, "INTERNET", 800000, LocalDate.of(2020, 11, 30), false, "VNPT")
        );
        paymentHistory = new ArrayList<>();
    }

    public static Account getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Account();
        }
        return INSTANCE;
    }

    public long getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public List<Payment> getPaymentHistory() {
        return paymentHistory;
    }

    public void setPaymentHistory(List<Payment> paymentHistory) {
        this.paymentHistory = paymentHistory;
    }

    public Optional<Bill> getBillById(long id) {
        return getBills().stream()
                .filter(bill -> bill.getBillId() == id)
                .findFirst();
    }

    public Bill removeBillById(long id) {
        for (int i = 0; i < getBills().size(); i++) {
            if (bills.get(i).getBillId() == id) {
                return bills.remove(i);
            }
        }
        return null;
    }

}
