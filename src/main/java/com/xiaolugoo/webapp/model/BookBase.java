package com.xiaolugoo.webapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @Auther: ALEX
 * @Date: 2018/5/10 23:29
 * @Description: elasticsearch 实体类
 */
@Document(indexName = "xiaolugoo",type = "bookBase")
public class BookBase {

    @Id
    private Integer bookId;

    private Integer userId;

    private String bookName;

    private Integer bookTypeId;

    private String bookIntro;

    private String bookAuth;

    private String bookImage1;

    private String bookImage2;

    private String bookImage3;

    private String bookStatus;

    private String bookDate;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBookIntro() {
        return bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro == null ? null : bookIntro.trim();
    }

    public String getBookAuth() {
        return bookAuth;
    }

    public void setBookAuth(String bookAuth) {
        this.bookAuth = bookAuth == null ? null : bookAuth.trim();
    }

    public String getBookImage1() {
        return bookImage1;
    }

    public void setBookImage1(String bookImage1) {
        this.bookImage1 = bookImage1 == null ? null : bookImage1.trim();
    }

    public String getBookImage2() {
        return bookImage2;
    }

    public void setBookImage2(String bookImage2) {
        this.bookImage2 = bookImage2 == null ? null : bookImage2.trim();
    }

    public String getBookImage3() {
        return bookImage3;
    }

    public void setBookImage3(String bookImage3) {
        this.bookImage3 = bookImage3 == null ? null : bookImage3.trim();
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus == null ? null : bookStatus.trim();
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
}