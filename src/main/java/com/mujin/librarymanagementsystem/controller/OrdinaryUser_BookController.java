package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BookInformationPojo;
import com.mujin.librarymanagementsystem.pojo.ReturnBookPojo;
import com.mujin.librarymanagementsystem.service.BookInformationService;
import com.mujin.librarymanagementsystem.service.BorrowInformationService;
import com.mujin.librarymanagementsystem.service.ReturnBookService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * <h1>普通用户获取图书信息</h1>
 * <h3>是否鉴权  true</h3>
 * <h3>是否权限鉴权 false </h3>
 * <p>鉴权文件 {@code OrdinaryUser_BookInterceptor.java }</p>
 */
@RestController
@RequestMapping("/commonuser/book")
public class OrdinaryUser_BookController {
    public ReturnBookService returnBookService;
    private BookInformationService bookInformationService;
    private BorrowInformationService borrowInformationService;

    @Autowired
    public void setReturnBookService(ReturnBookService returnBookService) {
        this.returnBookService = returnBookService;
    }

    @Autowired
    public void setBookInformationService(BookInformationService bookInformationService) {
        this.bookInformationService = bookInformationService;
    }

    @Autowired
    public void setBorrowInformationService(BorrowInformationService borrowInformationService) {
        this.borrowInformationService = borrowInformationService;
    }


    /**
     * 获取全部书籍的相关信息
     */
    @GetMapping("/allBookInformation")
    public Result getAllBookInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<BookInformationPojo> book = bookInformationService.allBooks();
        PageInfo<BookInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据书名获取书籍的全部信息
     *
     * @param title 书名
     */

    @GetMapping("/{title}")
    public Result getInformationAboutABook(@PathVariable String title) {
        BookInformationPojo bookInformationPojo = bookInformationService.findStudentsByTitle(title);
        List<BookInformationPojo> bookInformationPojos = new ArrayList<>();
        if (bookInformationPojo == null) {
            return new Result(Code.OK, null, "数据为空");
        } else {
            bookInformationPojos.add(bookInformationPojo);
            return new Result(Code.OK, bookInformationPojos, "数据获取成功");
        }
    }

    /**
     * 借阅书籍
     */
    @PostMapping
    public Result borrowBooks(@RequestBody ReturnBookPojo returnBookPojo) {
        BookInformationPojo bookInformationPojo = bookInformationService.findStudentsByTitle(returnBookPojo.getTitle());
        if (bookInformationPojo != null) {
            System.out.println(returnBookPojo);
            String account = (String) JwtUtils.validateJWT(returnBookPojo.getAccount()).getClaims().get("account");
            Boolean result = returnBookService.addReturnBook(returnBookPojo.getTitle(), account, returnBookPojo.getTime());
            if (result) {
                Boolean result1 = bookInformationService.updateBookStatus(returnBookPojo.getTitle(), 1);

                if (result1) {
                    Boolean result2 = borrowInformationService.addBorrowingRecords(bookInformationPojo.getTitle(), account, returnBookPojo.getTime(), 0, returnBookPojo.getTime() + 5184000); //添加借阅记录
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
        } else {
            return new Result(Code.OK, false, "借阅失败,请联系管理员");
        }
    }

}
