package com.xiaolugoo.webapp.model;

import java.util.Date;

public class BookType {
    private Integer bookTypeId;

    private String bookTypeName;

    private String bookTypeStatus;

    private Date createDate;

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName == null ? null : bookTypeName.trim();
    }

    public String getBookTypeStatus() {
        return bookTypeStatus;
    }

    public void setBookTypeStatus(String bookTypeStatus) {
        this.bookTypeStatus = bookTypeStatus == null ? null : bookTypeStatus.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}