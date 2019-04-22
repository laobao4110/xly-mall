package com.xly.mall.common.domain;

public class ApplicationConfig {

    public static String application;

    public static String authKey;

    public void setApplication(String application) {
        ApplicationConfig.application = application;
    }

    public void setAuthKey(String authKey) {
        ApplicationConfig.authKey = authKey;
    }

}
