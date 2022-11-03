package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BookPojo;
import com.mujin.librarymanagementsystem.service.BookService;
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
    private BookService bookService;


    @Autowired
    public void setBookInformationService(BookService bookService) {
        this.bookService = bookService;
    }


    /**
     * 获取全部书籍的相关信息
     */
    @GetMapping("/allBookInformation")
    public Result getAllBookInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<BookPojo> book = bookService.allBooks();
        PageInfo<BookPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据书名获取书籍的全部信息
     *
     * @param title 书名
     */

    @GetMapping("/{title}")
    public Result getInformationAboutABook(@PathVariable String title) {
        BookPojo bookPojo = bookService.findStudentsByTitle(title);
        List<BookPojo> bookPojos = new ArrayList<>();
        if (bookPojo == null) {
            return new Result(Code.OK, null, "数据为空");
        } else {
            bookPojos.add(bookPojo);
            return new Result(Code.OK, bookPojos, "数据获取成功");
        }
    }

}
