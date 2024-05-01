package edu.mum.cs.cs525.labs.skeleton.observer;


import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

public class LogObserver implements Observer {

    private LogObserver(){}
    public static LogObserver instance;

    public static LogObserver getLogObserver(){
        if(instance == null){
            instance = new LogObserver();
        }
        return instance;
    }
    @Override
    public void update(EnumNotifyType notifyType, String message) {
        System.out.println("LOG: "+ message);
    }
}
