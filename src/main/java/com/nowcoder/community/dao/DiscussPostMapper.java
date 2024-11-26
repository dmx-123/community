package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);


    //@Param注解用来给参数取别名，
    //如果只有一个参数，且在<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

}
