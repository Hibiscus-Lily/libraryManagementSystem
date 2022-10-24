package com.mujin.librarymanagementsystem.service.impl;


import com.mujin.librarymanagementsystem.dao.bookInformationDao;
import com.mujin.librarymanagementsystem.pojo.bookInformation;
import com.mujin.librarymanagementsystem.service.bookInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class bookInformationServiceImpl implements bookInformationService {

    public bookInformationDao bookInformationDao;

    @Autowired
    public void setBookInformationDao(bookInformationDao bookInformationDao) {
        this.bookInformationDao = bookInformationDao;
    }

    @Override
    public bookInformation findStudentsByTitle(String title) {
        return bookInformationDao.findBooksByTitle(title);
    }

    public List<bookInformation> allBooks() {
        return bookInformationDao.findBooks();
    }

    @Override
    public void deleteBooks(String title) {
        bookInformationDao.deleteBooks(title);
    }

    @Override
    public void updateBooks(String title, String author, String press, String year, String pages, String pricing, String isbn) {
        bookInformationDao.updateBooks(title, author, press, year, isbn);
    }

    @Override
    public void addBooks(String title, String author, String press, String year, String pages, String pricing, String isbn) {
        bookInformationDao.addBooks(title, author, press, year, isbn);
    }
}
