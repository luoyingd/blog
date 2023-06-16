package com.example.blog.common.service.inter;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.Comment;
import com.example.blog.base.request.comment.AddCommentForm;
import com.example.blog.base.request.comment.GetCommentForm;
import com.example.blog.base.response.comment.CommentVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface CommentService {
    int searchCommentCountByUserId(int userId);
    void add(AddCommentForm addCommentForm) throws BlogException;

    List<CommentVO> getComments(GetCommentForm getCommentForm);

    void delete(int blogId, int commentId) throws BlogException;

    List<Integer> getCommentIdsByBlogId(int blogId);

    void deleteBatch(int blogId);
}
