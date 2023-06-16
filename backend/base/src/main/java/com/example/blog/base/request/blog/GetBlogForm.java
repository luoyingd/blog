package com.example.blog.base.request.blog;

import lombok.Data;

import java.util.Date;

@Data
public class GetBlogForm {
    private Integer authorId;
    private String topic;
    private String title;
    private Date startDate;
    private Date endDate;
    private Integer page;
    private Integer pageSize;
}
