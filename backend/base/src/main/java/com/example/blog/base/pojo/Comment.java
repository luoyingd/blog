package com.example.blog.base.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int id;
    private int senderId;
    private int blogId;
    private int replierId;
    private String content;
    private Date createTime;
    private Date updateTime;
}
