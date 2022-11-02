package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.service.BorrowInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <h1>管理员管理借阅</h1>
 * <h3>是否鉴权  true</h3>
 * <h3>是否权限鉴权 true </h3>
 * <p>鉴权文件 {@code Admin_BorrowInterceptor.java }</p>
 */
@RestController
@RequestMapping(value = "/admin/borrow")
public class Admin_BorrowController {
    private BorrowInformationService borrowInformationService;

    @Autowired
    public void setBorrowInformationService(BorrowInformationService borrowInformationService) {
        this.borrowInformationService = borrowInformationService;
    }


}
