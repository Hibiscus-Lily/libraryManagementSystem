package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BookInformationPojo;
import com.mujin.librarymanagementsystem.pojo.BorrowInformationPojo;
import com.mujin.librarymanagementsystem.service.BookInformationService;
import com.mujin.librarymanagementsystem.service.BorrowInformationService;
import com.mujin.librarymanagementsystem.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    private BorrowInformationService borrowInformationService;

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
     * 根据书名删除一本图书
     *
     * @param title 书名
     */

    @DeleteMapping("/{title}")
    public Result deleteBook(@PathVariable String title) {
        BookInformationPojo bookInformation = bookInformationService.findStudentsByTitle(title);
        if (bookInformation != null) {
            Boolean result = bookInformationService.deleteBooks(title);
            if (result) {
                return new Result(Code.OK, true, "删除成功");
            } else {
                return new Result(Code.OK, false, "删除失败请检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, title + "删除失败，删除的图书不存在");

        }

    }

    /**
     * 添加一本新的图书
     */

    @PostMapping
    public Result addBook(@RequestBody BookInformationPojo bookInformationPojo) {
        BookInformationPojo bookInformation = bookInformationService.findStudentsByTitle(bookInformationPojo.getTitle());
        if (bookInformation == null) {
            Boolean result = bookInformationService.addBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getIsbn(), bookInformationPojo.getState());
            if (result) {
                return new Result(Code.OK, true, bookInformationPojo.getTitle() + "添加成功");
            } else {
                return new Result(Code.OK, false, bookInformationPojo.getTitle() + "添加失败请检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, bookInformationPojo.getTitle() + "添加失败，库中存在此书");
        }
    }

    /**
     * 根据title更新内容
     */

    @PutMapping()
    public Result updateBook(@RequestBody BookInformationPojo bookInformationPojo, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        BookInformationPojo bookInformation = bookInformationService.findStudentsByTitle(bookInformationPojo.getTitle()); //根据书名检查是否存在
        //存在
        if (bookInformation != null) {
            //检查书籍是否存在于借阅记录
            BorrowInformationPojo borrowInformationPojo = borrowInformationService.theLatestInformationOnBorrowedBooks(bookInformationPojo.getTitle());
            //存在存在于借阅记录
            if (borrowInformationPojo != null) {
                //检查提交的状态为0&&数据库中的状态为1 --->  还书
                if ((bookInformationPojo.getState() == 0 && bookInformation.getState() == 1)) {
                    int bookReturnTime = (int) (new Date().getTime() / 1000); //还书时间
                    Boolean result = borrowInformationService.updateBookReturnTimeById(borrowInformationPojo.getId(), bookReturnTime);
                    //更新记录成功
                    if (result) {
                        //更新书籍信息
                        Boolean result1 = bookInformationService.updateBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getIsbn(), bookInformationPojo.getState());
                        if (result1) {
                            map.put("data", true);
                            map.put("msg", bookInformationPojo.getTitle() + "更新成功");
                        } else {
                            map.put("data", false);
                            map.put("msg", bookInformationPojo.getTitle() + "更新失败请检查数据完整性");
                        }
                    } else {
                        map.put("data", false);
                        map.put("msg", bookInformationPojo.getTitle() + "更新失败,还书失败");
                    }
                }
                //检查提交的状态为1&&数据库中的状态为0 --->  借书
                if ((bookInformationPojo.getState() == 1 && bookInformation.getState() == 0)) {
                    String token = request.getHeader("token");
                    String account = (String) JwtUtils.validateJWT(token).getClaims().get("account");
                    int borrowingTime = (int) (new Date().getTime() / 1000); //还书时间
                    Boolean result1 = borrowInformationService.addBorrowingRecords(bookInformationPojo.getTitle(), account, borrowingTime, 0, borrowingTime + 5184000); //添加借阅记录
                    //添加借阅记录（成功）
                    if (result1) {
                        Boolean result = bookInformationService.updateBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getIsbn(), bookInformationPojo.getState());
                        if (result) {
                            map.put("data", true);
                            map.put("msg", bookInformationPojo.getTitle() + "更新成功");
                        } else {
                            map.put("data", false);
                            map.put("msg", bookInformationPojo.getTitle() + "更新失败请检查数据完整性");
                        }
                    } else {
                        map.put("data", false);
                        map.put("msg", bookInformationPojo.getTitle() + "更新失败,借书失败");
                    }
                }
            }
            //不存在借阅记录
            else {
                //检查提交的状态为0&&数据库中的状态为1 --->  还书
                if ((bookInformationPojo.getState() == 0 && bookInformation.getState() == 1)) {
                    Boolean result = bookInformationService.updateBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getIsbn(), bookInformationPojo.getState());
                    if (result) {
                        map.put("data", true);
                        map.put("msg", bookInformationPojo.getTitle() + "更新成功");
                    } else {
                        map.put("data", false);
                        map.put("msg", bookInformationPojo.getTitle() + "更新失败请检查数据完整性");
                    }
                }
                //检查提交的状态为1&&数据库中的状态为0 --->  借书
                if ((bookInformationPojo.getState() == 1 && bookInformation.getState() == 0)) {
                    String token = request.getHeader("token");
                    String account = (String) JwtUtils.validateJWT(token).getClaims().get("account");
                    int borrowingTime = (int) (new Date().getTime() / 1000); //还书时间
                    Boolean result = borrowInformationService.addBorrowingRecords(bookInformationPojo.getTitle(), account, borrowingTime, 0, borrowingTime + 5184000); //添加借阅记录
                    if (result) {
                        Boolean result1 = bookInformationService.updateBooks(bookInformationPojo.getTitle(), bookInformationPojo.getAuthor(), bookInformationPojo.getPress(), bookInformationPojo.getYear(), bookInformationPojo.getIsbn(), bookInformationPojo.getState());
                        if (result1) {
                            map.put("data", true);
                            map.put("msg", bookInformationPojo.getTitle() + "更新成功");
                        } else {
                            map.put("data", false);
                            map.put("msg", bookInformationPojo.getTitle() + "更新失败请检查数据完整性");
                        }
                    } else {
                        map.put("data", false);
                        map.put("msg", bookInformationPojo.getTitle() + "更新失败,借阅失败");
                    }
                }
            }
            return new Result(Code.OK, map.get("data"), map.get("msg"));

        } else {
            return new Result(Code.OK, false, bookInformationPojo.getTitle() + "更新失败，不存在此书籍");

        }
    }

    @GetMapping("/getAllBorrowingRecordsForBookTile")
    public Result findAllBorrowingRecordsForBookTile(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String title) {
        PageHelper.startPage(page, limit);
        List<BorrowInformationPojo> book = borrowInformationService.findAllBorrowingRecordsForBookTile(title);
        PageInfo<BorrowInformationPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }
}
