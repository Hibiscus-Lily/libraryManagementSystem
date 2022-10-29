package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BookInformationPojo;
import com.mujin.librarymanagementsystem.service.BookInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>管理员管理图书</h1>
 * <h3>是否鉴权  true</h3>
 * <h3>是否权限鉴权 true </h3>
 * <p>鉴权文件 {@code Admin_BookInterceptor.java }</p>
 */
@RestController
@RequestMapping(value = "/admin/book")
public class Admin_BookController {

    private BookInformationService bookInformationService;

    @Autowired
    public void setBookInformationService(BookInformationService bookInformationService) {
        this.bookInformationService = bookInformationService;
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
    public Result addBook(@RequestBody BookInformationPojo bookInformationPojo) {
        bookInformationService.addBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getPages(), bookInformationPojo.getPricing(), bookInformationPojo.getIsbn());
        return new Result(Code.OK, true, bookInformationPojo.getTitle() + "添加成功");
    }

    /**
     * 根据title更新内容
     */

    @PutMapping()
    public Result updateBook(@RequestBody BookInformationPojo bookInformationPojo) {
        bookInformationService.updateBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getPages(), bookInformationPojo.getPricing(), bookInformationPojo.getIsbn());
        return new Result(Code.OK, true, bookInformationPojo.getTitle() + "更新成功");
    }
}
