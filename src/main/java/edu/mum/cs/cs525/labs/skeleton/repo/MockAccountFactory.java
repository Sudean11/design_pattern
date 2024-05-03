package edu.mum.cs.cs525.labs.skeleton.repo;

public class MockAccountFactory extends AccountFactory{
    @Override
    AccountDAO createAccount() {
        return new MockAccountDAOImpl();
    }
}
