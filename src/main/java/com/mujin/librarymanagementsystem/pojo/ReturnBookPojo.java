package com.mujin.librarymanagementsystem.pojo;

public class ReturnBookPojo {
    String title;
    String account;
    Integer time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "ReturnBookPojo{" +
                "title='" + title + '\'' +
                ", account='" + account + '\'' +
                ", time=" + time +
                '}';
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
