package com.momo.momoassignment.service;

import com.momo.momoassignment.common.ExceptionConstants;
import com.momo.momoassignment.model.Account;

public class AccountService {

    private final Account account = Account.getInstance();

    public long cashIn(String amount) {
        try {
            account.setDepositAmount(account.getDepositAmount() + Long.parseLong(amount));
            return account.getDepositAmount();
        } catch (Exception e) {
            System.err.println(ExceptionConstants.ERR_LOG_INVALID_ARGUMENT);
            System.err.println("Failed to deposit cash");
            System.err.println(e);
            return -1;
        }
    }


}
