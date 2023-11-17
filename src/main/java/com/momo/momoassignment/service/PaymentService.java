package com.momo.momoassignment.service;

import com.momo.momoassignment.common.CommonUtils;
import com.momo.momoassignment.common.ExceptionConstants;
import com.momo.momoassignment.model.Account;
import com.momo.momoassignment.model.Bill;

import java.util.NoSuchElementException;

public class PaymentService {

    private final Account account = Account.getInstance();

    public void pay(String billId) {
        try {

            Bill matchedBill = account.getBillById(Long.parseLong(billId)).orElseThrow();
            if (account.getDepositAmount() < matchedBill.getAmount()) {
                throw new IllegalArgumentException();
            }

            matchedBill.setPaid(true);
            account.setDepositAmount(account.getDepositAmount() - matchedBill.getAmount());
            System.out.printf("Payment has been completed for Bill with id %s.\n", billId);
            System.out.println("Your current balance is: " + account.getDepositAmount());

        } catch (NoSuchElementException e) {
            System.out.println(ExceptionConstants.ERR_LOG_BILL_NOT_FOUND_WITH_ID);
        } catch (IllegalArgumentException e) {
            System.out.println(ExceptionConstants.ERR_LOG_INSUFFICIENT_PAYMENT);
        } catch (Exception e) {
            System.err.println(ExceptionConstants.ERR_LOG_INVALID_ARGUMENT);
            System.err.println(e);
        }
    }

    public void listPayments() {
        CommonUtils.printPaymentTable(account.getPaymentHistory());
    }

}
