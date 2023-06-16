package com.example.blog.base.response.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Top5AuthorDVO {
    private String username;
    private int id;
    private int comments;
}
