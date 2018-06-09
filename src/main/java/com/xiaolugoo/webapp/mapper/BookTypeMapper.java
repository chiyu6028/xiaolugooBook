package com.xiaolugoo.webapp.mapper;

import com.xiaolugoo.webapp.model.BookType;

public interface BookTypeMapper {
    int deleteByPrimaryKey(Integer bookTypeId);

    int insert(BookType record);

    int insertSelective(BookType record);

    BookType selectByPrimaryKey(Integer bookTypeId);

    int updateByPrimaryKeySelective(BookType record);

    int updateByPrimaryKey(BookType record);
}