package edu.mum.cs.cs525.labs.skeleton.strategy;

public class SavingAccount  implements AccountType{

    @Override
    public double calcInterest(double balance) {
        if(balance< 1000){
            return balance*0.01;
        }

        if(balance > 1000 && balance < 5000){
            return balance*0.02;
        }

        if(balance > 5000 ){
            return balance*0.04;
        }
        return 0;
    }
}
