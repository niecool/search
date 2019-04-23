package com.nie.monitor.impl;

import com.nie.model.DataChangeMessage;
import com.nie.model.NieMessage;
import com.nie.monitor.Observer;
import com.nie.monitor.ObserverServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaochengye
 * @date 2019-04-23 19:51
 */
public class RealtimeObserverServer implements ObserverServer {

    private NieMessage nieMessage;

    private List<Observer> observers;

    public RealtimeObserverServer(List<Observer> observers){
        this.observers = observers;
    }

    @Override
    public void addObserver(Observer observer) {
        if(this.observers == null){
            observers = new ArrayList<Observer>();
        }
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if(!observers.isEmpty()){
            observers.remove(observer);
        }
    }

    @Override
    public void notifyAllObservers() {
        if(!observers.isEmpty()){
            for (Observer o : observers){
                o.update(nieMessage);
            }
        }
    }

    @Override
    public void sendMessage(NieMessage message) {
        this.nieMessage = message;
        notifyAllObservers();
    }
}
