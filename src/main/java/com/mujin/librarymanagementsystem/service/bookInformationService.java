package com.mujin.librarymanagementsystem.service;


import com.mujin.librarymanagementsystem.pojo.bookInformation;

import java.util.List;

public interface bookInformationService {
    bookInformation findStudentsByTitle(String title);

    List<bookInformation> allBooks();

    void deleteBooks(String title);

    void updateBooks(String title, String author, String press, String year, String pages, String pricing, String isbn);

    void   addBooks(String title, String author, String press, String year, String pages, String pricing, String isbn);
}
