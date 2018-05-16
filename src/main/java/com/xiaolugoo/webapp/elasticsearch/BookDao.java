package com.xiaolugoo.webapp.elasticsearch;

import com.xiaolugoo.webapp.model.BookBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @Auther: ALEX
 * @Date: 2018/5/14 13:21
 * @Description:
 */


public interface BookDao extends ElasticsearchRepository<BookBase, Integer> {

    /*Page<BookBase> getByBookIntro(String key, Pageable pageable);*/
}
