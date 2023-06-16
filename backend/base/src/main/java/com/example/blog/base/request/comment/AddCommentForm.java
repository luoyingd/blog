package com.example.blog.base.request.comment;

import lombok.Data;

import java.util.Date;

@Data
public class AddCommentForm {
    private Integer senderId;
    private String content;
    private Integer blogId;
    private Integer replierId;
}
