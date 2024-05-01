package edu.mum.cs.cs525.labs.skeleton.observer;

import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

public class EmailObserver implements  Observer{

    private EmailObserver(){}

    public static EmailObserver instance;

    public static EmailObserver getEmailObserver(){
        if(instance == null){
            instance = new EmailObserver();
        }
        return instance;
    }

    @Override
    public void update(EnumNotifyType enumNotifyType, String message) {
        if(enumNotifyType == EnumNotifyType.CREATED){
            System.out.println("EMAIL:" +message);
        }
    }
}
