package edu.mum.cs.cs525.labs.skeleton;

import edu.mum.cs.cs525.labs.skeleton.Decorator.P1;
import edu.mum.cs.cs525.labs.skeleton.Decorator.P2;
import edu.mum.cs.cs525.labs.skeleton.Decorator.P3;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountEntry;
import edu.mum.cs.cs525.labs.skeleton.repo.Customer;
import edu.mum.cs.cs525.labs.skeleton.repo.mock.MockAccountFactory;
import edu.mum.cs.cs525.labs.skeleton.services.AccountService;
import edu.mum.cs.cs525.labs.skeleton.services.AccountServiceImpl;
import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;
import edu.mum.cs.cs525.labs.skeleton.strategy.CheckingAccountInterestStrategy;
import edu.mum.cs.cs525.labs.skeleton.strategy.SavingAccountInterestStrategy;

public class Application {

	public static void main(String[] args) {

		AccountService accountService = new AccountServiceImpl(new MockAccountFactory());

		AccountInterestStrategy strategy = new SavingAccountInterestStrategy();
		strategy = new P1(strategy);
		strategy = new P2(strategy);
		strategy = new P3(strategy);
		// create 2 accounts;
		accountService.createAccount("1263862", "Frank Brown",strategy);
		accountService.createAccount("4253892", "John Doe", new CheckingAccountInterestStrategy());
		// use account 1;
		accountService.deposit("1263862", 240);
		accountService.deposit("1263862", 529);
		accountService.withdraw("1263862", 230);
		// use account 2;
		accountService.deposit("4253892", 12450);
		accountService.transferFunds("4253892", "1263862", 100, "payment of invoice 10232");

		accountService.addInterest();

		// show balances

		for (Account account : accountService.getAllAccounts()) {
			Customer customer = account.getCustomer();
			System.out.println("Statement for Account: " + account.getAccountNumber());
			System.out.println("Account Holder: " + customer.getName());
			
			System.out.println("-Date-------------------------" 
					+ "-Description------------------" 
					+ "-Amount-------------");
			
			for (AccountEntry entry : account.getEntryList()) {
				System.out.printf("%30s%30s%20.2f\n", 
						entry.getDate().toString(), 
						entry.getDescription(),
						entry.getAmount());
			}
			
			System.out.println("----------------------------------------" + "----------------------------------------");
			System.out.printf("%30s%30s%20.2f\n\n", "", "Current Balance:", account.getBalance());
		}
	}

}
