package com.leon.reading_counter.tables;

public class PasswordInfo {
    public String oldPassword;
    public String newPassword;
    public String confirmPassword;

    public PasswordInfo(String oldPassword, String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
}
