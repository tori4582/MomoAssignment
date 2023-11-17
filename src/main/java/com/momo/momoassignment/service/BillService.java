package com.momo.momoassignment.service;

import com.momo.momoassignment.common.CommonUtils;
import com.momo.momoassignment.common.ExceptionConstants;
import com.momo.momoassignment.model.Account;
import com.momo.momoassignment.model.Bill;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;

import static java.util.function.Predicate.not;

public class BillService {

    private final Account account = Account.getInstance();

    public void getAllBill() {
        CommonUtils.printBillTable(account.getBills());
    }

    public void getNotPaidBill() {
        CommonUtils.printBillTable(
                account.getBills().stream().filter(not(Bill::isPaid)).toList()
        );
    }

//    public void schedule(String billId, String newDate) {
//
//    }

    public void getBillsByProviders(String providerName) {
        CommonUtils.printBillTable(
                account.getBills().stream()
                        .filter(bill -> providerName.equals(bill.getProvider()))
                        .toList()
        );
    }

    public void createBillProcedure(String billId,
                                    String type,
                                    String amount,
                                    String dueDate,
                                    String isPaid,
                                    String provider) {
        try {
            final var preparingBill = new Bill(
                    Long.parseLong(billId),
                    type,
                    Long.parseLong(amount),
                    LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    processIsPaid(isPaid),
                    provider
            );
            account.getBills().add(preparingBill);
        } catch (Exception e) {
            System.err.println(ExceptionConstants.ERR_LOG_INVALID_ARGUMENT);
            System.err.println(e);
        }
    }

    public void viewBillDetails(String billId) {
        try {
            Bill matchedBill = account.getBillById(Long.parseLong(billId)).orElseThrow();
            CommonUtils.printBillTable(List.of(matchedBill));
        } catch (NoSuchElementException e) {
            System.err.println(ExceptionConstants.ERR_LOG_BILL_NOT_FOUND_WITH_ID);
        } catch (Exception e) {
            System.err.println(ExceptionConstants.ERR_LOG_INVALID_ARGUMENT);
            System.err.println(e);
        }
    }

    public void updateBillDetails(String billId, String updateField, String newValue) {

        final Map<String, BiConsumer<Bill, String>> updatableField = Map.of(
                "TYPE", Bill::setType,
                "PROVIDER", Bill::setProvider,
                "AMOUNT", (Bill b, String v) -> b.setAmount(Long.parseLong(v)),
                "ISPAID", (Bill b, String v) -> b.setPaid(processIsPaid(v)),
                "DUEDATE", (Bill b, String v) -> b.setDueDate(
                        LocalDate.parse(v, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                )
        );

        try {
            Bill matchedBill = account.getBillById(Long.parseLong(billId)).orElseThrow();

            if (!updatableField.containsKey(updateField)) {
                throw new IllegalAccessException("Attempting to update invalid or inaccessible field: " + updateField);
            }

            updatableField.get(updateField).accept(matchedBill, newValue);
            System.out.println("Update fields '" + updateField + "' with value: " + newValue);

        } catch (IllegalAccessException e) {
            System.err.println(e);
        } catch (NoSuchElementException e) {
            System.err.println(ExceptionConstants.ERR_LOG_BILL_NOT_FOUND_WITH_ID);
        } catch (Exception e) {
            System.err.println(ExceptionConstants.ERR_LOG_INVALID_ARGUMENT);
            System.err.println(e);
        }
    }

    public void deleteBill(String billId) {
        try {
            Bill removedBill = account.removeBillById(Long.parseLong(billId));
            if (removedBill == null) {
                throw new NoSuchElementException();
            }
            CommonUtils.printBillTable(List.of(removedBill));

        } catch (NoSuchElementException e) {
            System.err.println(ExceptionConstants.ERR_LOG_BILL_NOT_FOUND_WITH_ID);
        } catch (Exception e) {
            System.err.println(ExceptionConstants.ERR_LOG_INVALID_ARGUMENT);
            System.err.println(e);
        }
    }

    private boolean processIsPaid(String isPaidString) {
        if ("not_paid".equalsIgnoreCase(isPaidString)) {
            return false;
        } else if ("paid".equalsIgnoreCase(isPaidString)) {
            return true;
        } else {
            return Boolean.parseBoolean(isPaidString);
        }
    }
}
