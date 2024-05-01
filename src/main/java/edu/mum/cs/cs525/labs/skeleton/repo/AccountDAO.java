package edu.mum.cs.cs525.labs.skeleton.repo;

import edu.mum.cs.cs525.labs.skeleton.Account;

import java.util.Collection;

public interface AccountDAO {
	void saveAccount(Account account);
	void updateAccount(Account account);
	Account loadAccount(String accountnumber);
	Collection<Account> getAccounts();
}
