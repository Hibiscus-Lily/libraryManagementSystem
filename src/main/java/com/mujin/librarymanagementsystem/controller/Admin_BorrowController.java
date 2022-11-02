package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BorrowInformationPojo;
import com.mujin.librarymanagementsystem.service.BorrowInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    /**
     * 获取全部书籍的相关信息
     */
    @GetMapping("/getAllBorrowingRecords")
    public Result getAllBookInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<BorrowInformationPojo> book = borrowInformationService.findAllBorrowingRecords();
        PageInfo<BorrowInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 获取全部书籍的相关信息
     */
    @GetMapping("/getAllBorrowingRecordsForBookTile")
    public Result findAllBorrowingRecordsForBookTile(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String title) {
        PageHelper.startPage(page, limit);
        List<BorrowInformationPojo> book = borrowInformationService.findAllBorrowingRecordsForBookTile(title);
        PageInfo<BorrowInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 获取全部书籍的相关信息
     */
    @GetMapping("/getAllBorrowingRecordsForUserAccount")
    public Result findAllBorrowingRecordsForUserAccount(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String account) {
        PageHelper.startPage(page, limit);
        List<BorrowInformationPojo> book = borrowInformationService.findAllBorrowingRecordsForUserAccount(account);
        PageInfo<BorrowInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }


}
