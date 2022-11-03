package com.mujin.librarymanagementsystem.controller;

import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BookPojo;
import com.mujin.librarymanagementsystem.pojo.BorrowPojo;
import com.mujin.librarymanagementsystem.service.BookService;
import com.mujin.librarymanagementsystem.service.BorrowService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commonuser/borrow")
public class OrdinaryUser_BorrowController {
    private BorrowService borrowService;
    private BookService bookService;

    @Autowired
    public void setBorrowInformationService(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @Autowired
    public void setBookInformationService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 普通用户借阅书籍
     */
    @PostMapping
    public Result borrowBooks(@RequestBody BorrowPojo borrowPojo) {
        BookPojo bookPojo = bookService.findStudentsByTitle(borrowPojo.getTitle());  //根据借阅的书名检查是否存在对应图书
        if (bookPojo != null) {
            String account = (String) JwtUtils.validateJWT(borrowPojo.getAccount()).getClaims().get("account");
            Boolean result = bookService.updateBookStatus(borrowPojo.getTitle(), 1);
            if (result) {
                Boolean result2 = borrowService.addBorrowingRecords(borrowPojo.getTitle(), account, borrowPojo.getBorrowingTime(), 0, borrowPojo.getBorrowingTime() + 5184000); //添加借阅记录
                if (result2) {
                    return new Result(Code.OK, true, "借阅成功");
                } else {
                    return new Result(Code.OK, false, "借阅失败");
                }
            } else {
                return new Result(Code.OK, false, "借阅失败,请联系管理员");
            }

        } else {
            return new Result(Code.OK, false, "借阅失败,请联系管理员");
        }
    }

    /**
     * 归还书籍
     */
    @DeleteMapping
    public Result returnBooks(@RequestBody BorrowPojo borrowPojo) {
        BookPojo bookPojo = bookService.findStudentsByTitle(borrowPojo.getTitle());  //根据借阅的书名检查是否存在对应图书
        if (bookPojo != null) {
            Boolean result = bookService.updateBookStatus(borrowPojo.getTitle(), 0);
            if (result) {
                Boolean result2 = borrowService.updateBookReturnTime(borrowPojo.getTitle(), borrowPojo.getBookReturnTime(), 0); //添加借阅记录
                if (result2) {
                    return new Result(Code.OK, true, "还书成功");
                } else {
                    return new Result(Code.OK, false, "还书失败");
                }
            } else {
                return new Result(Code.OK, false, "还书失败,请联系管理员");
            }
        } else {
            return new Result(Code.OK, false, "还书失败,请联系管理员");
        }
    }

}
