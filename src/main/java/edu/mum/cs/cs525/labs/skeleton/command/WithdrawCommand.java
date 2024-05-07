package edu.mum.cs.cs525.labs.skeleton.command;

import edu.mum.cs.cs525.labs.skeleton.services.AccountService;

public class WithdrawCommand implements Command{
    AccountService accountService;
    String accountNumber;
    double amount;

    WithdrawCommand(AccountService accountService, String accountNumber, double amount){
        this.accountService = accountService;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    @Override
    public void execute() {
        accountService.withdraw(accountNumber, amount);
    }

    @Override
    public void undo() {
        accountService.deposit(accountNumber, amount);
    }
}
