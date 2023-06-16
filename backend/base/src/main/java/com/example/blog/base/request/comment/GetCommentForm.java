package com.example.blog.base.request.comment;

import lombok.Data;

@Data
public class GetCommentForm {
    private Integer blogId;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer senderId;
}
