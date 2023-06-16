package com.example.blog.base.response.blog;

import lombok.Data;

import java.util.Date;

@Data
public class LoadBlogInfoDVO {
    private int id;
    private String topic;
    private String author;
    private String title;
    private String introduction;
    private String cover;
    private int comments;
    private int likes;
    private Date createTime;
}
