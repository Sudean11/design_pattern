package edu.mum.cs.cs525.labs.skeleton.Decorator;

import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;

public class P1 extends  InterestPromotionDecorator{
    public P1(AccountInterestStrategy accountInterestStrategy){
        this.interestStrategy = accountInterestStrategy;
    }
    @Override
    public double calcInterest(double balance) {
        System.out.println("P1: "+interestStrategy.calcInterest(balance)+" "+balance);
        return interestStrategy.calcInterest(balance)+balance*0.01;
    }
}
