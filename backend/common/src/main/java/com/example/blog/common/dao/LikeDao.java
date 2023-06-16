package com.example.blog.common.dao;

import com.example.blog.base.pojo.Like;
import com.example.blog.base.response.blog.BlogLikeStatusRedisData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface LikeDao {
    Byte loadLikeStatus(int userId, int blogId);

    void batchAddLike(List<BlogLikeStatusRedisData> likeList);

    void batchUpdateLike(List<BlogLikeStatusRedisData> likeList);

    List<Like> batchSearchLike(List<BlogLikeStatusRedisData> blogLikeStatusRedisData);

    void deleteBatch(int blogId);
}
