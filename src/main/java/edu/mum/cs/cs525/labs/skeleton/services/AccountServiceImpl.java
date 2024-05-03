package edu.mum.cs.cs525.labs.skeleton.services;

import edu.mum.cs.cs525.labs.skeleton.Account;
import edu.mum.cs.cs525.labs.skeleton.observer.*;
import edu.mum.cs.cs525.labs.skeleton.repo.*;
import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;
import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccountServiceImpl implements AccountService, Observable {
	private AccountDAO accountDAO;

	public AccountServiceImpl(AccountFactory accountFactory){
		accountDAO = accountFactory.getAccount();
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
		this.registerObserver(EmailObserver.getEmailObserver());
		this.registerObserver(SMSObserver.getSmsObserver());
		this.registerObserver(LogObserver.getLogObserver());
	}

	public void deposit(String accountNumber, double amount) {
		Account account = accountDAO.loadAccount(accountNumber);
		account.deposit(amount);
		accountDAO.updateAccount(account);
		notifyObservers(EnumNotifyType.DEPOSITED, "Deposited account: "+accountNumber);
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
		accountDAO.updateAccount(account);
		notifyObservers(EnumNotifyType.WITHDRAW, "Amount withdrawn from "+ accountNumber);
	}



	public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount, String description) {
		Account fromAccount = accountDAO.loadAccount(fromAccountNumber);
		Account toAccount = accountDAO.loadAccount(toAccountNumber);
		fromAccount.transferFunds(toAccount, amount, description);
		accountDAO.updateAccount(fromAccount);
		accountDAO.updateAccount(toAccount);
		notifyObservers(EnumNotifyType.TRANSFER_FUNDS, "Amount withdrawn from "+ fromAccountNumber);

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
