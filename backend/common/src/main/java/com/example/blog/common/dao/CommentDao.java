package com.example.blog.common.dao;
import com.example.blog.base.pojo.Comment;
import com.example.blog.base.request.comment.AddCommentForm;
import com.example.blog.base.request.comment.GetCommentForm;
import com.example.blog.base.response.comment.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {
    int searchCommentCountByUserId(int userId);

    void add(AddCommentForm addCommentForm);

    List<CommentVO> getComment(GetCommentForm getCommentForm);

    void delete(int id);

    List<Integer> getCommentIdsByBlogId(int blogId);

    void deleteBatch(int blogId);
}
