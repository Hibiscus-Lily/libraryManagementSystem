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
    public void deleteBooks(String title) {
        bookInformationMapper.deleteBooks(title);
    }

    @Override
    public void updateBooks(String title, String author, String press, String year, String pages, String pricing, String isbn) {
        bookInformationMapper.updateBooks(title, author, press, year, isbn);
    }

    @Override
    public void addBooks(String title, String author, String press, String year, String pages, String pricing, String isbn) {
        bookInformationMapper.addBooks(title, author, press, year, isbn);
    }
}
