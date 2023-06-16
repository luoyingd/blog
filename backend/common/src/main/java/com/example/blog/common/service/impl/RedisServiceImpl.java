package com.example.blog.common.service.impl;

import com.example.blog.base.enums.LikeStatusEnum;
import com.example.blog.base.response.blog.BlogLikeCountRedisData;
import com.example.blog.base.response.blog.BlogLikeStatusRedisData;
import com.example.blog.base.util.RedisKeyUtils;
import com.example.blog.common.dao.BlogDao;
import com.example.blog.common.service.inter.RedisService;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisServiceImpl implements RedisService {
    @Resource
    private RedisTemplate<String, Integer> redisIntegerTemplate;

    @Override
    public synchronized void saveLiked2Redis(int likedUserId, int likedBlogId) {
        String key = RedisKeyUtils.MAP_KEY_USER_LIKED + RedisKeyUtils.getLikedKey(likedUserId, likedBlogId);
        redisIntegerTemplate.opsForValue().set(key, (int) LikeStatusEnum.LIKED.getValue());
    }

    @Override
    public synchronized void unlikeFromRedis(int likedUserId, int likedBlogId) {
        String key = RedisKeyUtils.MAP_KEY_USER_LIKED + RedisKeyUtils.getLikedKey(likedUserId, likedBlogId);
        redisIntegerTemplate.opsForValue().set(key, (int) LikeStatusEnum.NOT_LIKED.getValue());
    }

    @Override
    public synchronized void incrementLikedCount(int likedBlogId, int count) {
        String key = RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT + likedBlogId;
        Integer value = redisIntegerTemplate.opsForValue().get(key);
        if (value == null) {
            redisIntegerTemplate.opsForValue().set(key, count);
        } else {
            redisIntegerTemplate.opsForValue().set(key, value + count);
        }

    }

    @Override
    public synchronized void decrementLikedCount(int likedBlogId, int count) {
        String key = RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT + likedBlogId;
        Integer value = redisIntegerTemplate.opsForValue().get(key);
        if (value == null) {
            redisIntegerTemplate.opsForValue().set(key, 0);
        } else {
            redisIntegerTemplate.opsForValue().set(key, value - count);
        }
    }


    @Override
    public Integer getLikedCountFromRedis(int likedBlogId) {
        String key = RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT + likedBlogId;
        return redisIntegerTemplate.opsForValue().get(key);
    }

    @Override
    public Integer getLikedStatusFromRedis(int likedUserId, int likedBlogId) {
        String key = RedisKeyUtils.MAP_KEY_USER_LIKED + RedisKeyUtils.getLikedKey(likedUserId, likedBlogId);
        return redisIntegerTemplate.opsForValue().get(key);
    }

    @Override
    public List<BlogLikeCountRedisData> getAllLikeCountRedisDataFromRedis() {
        List<BlogLikeCountRedisData> list = new ArrayList<>();
        Set<String> keys = redisIntegerTemplate.keys(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT + "*");
        if (keys != null) {
            keys.forEach(key -> {
                Integer count = redisIntegerTemplate.opsForValue().get(key);
                if (count != null) {
                    String[] strings = key.split("_");
                    int blogId = Integer.parseInt(strings[strings.length - 1]);
                    list.add(BlogLikeCountRedisData.builder().count(count).blogId(blogId).build());
                }

            });
        }
        return list;
    }

    @Override
    public List<BlogLikeStatusRedisData> getAllLikeStatusRedisDataFromRedis() {
        List<BlogLikeStatusRedisData> list = new ArrayList<>();
        Set<String> keys = redisIntegerTemplate.keys(RedisKeyUtils.MAP_KEY_USER_LIKED + "*");
        if (keys != null) {
            keys.forEach(key -> {
                Integer like = redisIntegerTemplate.opsForValue().get(key);
                if (like != null) {
                    String[] strings = key.split("_");
                    String[] myKeys = strings[strings.length - 1].split("::");
                    int blogId = Integer.parseInt(myKeys[1]);
                    int userId = Integer.parseInt(myKeys[0]);
                    list.add(BlogLikeStatusRedisData.builder()
                            .userId(userId)
                            .blogId(blogId)
                            .like(like.byteValue())
                            .build());
                }
            });
        }
        return list;
    }

    @Override
    public void deleteLikeCountDataFromRedis() {
        Set<String> likeKeys = redisIntegerTemplate.keys(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT + "*");
        if (likeKeys != null) {
            redisIntegerTemplate.delete(likeKeys);
        }
    }

    @Override
    public void deleteLikeDataFromRedis() {
        Set<String> likeKeys = redisIntegerTemplate.keys(RedisKeyUtils.MAP_KEY_USER_LIKED + "*");
        if (likeKeys != null) {
            redisIntegerTemplate.delete(likeKeys);
        }
    }
}
