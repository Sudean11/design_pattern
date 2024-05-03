package edu.mum.cs.cs525.labs.skeleton.repo;

public abstract class AccountFactory {

//    abstract AccountFactory
    public AccountDAO getAccount(){
        return createAccount();
    }
    abstract  AccountDAO createAccount();
}
