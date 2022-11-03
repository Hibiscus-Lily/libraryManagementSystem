package com.mujin.librarymanagementsystem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mujin.librarymanagementsystem.common.constant.Code;
import com.mujin.librarymanagementsystem.common.entity.Result;
import com.mujin.librarymanagementsystem.pojo.BookPojo;
import com.mujin.librarymanagementsystem.pojo.BorrowPojo;
import com.mujin.librarymanagementsystem.service.BookService;
import com.mujin.librarymanagementsystem.service.BorrowService;
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

    private BookService bookService;
    private BorrowService borrowService;

    @Autowired
    public void setBookInformationService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setBorrowInformationService(BorrowService borrowService) {
        this.borrowService = borrowService;
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

    /**
     * 根据书名删除一本图书
     *
     * @param title 书名
     */

    @DeleteMapping("/{title}")
    public Result deleteBook(@PathVariable String title) {
        BookPojo bookInformation = bookService.findStudentsByTitle(title);
        if (bookInformation != null) {
            Boolean result = bookService.deleteBooks(title);
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
    public Result addBook(@RequestBody BookPojo bookPojo) {
        BookPojo bookInformation = bookService.findStudentsByTitle(bookPojo.getTitle());
        if (bookInformation == null) {
            Boolean result = bookService.addBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
            if (result) {
                return new Result(Code.OK, true, bookPojo.getTitle() + "添加成功");
            } else {
                return new Result(Code.OK, false, bookPojo.getTitle() + "添加失败请检查数据完整性");
            }
        } else {
            return new Result(Code.OK, false, bookPojo.getTitle() + "添加失败，库中存在此书");
        }
    }

    /**
     * 根据title更新内容
     */

    @PutMapping()
    public Result updateBook(@RequestBody BookPojo bookPojo, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        BookPojo bookInformation = bookService.findStudentsByTitle(bookPojo.getTitle()); //根据书名检查是否存在
        //存在
        if (bookInformation != null) {
            //检查书籍是否存在于借阅记录
            BorrowPojo borrowPojo = borrowService.theLatestInformationOnBorrowedBooks(bookPojo.getTitle());
            //存在存在于借阅记录
            if (borrowPojo != null) {
                //检查提交的状态为0&&数据库中的状态为1 --->  还书
                if ((bookPojo.getState() == 0 && bookInformation.getState() == 1)) {
                    int bookReturnTime = (int) (new Date().getTime() / 1000); //还书时间
                    Boolean result = borrowService.updateBookReturnTimeById(borrowPojo.getId(), bookReturnTime);
                    //更新记录成功
                    if (result) {
                        //更新书籍信息
                        Boolean result1 = bookService.updateBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
                        if (result1) {
                            map.put("data", true);
                            map.put("msg", bookPojo.getTitle() + "更新成功");
                        } else {
                            map.put("data", false);
                            map.put("msg", bookPojo.getTitle() + "更新失败请检查数据完整性");
                        }
                    } else {
                        map.put("data", false);
                        map.put("msg", bookPojo.getTitle() + "更新失败,还书失败");
                    }
                }
                //检查提交的状态为1&&数据库中的状态为0 --->  借书
                else if ((bookPojo.getState() == 1 && bookInformation.getState() == 0)) {
                    String token = request.getHeader("token");
                    String account = (String) JwtUtils.validateJWT(token).getClaims().get("account");
                    int borrowingTime = (int) (new Date().getTime() / 1000); //还书时间
                    Boolean result1 = borrowService.addBorrowingRecords(bookPojo.getTitle(), account, borrowingTime, 0, borrowingTime + 5184000); //添加借阅记录
                    //添加借阅记录（成功）
                    if (result1) {
                        Boolean result = bookService.updateBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
                        if (result) {
                            map.put("data", true);
                            map.put("msg", bookPojo.getTitle() + "更新成功");
                        } else {
                            map.put("data", false);
                            map.put("msg", bookPojo.getTitle() + "更新失败请检查数据完整性");
                        }
                    } else {
                        map.put("data", false);
                        map.put("msg", bookPojo.getTitle() + "更新失败,借书失败");
                    }
                } else {
                    Boolean result = bookService.updateBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
                    if (result) {
                        return new Result(Code.OK, true, "更新成功");
                    } else {
                        return new Result(Code.OK, false, bookPojo.getTitle() + "更新失败,借书失败");
                    }
                }
            }
            //不存在借阅记录
            else {
                //检查提交的状态为0&&数据库中的状态为1 --->  还书
                if ((bookPojo.getState() == 0 && bookInformation.getState() == 1)) {
                    Boolean result = bookService.updateBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
                    if (result) {
                        map.put("data", true);
                        map.put("msg", bookPojo.getTitle() + "更新成功");
                    } else {
                        map.put("data", false);
                        map.put("msg", bookPojo.getTitle() + "更新失败请检查数据完整性");
                    }
                }
                //检查提交的状态为1&&数据库中的状态为0 --->  借书
                else if ((bookPojo.getState() == 1 && bookInformation.getState() == 0)) {
                    String token = request.getHeader("token");
                    String account = (String) JwtUtils.validateJWT(token).getClaims().get("account");
                    int borrowingTime = (int) (new Date().getTime() / 1000); //还书时间
                    Boolean result = borrowService.addBorrowingRecords(bookPojo.getTitle(), account, borrowingTime, 0, borrowingTime + 5184000); //添加借阅记录
                    if (result) {
                        Boolean result1 = bookService.updateBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
                        if (result1) {
                            map.put("data", true);
                            map.put("msg", bookPojo.getTitle() + "更新成功");
                        } else {
                            map.put("data", false);
                            map.put("msg", bookPojo.getTitle() + "更新失败请检查数据完整性");
                        }
                    } else {
                        map.put("data", false);
                        map.put("msg", bookPojo.getTitle() + "更新失败,借阅失败");
                    }
                } else {
                    Boolean result = bookService.updateBooks(bookPojo.getTitle(), bookPojo.getAuthor(), bookPojo.getPress(), bookPojo.getYear(), bookPojo.getIsbn(), bookPojo.getState());
                    if (result) {
                        return new Result(Code.OK, true, "更新成功");
                    } else {
                        return new Result(Code.OK, false, bookPojo.getTitle() + "更新失败,借书失败");

                    }
                }
            }
            return new Result(Code.OK, map.get("data"), map.get("msg"));

        } else {
            return new Result(Code.OK, false, bookPojo.getTitle() + "更新失败，不存在此书籍");

        }
    }

    @GetMapping("/getAllBorrowingRecordsForBookTile")
    public Result findAllBorrowingRecordsForBookTile(@RequestParam Integer page, @RequestParam Integer limit, @RequestParam String title) {
        PageHelper.startPage(page, limit);
        List<BorrowPojo> book = borrowService.findAllBorrowingRecordsForBookTile(title);
        PageInfo<BorrowPojo> pageInfo = new PageInfo<>(book);
        return new Result(Code.OK, pageInfo, "数据获取成功");
    }
}
