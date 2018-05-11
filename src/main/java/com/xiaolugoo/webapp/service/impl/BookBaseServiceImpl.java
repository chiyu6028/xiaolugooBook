package com.xiaolugoo.webapp.service.impl;

import com.xiaolugoo.webapp.mapper.BookBaseMapper;
import com.xiaolugoo.webapp.model.BookBase;
import com.xiaolugoo.webapp.service.BookBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Auther: ALEX
 * @Date: 2018/5/10 23:24
 * @Description:
 */
@Service
public class BookBaseServiceImpl implements BookBaseService {

    @Autowired
    BookBaseMapper bookBaseMapper;

    @Override
    public int deleteByPrimaryKey(Integer bookId) {
        return bookBaseMapper.deleteByPrimaryKey(bookId);
    }

    @Override
    public int insertSelective(BookBase record) {
        return bookBaseMapper.insertSelective(record);
    }

    @Override
    public BookBase selectByPrimaryKey(Integer bookId) {
        BookBase bookBase = bookBaseMapper.selectByPrimaryKey(bookId);
        return bookBase;
    }

    @Override
    public int updateByPrimaryKey(BookBase record) {
        return bookBaseMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<BookBase> selectAll(HashMap map) {
        return bookBaseMapper.selectAll(map);
    }
}
