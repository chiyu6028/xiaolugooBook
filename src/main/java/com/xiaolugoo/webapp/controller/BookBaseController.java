package com.xiaolugoo.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolugoo.webapp.model.BookBase;
import com.xiaolugoo.webapp.service.BookBaseService;
import com.xiaolugoo.webapp.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ALEX
 * @Date: 2018/5/10 23:29
 * @Description:
 */

@Api(value = "书本")
@RestController
@RequestMapping(value = "book")
public class BookBaseController {

    Logger logger = LoggerFactory.getLogger(BookBaseController.class);

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    BookBaseService bookBaseService;

    @ApiOperation(value = "书本新增", notes = "用户新增一本书")
    @ApiImplicitParam(name = "bookBase", value = "书本详细实体", dataType = "BookBase")
    @RequestMapping(value = "/insert", method = {RequestMethod.GET,RequestMethod.POST})
    public String insertSelective(BookBase bookBase) throws JsonProcessingException {
        logger.debug("书本新增");
        ResultData resultData = null;
        try {
            int res = bookBaseService.insertSelective(bookBase);
            if (res > 0){
                //向redis队列发送一条数据，用来创建elasticsearch索引
                redisTemplate.convertAndSend("elasticsearch",objectMapper.writeValueAsString(bookBase));

                List resList = new ArrayList();
                resList.add(res);
                resultData = new ResultData("1","新增成功！",resList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultData = new ResultData("0","新增失败！");
        }
        return objectMapper.writeValueAsString(resultData);
    }

    @ApiOperation(value = "修改书本",notes = "修改书本详情")
    @ApiImplicitParam(name = "bookBase", value = "书本详情", dataType = "BookBase")
    @RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
    public String userUpdate(BookBase bookBase) throws JsonProcessingException {
        logger.debug("修改书本详情");
        ResultData result = new ResultData();
        int res = 0;
        try {
            res = bookBaseService.updateByPrimaryKey(bookBase);
            List resList = new ArrayList();
            resList.add(res);
            if (res > 0){
                result.setFlag("1");
                result.setMsg("修改成功！");
                result.setResult(resList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag("0");
            result.setMsg("新增失败！");
        }
        return objectMapper.writeValueAsString(result);
    }

    @ApiOperation(value = "查询书本",notes = "根据书的编号查询书本信息")
    @ApiImplicitParam(name = "bookId", value = "书本ID", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "/selectByPrimaryKey/{bookId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String selectByPrimaryKey(@PathVariable("bookId") Integer bookId) throws JsonProcessingException {
        logger.debug("根据编号查询书本");
        ResultData resultData = null;
        try {
            BookBase bookBase = bookBaseService.selectByPrimaryKey(bookId);
            if (bookBase != null){
                List bookList = new ArrayList();
                bookList.add(bookBase);
                resultData = new ResultData("1","查询成功！",bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultData = new ResultData("0","查询失败！");
        }
        return objectMapper.writeValueAsString(resultData);
    }

    @ApiOperation(value = "批量查询", notes = "根据不同条件批量查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookId", value = "用户编号", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "userId", value = "用户编号", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "beginDate", value = "开始日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bookStatus", value = "登陆状态", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/selectAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String selectAll(@PathParam("bookId") Integer bookId,
                            @PathParam("userId") Integer userId,
                            @PathParam("beginDate") String beginDate,
                            @PathParam("endDate") String endDate,
                            @PathParam("bookStatus") String bookStatus) throws JsonProcessingException {
        logger.debug("批量查询书本");
        ResultData resultData = null;
        HashMap bookMap = new HashMap();
        bookMap.put("bookId", bookId);
        bookMap.put("userId", userId);
        bookMap.put("beginDate", beginDate);
        bookMap.put("endDate", endDate);
        bookMap.put("bookStatus", bookStatus);
        try {
            List<BookBase> bookBaseList = bookBaseService.selectAll(bookMap);
            if (bookBaseList.size() > 0){
                resultData = new ResultData("1","查询成功！",bookBaseList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultData = new ResultData("0","查询失败！");
        }
        return objectMapper.writeValueAsString(resultData);
    }

    @ApiOperation(value = "删除书本",notes = "根据书的编号删除书本")
    @ApiImplicitParam(name = "bookId", value = "书本ID", required = true, paramType = "path", dataType = "Integer")
    @RequestMapping(value = "/deleteByPrimaryKey/{bookId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteByPrimaryKey(@PathVariable("bookId") Integer bookId) throws JsonProcessingException {
        logger.debug("根据编号查询书本");
        ResultData resultData = null;
        try {
            int res = bookBaseService.deleteByPrimaryKey(bookId);
            if (res > 0){
                List bookList = new ArrayList();
                bookList.add(res);
                resultData = new ResultData("1","删除成功！",bookList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultData = new ResultData("0","删除失败！");
        }
        return objectMapper.writeValueAsString(resultData);
    }

}
