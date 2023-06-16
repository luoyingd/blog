package com.example.blog.common.service.inter;

import com.example.blog.base.response.blog.BlogLikeCountRedisData;
import com.example.blog.base.response.blog.BlogLikeStatusRedisData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RedisService {
    void saveLiked2Redis(int likedUserId, int likedBlogId);
    void unlikeFromRedis(int likedUserId, int likedBlogId);
    void incrementLikedCount(int likedBlogId, int count);
    void decrementLikedCount(int likedBlogId, int count);
    Integer getLikedCountFromRedis(int likedBlogId);
    Integer getLikedStatusFromRedis(int likedUserId, int likedBlogId);
    List<BlogLikeCountRedisData> getAllLikeCountRedisDataFromRedis();
    List<BlogLikeStatusRedisData> getAllLikeStatusRedisDataFromRedis();
    void deleteLikeCountDataFromRedis();
    void deleteLikeDataFromRedis();
}
