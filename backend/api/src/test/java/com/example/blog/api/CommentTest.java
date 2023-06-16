package com.example.blog.api;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.request.comment.AddCommentForm;
import com.example.blog.base.request.comment.GetCommentForm;
import com.example.blog.base.response.comment.CommentVO;
import com.example.blog.common.service.inter.BlogService;
import com.example.blog.common.service.inter.CommentService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentTest {
    @Resource
    private CommentService commentService;
    @Resource
    private BlogService blogService;
    private static final int SENDER_ID = 3;
    private static final int REPLIER_ID = 2;
    private static final int BLOG_ID = 18;
    private static final GetCommentForm getCommentForm = new GetCommentForm();

    static {
        getCommentForm.setSenderId(SENDER_ID);
        getCommentForm.setBlogId(BLOG_ID);
        getCommentForm.setPageIndex(1);
        getCommentForm.setPageSize(10);
    }

    @Test
    public void testAddAndDeleteComment() throws BlogException {
        int comment = blogService.searchBlogCommentById(BLOG_ID);
        AddCommentForm addCommentForm = new AddCommentForm();
        addCommentForm.setBlogId(BLOG_ID);
        addCommentForm.setSenderId(SENDER_ID);
        addCommentForm.setReplierId(REPLIER_ID);
        addCommentForm.setContent("testComment");
        commentService.add(addCommentForm);
        List<CommentVO> comments = commentService.getComments(getCommentForm);
        int commentId = comments.get(0).getCommentId();
        Assert.assertEquals(REPLIER_ID, (int) comments.get(0).getReplierId());
        Assert.assertEquals(SENDER_ID, (int) comments.get(0).getSenderId());
        Assert.assertEquals("testComment", comments.get(0).getContent());
        Assert.assertEquals(blogService.searchBlogCommentById(BLOG_ID), comment + 1);
        commentService.delete(BLOG_ID, commentId);
        Assert.assertEquals(blogService.searchBlogCommentById(BLOG_ID), comment);
    }

}
