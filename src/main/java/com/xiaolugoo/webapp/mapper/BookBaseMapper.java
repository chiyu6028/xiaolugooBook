package com.xiaolugoo.webapp.mapper;

import com.xiaolugoo.webapp.model.BookBase;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BookBaseMapper {

    @Delete("DELETE FROM t_book_base WHERE book_id = #{bookId}")
    int deleteByPrimaryKey(Integer bookId);

    @Insert("INSERT INTO t_book_base " +
            " (book_id, user_id, book_name, book_type_id, book_intro, book_auth, book_image1, book_image2, book_image3, book_status, book_date) " +
            " VALUES " +
            " (#{bookId}, #{userId}, #{bookName}, #{bookTypeId}, #{bookIntro}, #{bookAuth}, #{bookImage1}, #{bookImage2}, #{bookImage3}, #{bookStatus}, now()) ")
    @Options(useGeneratedKeys = true, keyProperty = "bookId", keyColumn = "book_id")
    int insertSelective(BookBase record);

    @Select("SELECT book_id, user_id, book_name, book_type_id, book_intro, book_auth, book_image1, book_image2, book_image3, book_status, book_date" +
            " FROM t_book_base WHERE book_id = #{bookId}")
    @Results({
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "bookName", column = "book_name"),
            @Result(property = "bookTypeId", column = "book_type_id"),
            @Result(property = "bookIntro", column = "book_intro"),
            @Result(property = "bookAuth", column = "book_auth"),
            @Result(property = "bookImage1", column = "book_image1"),
            @Result(property = "bookImage2", column = "book_image2"),
            @Result(property = "bookImage3", column = "book_image3"),
            @Result(property = "bookStatus", column = "book_status"),
            @Result(property = "bookDate", column = "book_date"),
    })
    BookBase selectByPrimaryKey(Integer bookId);

    @Update("UPDATE t_book_base " +
            " SET user_id = #{userId}, book_name = #{bookName}, book_type_id = #{bookTypeId}, book_intro = #{bookIntro}, book_auth = #{bookAuth}, " +
            "     book_image1 = #{bookImage1}, book_image2 = #{bookImage2}, book_image3 = #{bookImage3} ,book_status =#{bookStatus} " +
            " WHERE book_id = #{bookId} ")
    int updateByPrimaryKey(BookBase record);

    @Select("<script> " +
            " SELECT book_id, user_id, book_name, book_type_id, book_intro, book_auth, book_image1, book_image2, " +
            "        book_image3, book_status, DATE_FORMAT(book_date,'%Y%m%d') bookDate " +
            " FROM t_book_base WHERE 1 = 1 " +
            " <if test = \" bookId != null \"> and book_id = #{bookId} </if> " +
            " <if test = \" userId != null \"> and user_id = #{userId} </if> " +
            " <if test = \" beginDate != null \"> and DATE_FORMAT(book_date,'%Y%m%d') + 0 &gt;= #{beginDate} + 0 </if> " +
            " <if test = \" endDate != null \"> and DATE_FORMAT(book_date,'%Y%m%d') + 0 &lt; #{endDate} + 0 </if> " +
            " <if test = \" bookStatus != null \"> and book_status = #{bookStatus} </if> " +
            "</script>")
    @Results({
            @Result(property = "bookId", column = "book_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "bookName", column = "book_name"),
            @Result(property = "bookTypeId", column = "book_type_id"),
            @Result(property = "bookIntro", column = "book_intro"),
            @Result(property = "bookAuth", column = "book_auth"),
            @Result(property = "bookImage1", column = "book_image1"),
            @Result(property = "bookImage2", column = "book_image2"),
            @Result(property = "bookImage3", column = "book_image3"),
            @Result(property = "bookStatus", column = "book_status"),
            @Result(property = "bookDate", column = "book_date"),
    })
    List<BookBase> selectAll(HashMap map);
}