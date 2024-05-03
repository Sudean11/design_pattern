package edu.mum.cs.cs525.labs.skeleton.Decorator;

import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;

public class P3 extends InterestPromotionDecorator{
    public P3(AccountInterestStrategy accountInterestStrategy){
        this.interestStrategy = accountInterestStrategy;

    }
    @Override
    public double calcInterest(double balance) {
        return interestStrategy.calcInterest(balance)+balance*0.03;
    }
}
