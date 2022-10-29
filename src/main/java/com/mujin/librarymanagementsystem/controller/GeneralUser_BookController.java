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

@RestController
@RequestMapping("/commonuser/book")
public class GeneralUser_BookController {
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
        BookInformationPojo book = bookInformationService.findStudentsByTitle(title);
        List<BookInformationPojo> bookList = new ArrayList<>();
        if (book == null) {
            return new Result(Code.OK, null, "数据为空");
        } else {
            bookList.add(book);
            return new Result(Code.OK, bookList, "数据获取成功");
        }
    }
}
