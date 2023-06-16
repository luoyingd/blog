package com.example.blog.api;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.Blog;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.request.blog.UpdateLikeForm;
import com.example.blog.base.request.user.EmailForm;
import com.example.blog.base.response.blog.LoadBlogContentDVO;
import com.example.blog.base.response.blog.LoadBlogDetailDVO;
import com.example.blog.base.response.blog.LoadBlogInfoDVO;
import com.example.blog.base.response.data.Top5TopicDVO;
import com.example.blog.common.service.inter.BlogService;
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
public class BlogTest {
    @Resource
    private BlogService blogService;
    private static final int USER_ID = 3;
    private static final int LIKE_USER_ID = 2;
    private static int blogId;

    @Test
    public void testGetBlogs() {
        GetBlogForm getBlogForm = new GetBlogForm();
        getBlogForm.setAuthorId(3);
        getBlogForm.setTopic("Machine Learning");
        getBlogForm.setTitle("te");
        getBlogForm.setPageSize(10);
        getBlogForm.setPage(1);
        List<LoadBlogInfoDVO> blogs = blogService.getBlogs(getBlogForm);
        Assert.assertTrue(blogs.size() <= 10);
        blogs.forEach(loadBlogInfoDVO -> {
            Assert.assertEquals("Machine Learning", loadBlogInfoDVO.getTopic());
            Assert.assertEquals("dly888", loadBlogInfoDVO.getAuthor());
            Assert.assertTrue(loadBlogInfoDVO.getTitle().contains("te"));
        });
    }

    @Test
    public void testBlogCount() {
        GetBlogForm getBlogForm = new GetBlogForm();
        int blogCount = blogService.searchBlogCount(getBlogForm);
        Assert.assertTrue(blogCount > 0);
    }

    @Test
    public void testBlogCountByUserId() {
        int blogCountByUserId = blogService.searchBlogCountByUserId(USER_ID);
        Assert.assertTrue(blogCountByUserId >= 2);
    }

    @Test
    public void testTOP5TopicThisMonth() {
        List<Top5TopicDVO> top5TopicThisMonth = blogService.getTOP5TopicThisMonth();
        Assert.assertTrue(top5TopicThisMonth.size() <= 5);
        for (int i = 1; i < top5TopicThisMonth.size(); i++) {
            Assert.assertTrue(top5TopicThisMonth.get(i).getCount() <= top5TopicThisMonth.get(i - 1).getCount());
        }
    }

    @Test
    public void testBlogContent() throws BlogException {
        LoadBlogContentDVO loadBlogContentDVO = blogService.loadBlogContent(18);
        Assert.assertEquals("Machine Learning", loadBlogContentDVO.getTopic());
        Assert.assertEquals("test", loadBlogContentDVO.getTitle());
        Assert.assertEquals(18, loadBlogContentDVO.getId());
    }

    @Test
    public void testBlogDetail() throws BlogException {
        LoadBlogDetailDVO loadBlogDetailDVO = blogService.loadBlogDetail(18);
        Assert.assertEquals("Machine Learning", loadBlogDetailDVO.getTopic());
        Assert.assertEquals("test", loadBlogDetailDVO.getTitle());
        Assert.assertEquals("test", loadBlogDetailDVO.getIntroduction());
    }

    @Test
    public void testSearchBlogCommentById() throws BlogException {
        int commentById = blogService.searchBlogCommentById(18);
        Assert.assertTrue(commentById >= 3);
    }

    @Test
    @Order(1)
    public void testAddBlog() throws BlogException {
        Blog blog = new Blog();
        blog.setTitle("testAdd");
        blog.setUserId(USER_ID);
        blog.setTopic("Machine Learning");
        blog.setContent("testAdd");
        blog.setIntroduction("testAdd");
        blogService.addOrUpdateBlog(blog);
        GetBlogForm getBlogForm = new GetBlogForm();
        getBlogForm.setAuthorId(3);
        getBlogForm.setTopic("Machine Learning");
        getBlogForm.setTitle("testAdd");
        getBlogForm.setPageSize(10);
        getBlogForm.setPage(1);
        List<LoadBlogInfoDVO> blogs = blogService.getBlogs(getBlogForm);
        Assert.assertEquals("Machine Learning", blogs.get(0).getTopic());
        Assert.assertEquals("testAdd", blogs.get(0).getTitle());
        Assert.assertEquals("testAdd", blogs.get(0).getIntroduction());
        Assert.assertEquals("dly888", blogs.get(0).getAuthor());
        blogId = blogs.get(0).getId();
    }

    @Test
    @Order(2)
    public void testLikeBlog() throws BlogException {
        int likeCount = blogService.loadLikeCount(blogId);
        UpdateLikeForm updateLikeForm = new UpdateLikeForm();
        updateLikeForm.setBlogId(blogId);
        updateLikeForm.setUserId(LIKE_USER_ID);
        updateLikeForm.setLike(true);
        blogService.updateLike(updateLikeForm);
        Assert.assertTrue(blogService.loadLikeStatus(LIKE_USER_ID, blogId));
        Assert.assertEquals(blogService.loadLikeCount(blogId), likeCount + 1);
    }

    @Test
    @Order(3)
    public void testUnLikeBlog() throws BlogException {
        int likeCount = blogService.loadLikeCount(blogId);
        UpdateLikeForm updateLikeForm = new UpdateLikeForm();
        updateLikeForm.setBlogId(blogId);
        updateLikeForm.setUserId(LIKE_USER_ID);
        updateLikeForm.setLike(false);
        blogService.updateLike(updateLikeForm);
        Assert.assertFalse(blogService.loadLikeStatus(LIKE_USER_ID, blogId));
        Assert.assertEquals(blogService.loadLikeCount(blogId), likeCount - 1);
    }

    @Test
    @Order(4)
    public void testUpdateBlog() throws BlogException {
        Blog blog = new Blog();
        blog.setId(blogId);
        blog.setTitle("testUpdate");
        blogService.updateBlog(blog);
        LoadBlogDetailDVO loadBlogDetailDVO = blogService.loadBlogDetail(blogId);
        Assert.assertEquals("testUpdate", loadBlogDetailDVO.getTitle());
    }

    @Test
    @Order(5)
    public void testDeleteBlog() throws BlogException {
        blogService.delete(blogId);
        LoadBlogDetailDVO loadBlogDetailDVO = blogService.loadBlogDetail(blogId);
        Assert.assertNull(loadBlogDetailDVO);
    }
}
