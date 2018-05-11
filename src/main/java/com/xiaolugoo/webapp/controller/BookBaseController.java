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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            @ApiImplicitParam(name = "bookId", value = "用户编号", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "beginDate", value = "开始日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "bookStatus", value = "登陆状态", paramType = "query", dataType = "String")
    })
    @RequestMapping(value = "/selectAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String selectAll(@PathParam("bookId") String bookId,
                                    @PathParam("beginDate") String beginDate,
                                    @PathParam("endDate") String endDate,
                                    @PathParam("bookStatus") String bookStatus) throws JsonProcessingException {
        ResultData resultData = null;
        HashMap bookMap = new HashMap();
        bookMap.put("bookId", bookId);
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

}
