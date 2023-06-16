package com.example.blog.base.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Blog {
    private int id;
    private int userId;
    private String content;
    private String title;
    private String topic;
    private String introduction;
    private String cover;
    private Integer likes;
    private Integer comment;
    private Date createTime;
    private Date updateTime;
}
