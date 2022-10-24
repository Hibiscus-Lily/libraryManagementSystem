package com.mujin.librarymanagementsystem.pojo;

public class loginStatusInformation {
    private String account;
    private String status;

    @Override
    public String toString() {
        return "loginStatusInformation{" +
                "account='" + account + '\'' +
                ", status=" + status +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
