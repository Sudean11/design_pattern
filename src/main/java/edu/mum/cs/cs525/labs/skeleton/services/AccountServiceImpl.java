package edu.mum.cs.cs525.labs.skeleton.services;

import edu.mum.cs.cs525.labs.skeleton.Account;
import edu.mum.cs.cs525.labs.skeleton.observer.*;
import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;
import edu.mum.cs.cs525.labs.skeleton.repo.Customer;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountDAO;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountDAOImpl;
import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountServiceImpl implements AccountService, Observable {
	private AccountDAO accountDAO;
	
	public AccountServiceImpl(){
		accountDAO = new AccountDAOImpl();
		setupAndRegisterObservers();
	}

	public Account createAccount(String accountNumber, String customerName, AccountInterestStrategy accountType) {
		Account account = new Account(accountNumber);
		Customer customer = new Customer(customerName);
		account.setCustomer(customer);
		account.setAccountType(accountType);
		accountDAO.saveAccount(account);
		notifyObservers(EnumNotifyType.CREATED, "Account with "+ accountNumber+ " created");
		return account;
	}

	private void setupAndRegisterObservers() {
		Observer emailObserver = EmailObserver.getEmailObserver();
		Observer smsObserver = SMSObserver.getSmsObserver();
		Observer logObserver = LogObserver.getLogObserver();
		this.registerObserver(emailObserver);
		this.registerObserver(smsObserver);
		this.registerObserver(logObserver);
	}

	public void deposit(String accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		account.deposit(amount);
		notifyObservers(EnumNotifyType.DEPOSITED, "Deposited account: "+accountNumber);

		accountDAO.updateAccount(account);
	}

	public Account getAccount(String accountNumber) {
		Account account = accountDAO.loadAccount(accountNumber);
		notifyObservers(EnumNotifyType.FETCHED, "Fetched account: "+accountNumber);

		return account;
	}

	public Collection<Account> getAllAccounts() {
		notifyObservers(EnumNotifyType.FETCHED, "Fetched ALL accounts");
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
		notifyObservers(EnumNotifyType.WITHDRAW, "Amount withdrawn from "+ accountNumber);
		accountDAO.updateAccount(account);
	}



	public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount, String description) {
		Account fromAccount = accountDAO.loadAccount(fromAccountNumber);
		Account toAccount = accountDAO.loadAccount(toAccountNumber);
		fromAccount.transferFunds(toAccount, amount, description);
		notifyObservers(EnumNotifyType.TRANSFER_FUNDS, "Amount withdrawn from "+ fromAccountNumber);

		accountDAO.updateAccount(fromAccount);
		accountDAO.updateAccount(toAccount);
	}
	private List<Observer> observers = new ArrayList<>();


	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(EnumNotifyType enumNotifyType, String message) {
		observers.forEach(observer -> observer.update(enumNotifyType, message));
	}

}
