package edu.mum.cs.cs525.labs.skeleton.repo.mock;

import edu.mum.cs.cs525.labs.skeleton.repo.AccountDAO;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountFactory;

public class MockAccountFactory extends AccountFactory {
    @Override
    public AccountDAO createAccount() {
        return new MockAccountDAOImpl();
    }
}
