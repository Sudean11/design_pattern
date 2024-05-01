package edu.mum.cs.cs525.labs.skeleton.services;

import edu.mum.cs.cs525.labs.skeleton.Account;
import edu.mum.cs.cs525.labs.skeleton.strategy.AccountType;
import edu.mum.cs.cs525.labs.skeleton.repo.Customer;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountDAO;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountDAOImpl;

import java.util.Collection;

public class AccountServiceImpl implements AccountService {
	private AccountDAO accountDAO;
	
	public AccountServiceImpl(){
		accountDAO = new AccountDAOImpl();
	}

	public Account createAccount(String accountNumber, String customerName, AccountType accountType) {
		Account account = new Account(accountNumber);
		Customer customer = new Customer(customerName);
		account.setCustomer(customer);
		account.setAccountType(accountType);
		accountDAO.saveAccount(account);
		return account;
	}

	public void deposit(String accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		account.deposit(amount);
		
		accountDAO.updateAccount(account);
	}

	public Account getAccount(String accountNumber) {
		Account account = accountDAO.loadAccount(accountNumber);
		return account;
	}

	public Collection<Account> getAllAccounts() {
		return accountDAO.getAccounts();
	}

	@Override
	public void addInterest() {
		for (Account account : getAllAccounts()) {
			account.deposit(account.calcInterest(account.getBalance()));
		}
	}

	public void withdraw(String accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		account.withdraw(amount);
		accountDAO.updateAccount(account);
	}



	public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount, String description) {
		Account fromAccount = accountDAO.loadAccount(fromAccountNumber);
		Account toAccount = accountDAO.loadAccount(toAccountNumber);
		fromAccount.transferFunds(toAccount, amount, description);
		accountDAO.updateAccount(fromAccount);
		accountDAO.updateAccount(toAccount);
	}
}
