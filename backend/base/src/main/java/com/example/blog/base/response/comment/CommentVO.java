package com.example.blog.base.response.comment;

import lombok.Data;

import java.util.Date;

@Data
public class CommentVO {
    private Integer commentId;
    private Integer senderId;
    private Integer replierId;
    private String senderName;
    private String replierName;
    private String content;
    private Date createTime;
    private String senderPhoto;
}
