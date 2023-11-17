package com.momo.momoassignment.common;

import com.momo.momoassignment.model.Bill;
import com.momo.momoassignment.model.Payment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommonUtils {

    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static void printBillTable(List<Bill> bills) {

        final var formatPattern = "%-10s%-10s%-10s%-12s%-10s%-10s\n";

        if (bills == null || bills.isEmpty()) {
            System.out.println("No bill to print out");
            return;
        }
        System.out.format(formatPattern, "Bill No.", "Type", "Amount", "Due Date", "Status", "Provider");
        for (Bill bill : bills) {
            System.out.format(
                formatPattern,
                bill.getBillId(),
                bill.getType(),
                bill.getAmount(),
                formatDate(bill.getDueDate()),
                bill.isPaid() ? "PAID" : "NOT_PAID",
                bill.getProvider()
            );
        }
    }

    public static void printPaymentTable(List<Payment> payments) {

        final var formatPattern = "%-10s%-10s%-10s%-10ss%-10s\n";

        if (payments == null || payments.isEmpty()) {
            System.out.println("No payment to print out");
            return;
        }
        System.out.format(formatPattern, "No.", "Amount", "Payment Date", "State", "Bill Id");
        int counter = 1;
        for (var payment : payments) {
            System.out.format(
                    formatPattern,
                    counter,
                    payment.getAmount(),
                    formatDate(payment.getDate()),
                    payment.getStatus(),
                    payment.getBillId()
            );
            counter += 1;
        }
    }



}
