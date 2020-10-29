package com.leon.reading_counter.tables;

public class LoginFeedBack {
    public String access_token;
    public String refresh_token;

    public LoginFeedBack(String access_token, String refresh_token) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }
}
