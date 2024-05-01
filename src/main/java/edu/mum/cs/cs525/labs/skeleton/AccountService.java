package edu.mum.cs.cs525.labs.skeleton;

import java.util.Collection;

public interface AccountService {
    Account createAccount(String accountNumber, String customerName, AccountType accountType);
    Account getAccount(String accountNumber);
    Collection<Account> getAllAccounts();

    void addInterest();

    void deposit (String accountNumber, double amount);
    void withdraw (String accountNumber, double amount);
    void transferFunds(String fromAccountNumber, String toAccountNumber, double amount, String description);
}
