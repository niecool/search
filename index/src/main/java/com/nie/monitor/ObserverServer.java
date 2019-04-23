package com.nie.monitor;

import com.nie.model.DataChangeMessage;
import com.nie.model.NieMessage;

import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 19:56
 */
public interface ObserverServer {

    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyAllObservers();

    public void sendMessage(NieMessage message);

}
