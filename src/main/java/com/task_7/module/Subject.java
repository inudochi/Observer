package com.task_7.module;

public interface Subject {
    void notifyAllObservers();
    void attach(Observer obs);
    void detach(Observer obs);

    int getState();
}