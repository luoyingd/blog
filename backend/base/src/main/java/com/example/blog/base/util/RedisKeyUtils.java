package com.example.blog.base.util;

public class RedisKeyUtils {
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED_";
    public static final String MAP_KEY_BLOG_LIKED_COUNT = "MAP_BLOG_LIKED_COUNT_";

    public static String getLikedKey(int likedUserId, int blogId) {
        return likedUserId +
                "::" +
                blogId;
    }
}
