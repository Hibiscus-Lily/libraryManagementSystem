package com.mujin.librarymanagementsystem.pojo;

public class BorrowInformationPojo {
    Integer id;
    String title;
    String account;
    Integer borrowingTime;
    Integer bookReturnTime;
    Integer estimatedReturnTime;

    @Override
    public String toString() {
        return "BorrowInformationPojo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", account='" + account + '\'' +
                ", borrowingTime=" + borrowingTime +
                ", bookReturnTime=" + bookReturnTime +
                ", estimatedReturnTime=" + estimatedReturnTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBorrowingTime() {
        return borrowingTime;
    }

    public void setBorrowingTime(Integer borrowingTime) {
        this.borrowingTime = borrowingTime;
    }

    public Integer getBookReturnTime() {
        return bookReturnTime;
    }

    public void setBookReturnTime(Integer bookReturnTime) {
        this.bookReturnTime = bookReturnTime;
    }

    public Integer getEstimatedReturnTime() {
        return estimatedReturnTime;
    }

    public void setEstimatedReturnTime(Integer estimatedReturnTime) {
        this.estimatedReturnTime = estimatedReturnTime;
    }
}
