package com.mujin.librarymanagementsystem.service.impl;


import com.mujin.librarymanagementsystem.mapper.BookInformationMapper;
import com.mujin.librarymanagementsystem.pojo.BookInformationPojo;
import com.mujin.librarymanagementsystem.service.BookInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookInformationServiceImpl implements BookInformationService {

    public BookInformationMapper bookInformationMapper;

    @Autowired
    public void setBookInformationDao(BookInformationMapper bookInformationMapper) {
        this.bookInformationMapper = bookInformationMapper;
    }

    @Override
    public BookInformationPojo findStudentsByTitle(String title) {
        return bookInformationMapper.findBooksByTitle(title);
    }

    public List<BookInformationPojo> allBooks() {
        return bookInformationMapper.findBooks();
    }

    @Override
    public Boolean deleteBooks(String title) {
        if (title != null) {
            bookInformationMapper.deleteBooks(title);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean updateBooks(String title, String author, String press, String year, String isbn, Integer state) {
        if (title != null && author != null && press != null && year != null && isbn != null && state != null) {
            bookInformationMapper.updateBooks(title, author, press, year, isbn, state);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean addBooks(String title, String author, String press, String year, String isbn, Integer state) {
        if (title != null && author != null && press != null && year != null && isbn != null && state != null) {
            bookInformationMapper.addBooks(title, author, press, year, isbn, state);
            return true;
        } else {
            return false;
        }
    }
}
