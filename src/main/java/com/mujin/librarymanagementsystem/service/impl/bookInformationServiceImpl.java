package com.mujin.librarymanagementsystem.service.impl;


import com.mujin.librarymanagementsystem.mapper.bookInformationMapper;
import com.mujin.librarymanagementsystem.pojo.bookInformation;
import com.mujin.librarymanagementsystem.service.bookInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class bookInformationServiceImpl implements bookInformationService {

    public bookInformationMapper bookInformationMapper;

    @Autowired
    public void setBookInformationDao(bookInformationMapper bookInformationMapper) {
        this.bookInformationMapper = bookInformationMapper;
    }

    @Override
    public bookInformation findStudentsByTitle(String title) {
        return bookInformationMapper.findBooksByTitle(title);
    }

    public List<bookInformation> allBooks() {
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
