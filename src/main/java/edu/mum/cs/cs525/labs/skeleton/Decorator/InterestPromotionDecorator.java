package edu.mum.cs.cs525.labs.skeleton.Decorator;

import edu.mum.cs.cs525.labs.skeleton.strategy.AccountInterestStrategy;

abstract class InterestPromotionDecorator implements AccountInterestStrategy {
    AccountInterestStrategy interestStrategy;
    public abstract double calcInterest(double balance);

}
