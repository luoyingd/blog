package com.example.blog.common.dao;

import com.example.blog.base.pojo.Blog;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.response.blog.BlogLikeCountRedisData;
import com.example.blog.base.response.blog.LoadBlogContentDVO;
import com.example.blog.base.response.blog.LoadBlogDetailDVO;
import com.example.blog.base.response.blog.LoadBlogInfoDVO;
import com.example.blog.base.response.data.Top5TopicDVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface BlogDao {
    int searchBlogCount(GetBlogForm getBlogForm);

    int searchBlogCountByUserId(int userId);

    List<Top5TopicDVO> getTOP5Topic(Date start, Date end);

    List<LoadBlogInfoDVO> getBlogs(GetBlogForm getBlogForm);

    LoadBlogContentDVO loadBlogContent(int blogId);

    int searchLikeCountById(int blogId);

    void updateBlog(Blog blog);

    void batchUpdateBlogLike(List<BlogLikeCountRedisData> blogLikeCountRedisDataList);

    void addBlog(Blog blog);

    int searchBlogCommentById(int id);

    LoadBlogDetailDVO loadBlogDetail(int blogId);

    void delete(int id);
}
