package com.xiaolugoo.webapp.service.impl;

import com.xiaolugoo.webapp.elasticsearch.BookDao;
import com.xiaolugoo.webapp.mapper.BookBaseMapper;
import com.xiaolugoo.webapp.model.BookBase;
import com.xiaolugoo.webapp.service.BookBaseService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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

    Logger logger = LoggerFactory.getLogger(BookBaseServiceImpl.class);

    @Autowired
    BookBaseMapper bookBaseMapper;

    @Autowired
    BookDao bookDao;

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

    @Override
    public List<BookBase> findBookAllByEs(HashMap map) {

        String key = String.valueOf(map.get("key"));
        Boolean order = (Boolean) map.get("order") == null ? true : false;
        String bookStatus = String.valueOf(map.get("bookStatus")) == "null" ? "1" : String.valueOf(map.get("bookStatus"));
        Integer pageOffset = (Integer) map.get("pageOffset") == null ? 0 : (Integer) map.get("pageOffset");
        Integer pageSize = (Integer) map.get("pageSize") == null ? 9 : (Integer) map.get("pageSize");

        if (key == null || key == "null"){
            logger.debug("未输入搜索关键字");
            return null;
        }

        //创建builder
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //builder下有must、should以及mustNot 相当于sql中的and、or以及not

        //设置模糊搜索，多字段匹配
        builder.must(QueryBuilders.multiMatchQuery( key, "bookIntr", "bookAuth", "bookName"));
        //builder.should(QueryBuilders.fuzzyQuery("bookAuth", key));
        //builder.should(QueryBuilders.fuzzyQuery("bookName", key));

        //设置性别必须为man
        builder.must(new QueryStringQueryBuilder(bookStatus).field("bookStatus"));

        //按照录入时间从高到低
        FieldSortBuilder sort = SortBuilders.fieldSort("bookDate").order(order?SortOrder.DESC:SortOrder.ASC);

        //设置分页(拿第一页，一页显示两条)
        //注意!es的分页api是从第0页开始的(坑)
        PageRequest page = PageRequest.of(pageOffset,pageSize);

        //构建查询
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //将搜索条件设置到构建中
        nativeSearchQueryBuilder.withQuery(builder);
        //将分页设置到构建中
        nativeSearchQueryBuilder.withPageable(page);
        //将排序设置到构建中
        nativeSearchQueryBuilder.withSort(sort);
        //生产NativeSearchQuery
        NativeSearchQuery query = nativeSearchQueryBuilder.build();

        Page<BookBase> books = bookDao.search(query);

        List<BookBase> bookBaseList = books.getContent();

        return bookBaseList;
    }
}
