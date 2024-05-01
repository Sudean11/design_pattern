package edu.mum.cs.cs525.labs.skeleton.strategy;

public class CheckingAccount  implements  AccountType{
    @Override
    public double calcInterest(double balance) {
        if(balance< 1000){
            return balance*0.015;
        }
        if(balance > 1000){
            return  balance*0.025;
        }
        return 0;
    }
}
