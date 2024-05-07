package edu.mum.cs.cs525.labs.skeleton;

import edu.mum.cs.cs525.labs.skeleton.Decorator.P1;
import edu.mum.cs.cs525.labs.skeleton.Decorator.P2;
import edu.mum.cs.cs525.labs.skeleton.Decorator.P3;
import edu.mum.cs.cs525.labs.skeleton.command.Command;
import edu.mum.cs.cs525.labs.skeleton.command.DepositCommand;
import edu.mum.cs.cs525.labs.skeleton.command.TransferCommand;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountEntry;
import edu.mum.cs.cs525.labs.skeleton.repo.Customer;
import edu.mum.cs.cs525.labs.skeleton.repo.mock.MockAccountFactory;
import edu.mum.cs.cs525.labs.skeleton.services.AccountService;
import edu.mum.cs.cs525.labs.skeleton.services.AccountServiceImpl;
import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;
import edu.mum.cs.cs525.labs.skeleton.strategy.CheckingAccountInterestStrategy;
import edu.mum.cs.cs525.labs.skeleton.strategy.SavingAccountInterestStrategy;

import java.util.Stack;

public class Application {

	public static void main(String[] args) {

		Stack<Command> commandStack = new Stack<>();

		AccountService accountService = new AccountServiceImpl(new MockAccountFactory());

		AccountInterestStrategy strategy = new SavingAccountInterestStrategy();
		strategy = new P1(strategy);
		strategy = new P2(strategy);
		strategy = new P3(strategy);
		// create 2 accounts;
		accountService.createAccount("1263862", "Frank Brown",strategy);
		accountService.createAccount("4253892", "John Doe", new CheckingAccountInterestStrategy());
		// use account 1;

		Command deposit = new DepositCommand(accountService,"1263862", 240);
		deposit.execute();
		commandStack.push(deposit);
		deposit = new DepositCommand(accountService,"1263862", 529);
		deposit.execute();
		commandStack.push(deposit);
		deposit = new DepositCommand(accountService,"1263862", 230);
		deposit.execute();
		commandStack.push(deposit);

		// use account 2;
		accountService.deposit("4253892", 12450);
		deposit = new DepositCommand(accountService,"4253892", 12450);
		deposit.execute();
		commandStack.push(deposit);

		Command transferFund = new TransferCommand(accountService, "4253892", "1263862", 100, "payment of invoice 10232");
		transferFund.execute();
		commandStack.push(transferFund);
		commandStack.pop().undo();

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
