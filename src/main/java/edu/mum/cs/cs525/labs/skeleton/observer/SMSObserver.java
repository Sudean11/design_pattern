package edu.mum.cs.cs525.labs.skeleton.observer;

import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

public class SMSObserver implements  Observer{
    private SMSObserver(){}

    public static SMSObserver instance;

    public static SMSObserver getSmsObserver(){
        if(instance == null){
            instance = new SMSObserver();
        }
        return instance;
    }

    @Override
    public void update(EnumNotifyType enumNotifyType, String message) {
        System.out.println("SMS:" +message);
    }
}
