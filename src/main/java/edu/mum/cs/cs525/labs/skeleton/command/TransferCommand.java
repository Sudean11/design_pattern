package edu.mum.cs.cs525.labs.skeleton.command;

import edu.mum.cs.cs525.labs.skeleton.services.AccountService;

public class TransferCommand implements Command{
    AccountService accountService;
    String fromAccountNumber;
    String toAccountNumber;
    double amount;
    String description;

    public TransferCommand(AccountService accountService, String fromAccountNumber, String toAccountNumber, double amount, String description){
        this.accountService = accountService;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.amount = amount;
        this.description = description;
    }
    @Override
    public void execute() {
        accountService.transferFunds(fromAccountNumber, toAccountNumber, amount, description);
    }

    @Override
    public void undo() {
        accountService.transferFunds(toAccountNumber, fromAccountNumber, amount, description+" REVERTED");
    }
}
