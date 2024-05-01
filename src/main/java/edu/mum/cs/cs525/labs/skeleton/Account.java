package edu.mum.cs.cs525.labs.skeleton;

import edu.mum.cs.cs525.labs.skeleton.observer.*;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountEntry;
import edu.mum.cs.cs525.labs.skeleton.repo.Customer;
import edu.mum.cs.cs525.labs.skeleton.strategy.AccountType;
import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Account implements Observable {
	private Customer customer;

	private AccountType accountType;

	private String accountNumber;

	private  List<Observer> observers = new ArrayList<>();

	public AccountType getAccountType() {
		return accountType;
	}

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

	public double calcInterest(double balance){
		return accountType.calcInterest(balance);
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	private List<AccountEntry> entryList = new ArrayList<AccountEntry>();

	public Account(String accountNumber) {
		//setup Observers and initialize observers
		setupAndRegisterObservers();

		this.accountNumber = accountNumber;
		notifyObservers(EnumNotifyType.CREATED, "Account with "+this.accountNumber+ " created");

	}

	private void setupAndRegisterObservers() {
		Observer emailObserver = EmailObserver.getEmailObserver();
		Observer smsObserver = SMSObserver.getSmsObserver();
		Observer logObserver = LogObserver.getLogObserver();
		this.registerObserver(emailObserver);
		this.registerObserver(smsObserver);
		this.registerObserver(logObserver);
	}

	public String getAccountNumber() {
		notifyObservers(EnumNotifyType.FETCHED, "Fetched account: "+this.accountNumber);
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
		notifyObservers(EnumNotifyType.UPDATED, "Updated account: "+this.accountNumber);

	}

	public double getBalance() {
		double balance = 0;
		for (AccountEntry entry : entryList) {
			balance += entry.getAmount();
		}
		notifyObservers(EnumNotifyType.FETCHED, "Fetched Balance, Account: "+this.accountNumber);
		return balance;
	}

	public void deposit(double amount) {
		AccountEntry entry = new AccountEntry(amount, "deposit", "", "");
		entryList.add(entry);
		notifyObservers(EnumNotifyType.DEPOSITED, "Amount Deposited, Account: "+this.accountNumber);


	}

	public void withdraw(double amount) {
		AccountEntry entry = new AccountEntry(-amount, "withdraw", "", "");
		entryList.add(entry);
		notifyObservers(EnumNotifyType.WITHDRAW, "Withdraw Balance, Account: "+this.accountNumber);

	}

	private void addEntry(AccountEntry entry) {
		notifyObservers(EnumNotifyType.NEW_ENTRY, "New Transaction, Account: "+this.accountNumber);
		entryList.add(entry);
	}

	public void transferFunds(Account toAccount, double amount, String description) {
		AccountEntry fromEntry = new AccountEntry(-amount, description, toAccount.getAccountNumber(),
				toAccount.getCustomer().getName());
		AccountEntry toEntry = new AccountEntry(amount, description, toAccount.getAccountNumber(),
				toAccount.getCustomer().getName());
		
		entryList.add(fromEntry);
		notifyObservers(EnumNotifyType.TRANSFER_FUNDS, "Fund Transfer, Account: "+this.accountNumber);

		toAccount.addEntry(toEntry);
	}

	public Customer getCustomer() {
		notifyObservers(EnumNotifyType.FETCH_CUSTOMER, "Customer Details fetched,  Account: "+this.accountNumber);
		return customer;
	}

	public void setCustomer(Customer customer) {
		notifyObservers(EnumNotifyType.UPDATED, "Customer Updated,  Account: "+this.accountNumber);
		this.customer = customer;
	}

	public Collection<AccountEntry> getEntryList() {
		notifyObservers(EnumNotifyType.FETCHED, "FETCHED Txn,  Account: "+this.accountNumber);
		return entryList;
	}
}
