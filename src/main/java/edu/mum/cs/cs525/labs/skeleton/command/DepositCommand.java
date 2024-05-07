package edu.mum.cs.cs525.labs.skeleton.command;

import edu.mum.cs.cs525.labs.skeleton.services.AccountService;

public class DepositCommand implements Command{

    AccountService accountService;
    String accountNumber;
    double amount;

    public DepositCommand(AccountService accountService, String accountNumber, double amount){
        this.accountService = accountService;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public void execute() {
        accountService.deposit(accountNumber, amount);
    }

    @Override
    public void undo() {
        accountService.withdraw(accountNumber, amount);
    }
}
