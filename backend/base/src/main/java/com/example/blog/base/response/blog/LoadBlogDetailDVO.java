package com.example.blog.base.response.blog;

import lombok.Data;

import java.util.Date;

@Data
public class LoadBlogDetailDVO {
    private String topic;
    private String title;
    private String content;
    private String introduction;
    private String cover;
}
