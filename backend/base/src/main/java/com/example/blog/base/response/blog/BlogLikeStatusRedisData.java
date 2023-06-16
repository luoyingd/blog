package com.example.blog.base.response.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogLikeStatusRedisData {
    private int blogId;
    private int userId;
    private Byte like;
}
