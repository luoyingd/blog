package com.example.blog.base.response.blog;

import lombok.Data;

import java.util.Date;

@Data
public class LoadBlogContentDVO {
    private int id;
    private String topic;
    private String author;
    private String authorPhoto;
    private String title;
    private String content;
    private int likes;
    private Date createTime;
}
