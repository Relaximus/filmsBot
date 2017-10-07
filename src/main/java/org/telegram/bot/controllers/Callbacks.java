package org.telegram.bot.controllers;

public enum Callbacks {
    FIND,ADD,WATCH,REMOVE;

    private Callbacks(){}

    public String forChat(){
        return name().toLowerCase();
    }
}
