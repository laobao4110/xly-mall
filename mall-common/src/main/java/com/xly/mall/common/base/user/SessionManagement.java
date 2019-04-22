package com.xly.mall.common.base.user;

import java.util.HashSet;

public class SessionManagement {

    private HashSet<String> sessionIdSet;

    private SessionManagement(){sessionIdSet = new HashSet<>();}

    public static SessionManagement getInstance(){return EnumSingleton.Singleton.getInstance();}

    public boolean hasSession(String id){return sessionIdSet.contains(id);}

    public void addSessionId(String id){
        if(id != null && id.length() > 0){
            sessionIdSet.add(id);
        }
    }

    public void removeSession(String id){
        if(id != null && id.length() >0){
            sessionIdSet.remove(id);
        }
    }


    private enum EnumSingleton{
        Singleton;

        private SessionManagement sessionManagement;

        EnumSingleton(){sessionManagement = new SessionManagement();}

        public SessionManagement getInstance(){return sessionManagement;}

    }

}
