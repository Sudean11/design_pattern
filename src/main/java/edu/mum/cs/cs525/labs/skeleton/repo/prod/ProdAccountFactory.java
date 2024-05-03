package edu.mum.cs.cs525.labs.skeleton.repo.prod;

import edu.mum.cs.cs525.labs.skeleton.repo.AccountDAO;
import edu.mum.cs.cs525.labs.skeleton.repo.AccountFactory;

public class ProdAccountFactory extends AccountFactory {
    @Override
    public AccountDAO createAccount() {
        return new ProdAccountDAOImpl();
    }
}
