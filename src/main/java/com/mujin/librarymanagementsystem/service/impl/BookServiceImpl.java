package com.mujin.librarymanagementsystem.service.impl;


import com.mujin.librarymanagementsystem.mapper.BookMapper;
import com.mujin.librarymanagementsystem.pojo.BookPojo;
import com.mujin.librarymanagementsystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    public BookMapper bookMapper;

    @Autowired
    public void setBookInformationDao(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public BookPojo findStudentsByTitle(String title) {
        return bookMapper.findBooksByTitle(title);
    }

    public List<BookPojo> allBooks() {
        return bookMapper.findBooks();
    }

    @Override
    public Boolean deleteBooks(String title) {
        if (title != null) {
            bookMapper.deleteBooks(title);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateBooks(String title, String author, String press, String year, String isbn, Integer state) {
        if (title != null && author != null && press != null && year != null && isbn != null && state != null) {
            bookMapper.updateBooks(title, author, press, year, isbn, state);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean addBooks(String title, String author, String press, String year, String isbn, Integer state) {
        if (title != null && author != null && press != null && year != null && isbn != null && state != null) {
            bookMapper.addBooks(title, author, press, year, isbn, state);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateBookStatus(String title, Integer state) {
        if (title != null && state != null) {
            bookMapper.updateBookStatus(title, state);
            return true;
        } else {
            return false;
        }
    }
}
