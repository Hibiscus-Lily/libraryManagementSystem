package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BorrowInformationPojo;
import com.mujin.librarymanagementsystem.service.BorrowInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 根据书名获取相关信息
     */
    @GetMapping("/getAllBorrowingRecordsForBookTile")
    public Result findAllBorrowingRecordsForBookTile(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String title) {
        PageHelper.startPage(page, limit);
        List<BorrowInformationPojo> book = borrowInformationService.findAllBorrowingRecordsForBookTile(title);
        PageInfo<BorrowInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据用户名获取相关信息
     */
    @GetMapping("/getAllBorrowingRecordsForUserAccount")
    public Result findAllBorrowingRecordsForUserAccount(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String account) {
        PageHelper.startPage(page, limit);
        List<BorrowInformationPojo> book = borrowInformationService.findAllBorrowingRecordsForUserAccount(account);
        PageInfo<BorrowInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据ID删除相关记录
     */

    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable Integer id) {
        BorrowInformationPojo borrowInformationPojo = borrowInformationService.findBorrowingRecordsById(id);
        if (borrowInformationPojo != null) {
            Boolean result = borrowInformationService.deletBorrowingRecords(id);
            if (result) {
                return new Result(Code.OK, true, "删除成功");
            } else {
                return new Result(Code.OK, false, "删除失败请检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, id + "删除失败，删除的图书不存在");
        }
    }


    @PutMapping()
    public Result updateBook(@RequestBody BorrowInformationPojo borrowInformationPojos) {
        BorrowInformationPojo borrowInformationPojo = borrowInformationService.findBorrowingRecordsById(borrowInformationPojos.getId());
        if (borrowInformationPojo != null) {
            Boolean result = borrowInformationService.updateBorrowingRecords(borrowInformationPojos.getId(), borrowInformationPojos.getTitle(), borrowInformationPojos.getAccount(), borrowInformationPojos.getBorrowingTime(), borrowInformationPojos.getBookReturnTime(), borrowInformationPojos.getEstimatedReturnTime());
            if (result) {
                return new Result(Code.OK, true, borrowInformationPojos.getId() + "更新成功");
            } else {
                return new Result(Code.OK, false, borrowInformationPojos.getId() + "更新失败请检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, borrowInformationPojos.getId() + "更新失败，不存在此书籍");

        }
    }


}
