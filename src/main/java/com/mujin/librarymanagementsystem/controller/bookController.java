package com.mujin.librarymanagementsystem.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.bookInformation;
import com.mujin.librarymanagementsystem.service.bookInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 书籍相关操作
 */
@RestController
@RequestMapping(value = "/book")

public class bookController {

    private bookInformationService bookInformationService;

    @Autowired
    public void setBookInformationService(com.mujin.librarymanagementsystem.service.bookInformationService bookInformationService) {
        this.bookInformationService = bookInformationService;
    }

    /**
     * 获取全部书籍的相关信息
     */
    @GetMapping("/allBookInformation")
    public Result getAllBookInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<bookInformation> book = bookInformationService.allBooks();
        PageInfo<bookInformation> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据书名获取书籍的全部信息
     *
     * @param title 书名
     */

    @GetMapping("/{title}")
    public Result getInformationAboutABook(@PathVariable String title) {
        bookInformation book = bookInformationService.findStudentsByTitle(title);
        List<bookInformation> bookList = new ArrayList<>();
        if (book == null) {
            bookInformation bookInformation = new bookInformation();
            bookList.add(bookInformation);
            return new Result(Code.OK, bookList, "数据为空");
        } else {
            bookList.add(book);
            return new Result(Code.OK, bookList, "数据获取成功");
        }
    }

    /**
     * 根据书名删除一本图书
     *
     * @param title 书名
     */

    @DeleteMapping("/{title}")
    public Result deleteBook(@PathVariable String title) {
        bookInformationService.deleteBooks(title);
        return new Result(Code.OK, true, title + "删除成功");
    }

    /**
     * 添加一本新的图书
     */

    @PostMapping
    public Result addBook(@RequestBody bookInformation bookInformation) {
        bookInformationService.addBooks(bookInformation.getTitle(), bookInformation.getAuthor(), bookInformation.getPress(), bookInformation.getYear(), bookInformation.getPages(), bookInformation.getPricing(), bookInformation.getIsbn());
        return new Result(Code.OK, true, bookInformation.getTitle() + "添加成功");
    }

    /**
     * 根据title更新内容
     */

    @PutMapping()
    public Result updateBook(@RequestBody bookInformation bookInformation) {
        bookInformationService.updateBooks(bookInformation.getTitle(), bookInformation.getAuthor(), bookInformation.getPress(), bookInformation.getYear(), bookInformation.getPages(), bookInformation.getPricing(), bookInformation.getIsbn());
        return new Result(Code.OK, true, bookInformation.getTitle() + "更新成功");
    }

}
