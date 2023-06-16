package com.example.blog.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    private int id;
    private int userId;
    private int blogId;
    private Byte status;
    private Date createTime;
    private Date updateTime;
}
