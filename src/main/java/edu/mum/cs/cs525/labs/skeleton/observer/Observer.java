package edu.mum.cs.cs525.labs.skeleton.observer;

import edu.mum.cs.cs525.labs.skeleton.utils.EnumNotifyType;

public interface Observer {
    void update(EnumNotifyType enumNotifyType, String message);
}
