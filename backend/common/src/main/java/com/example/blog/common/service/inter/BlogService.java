package com.example.blog.common.service.inter;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.Blog;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.request.blog.UpdateLikeForm;
import com.example.blog.base.response.blog.LoadBlogContentDVO;
import com.example.blog.base.response.blog.LoadBlogDetailDVO;
import com.example.blog.base.response.blog.LoadBlogInfoDVO;
import com.example.blog.base.response.data.Top5TopicDVO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface BlogService {
    int searchBlogCount(GetBlogForm getBlogForm);

    int searchBlogCountByUserId(int userId);

    List<Top5TopicDVO> getTOP5TopicThisMonth();

    List<LoadBlogInfoDVO> getBlogs(GetBlogForm getBlogForm);

    LoadBlogContentDVO loadBlogContent(int blogId) throws BlogException;

    boolean loadLikeStatus(int userId, int blogId) throws BlogException;

    void updateLike(UpdateLikeForm updateLikeForm) throws BlogException;

    int loadLikeCount(int blogId);

    void saveLikeDataFromRedis();

    void addOrUpdateBlog(Blog blog) throws BlogException;

    int searchBlogCommentById(int id);

    void updateBlog(Blog blog) throws BlogException;

    LoadBlogDetailDVO loadBlogDetail(int blogId) throws BlogException;

    void delete(int id);
}
