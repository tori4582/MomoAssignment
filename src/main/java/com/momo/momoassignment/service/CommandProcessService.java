package com.momo.momoassignment.service;

import com.momo.momoassignment.common.ExceptionConstants;

public class CommandProcessService {

    public void processCommand(String[] args) {
        AccountService accountService = new AccountService();
        BillService billService = new BillService();
        PaymentService paymentService = new PaymentService();

        try {
            final var command = args[0].toLowerCase();
            switch (command) {
                case "cash_in" -> {
                    final var cashInAmount = args[1];
                    long newAmount = accountService.cashIn(cashInAmount);

                    if (newAmount != -1) {
                        System.out.println("Your available balance: " + newAmount);
                    }
                }
                case "list_bill" -> billService.getAllBill();
                case "create_bill" -> billService.createBillProcedure(
                            args[1],
                            args[2],
                            args[3],
                            args[4],
                            args[5],
                            args[6]
                );
                case "delete_bill" -> billService.deleteBill(args[1]);
                case "update_bill" -> billService.updateBillDetails(args[1], args[2], args[3]);
                case "view_bill" -> billService.viewBillDetails(args[1]);
                case "pay" -> {
                    for (int i = 1; i < args.length; i++) {
                        paymentService.pay(args[i]);
                    }
                }
                case "due_date" -> billService.getNotPaidBill();
                case "schedule" -> throw new UnsupportedOperationException();
                case "list_payment" -> paymentService.listPayments();
                case "search_bill_by_provider" -> billService.getBillsByProviders(args[1]);
                case "exit" -> {
                    System.out.println("Good bye!");
                    System.exit(0);
                }
                default -> System.out.println("Unrecognised command: " + command);
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println(ExceptionConstants.ERR_LOG_MISSING_ARGUMENT);
            System.err.println(e);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
