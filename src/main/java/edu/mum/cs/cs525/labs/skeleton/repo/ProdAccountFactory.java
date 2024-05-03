package edu.mum.cs.cs525.labs.skeleton.repo;

public class ProdAccountFactory extends AccountFactory{
    @Override
    AccountDAO createAccount() {
        return new ProdAccountDAOImpl();
    }
}
