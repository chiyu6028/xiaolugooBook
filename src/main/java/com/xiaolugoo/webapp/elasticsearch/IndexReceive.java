package com.xiaolugoo.webapp.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolugoo.webapp.model.BookBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Auther: ALEX
 * @Date: 2018/5/11 18:23
 * @Description: 定义redis 队列的接收类和方法
 */

@Component
public class IndexReceive {

    Logger logger = LoggerFactory.getLogger(IndexReceive.class);

    /*
    *接收消息的方法
    * */
    public void receiveMsg(String bookBase) throws IOException {
        logger.debug("监听到消息："+bookBase);

        ObjectMapper mapper = new ObjectMapper();
        BookBase book = mapper.readValue(bookBase,BookBase.class);

    }
}
