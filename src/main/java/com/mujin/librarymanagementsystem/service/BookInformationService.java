package com.mujin.librarymanagementsystem.service;


import com.mujin.librarymanagementsystem.pojo.BookInformationPojo;

import java.util.List;

public interface BookInformationService {
    BookInformationPojo findStudentsByTitle(String title);

    List<BookInformationPojo> allBooks();

    void deleteBooks(String title);

    void updateBooks(String title, String author, String press, String year, String pages, String pricing, String isbn);

    void   addBooks(String title, String author, String press, String year, String pages, String pricing, String isbn);
}
