package com.mujin.librarymanagementsystem.service;


import com.mujin.librarymanagementsystem.pojo.BookPojo;

import java.util.List;

public interface BookService {
    BookPojo findStudentsByTitle(String title);

    List<BookPojo> allBooks();

    Boolean deleteBooks(String title);

    Boolean updateBooks(String title, String author, String press, String year, String isbn, Integer state);

    Boolean addBooks(String title, String author, String press, String year, String isbn, Integer state);

    Boolean updateBookStatus(String title, Integer state);

}
