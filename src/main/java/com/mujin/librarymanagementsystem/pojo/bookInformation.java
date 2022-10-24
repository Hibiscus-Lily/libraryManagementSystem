package com.mujin.librarymanagementsystem.pojo;

public class bookInformation {

    private String title;
    private String author;
    private String press;
    private String year;
    private String pages;
    private String pricing;
    private String isbn;

    @Override
    public String toString() {
        return "allBookInformation{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", press='" + press + '\'' +
                ", year='" + year + '\'' +
                ", pages='" + pages + '\'' +
                ", pricing='" + pricing + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
