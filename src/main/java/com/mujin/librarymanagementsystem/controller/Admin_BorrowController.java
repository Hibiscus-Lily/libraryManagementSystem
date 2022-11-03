package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BorrowPojo;
import com.mujin.librarymanagementsystem.service.BorrowService;
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
    private BorrowService borrowService;

    @Autowired
    public void setBorrowInformationService(BorrowService borrowService) {
        this.borrowService = borrowService;
    }




    /**
     * 获取所有借阅记录
     */
    @GetMapping("/getAllBorrowingRecords")
    public Result getAllBookInformation(@RequestParam Integer page, @RequestParam Integer limit) {
        PageHelper.startPage(page, limit);
        List<BorrowPojo> book = borrowService.findAllBorrowingRecords();
        PageInfo<BorrowPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据书名获取相关信息
     */
    @GetMapping("/getAllBorrowingRecordsForBookTile")
    public Result findAllBorrowingRecordsForBookTile(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String title) {
        PageHelper.startPage(page, limit);
        List<BorrowPojo> book = borrowService.findAllBorrowingRecordsForBookTile(title);
        PageInfo<BorrowPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }

    /**
     * 根据用户名获取相关信息
     */
    @GetMapping("/getAllBorrowingRecordsForUserAccount")
    public Result findAllBorrowingRecordsForUserAccount(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String account) {
        PageHelper.startPage(page, limit);
        List<BorrowPojo> book = borrowService.findAllBorrowingRecordsForUserAccount(account);
        PageInfo<BorrowPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }


    /**
     * 添加一条借阅记录
     */
//    @GetMapping("/addBorrowingRecords")
//    public Result addBorrowingRecords(@RequestBody BorrowPojo borrowPojos) {
//        Boolean result = borrowService.addBorrowingRecords(borrowPojos.getTitle(), borrowPojos.getAccount(), borrowPojos.getBorrowingTime(), borrowPojos.getBookReturnTime(), borrowPojos.getEstimatedReturnTime());
//        if (result) {
//            return new Result(Code.OK, true, "添加成功");
//        } else {
//            return new Result(Code.OK, false, "添加失败请检查数据完整性");
//        }
//    }

    /**
     * 根据ID删除相关记录
     */

    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable Integer id) {
        BorrowPojo borrowPojo = borrowService.findBorrowingRecordsById(id);
        if (borrowPojo != null) {
            Boolean result = borrowService.deletBorrowingRecords(id);
            if (result) {
                return new Result(Code.OK, true, "删除成功");
            } else {
                return new Result(Code.OK, false, "删除失败请检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, id + "删除失败，删除的图书不存在");
        }
    }



//    @PutMapping()
//    public Result updateBook(@RequestBody BorrowPojo borrowPojos) {
//        BorrowPojo borrowPojo = borrowService.findBorrowingRecordsById(borrowPojos.getId());
//        if (borrowPojo != null) {
//            Boolean result = borrowService.updateBorrowingRecords(borrowPojos.getId(), borrowPojos.getTitle(), borrowPojos.getAccount(), borrowPojos.getBorrowingTime(), borrowPojos.getBookReturnTime(), borrowPojos.getEstimatedReturnTime());
//            if (result) {
//                return new Result(Code.OK, true, borrowPojos.getId() + "更新成功");
//            } else {
//                return new Result(Code.OK, false, borrowPojos.getId() + "更新失败请检查数据完整性");
//            }
//        } else {
//            return new Result(Code.OK, false, borrowPojos.getId() + "更新失败，不存在此书籍");
//
//        }
//    }


}
