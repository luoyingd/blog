package com.example.blog.common.service.impl;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.exception.CodeAndMsg;
import com.example.blog.base.pojo.Blog;
import com.example.blog.base.request.comment.AddCommentForm;
import com.example.blog.base.request.comment.GetCommentForm;
import com.example.blog.base.response.comment.CommentVO;
import com.example.blog.common.dao.CommentDao;
import com.example.blog.common.service.inter.BlogService;
import com.example.blog.common.service.inter.CommentService;
import com.example.blog.common.service.inter.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;
    @Resource
    private UserService userService;
    @Resource
    @Lazy
    private BlogService blogService;

    @Override
    public int searchCommentCountByUserId(int userId) {
        return commentDao.searchCommentCountByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AddCommentForm addCommentForm) throws BlogException {
        if (addCommentForm.getBlogId() == 0) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        commentDao.add(addCommentForm);
        synchronized (this) {
            int comment = blogService.searchBlogCommentById(addCommentForm.getBlogId());
            Blog blog = new Blog();
            blog.setId(addCommentForm.getBlogId());
            blog.setComment(comment + 1);
            blogService.updateBlog(blog);
        }
    }

    @Override
    public List<CommentVO> getComments(GetCommentForm getCommentForm) {
        getCommentForm.setPageIndex((getCommentForm.getPageIndex() - 1) * getCommentForm.getPageSize());
        List<CommentVO> commentList = commentDao.getComment(getCommentForm);
        commentList.forEach(commentVO -> {
            if (commentVO.getReplierId() != null && commentVO.getReplierId() != 0) {
                String userName = userService.getUserInfo(commentVO.getReplierId()).getUsername();
                commentVO.setReplierName(userName);
            }
        });
        return commentList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int blogId, int commentId) throws BlogException {
        if (blogId == 0 || commentId == 0) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        commentDao.delete(commentId);
        synchronized (this) {
            int comment = blogService.searchBlogCommentById(blogId);
            Blog blog = new Blog();
            blog.setId(blogId);
            blog.setComment(comment - 1);
            blogService.updateBlog(blog);
        }
    }

    @Override
    public List<Integer> getCommentIdsByBlogId(int blogId) {
        return commentDao.getCommentIdsByBlogId(blogId);
    }

    @Override
    public void deleteBatch(int blogId) {
        commentDao.deleteBatch(blogId);
    }
}
