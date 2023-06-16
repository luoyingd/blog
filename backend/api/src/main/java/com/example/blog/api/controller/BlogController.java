package com.example.blog.api.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.blog.base.constant.Constant;
import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.Blog;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.request.blog.UpdateLikeForm;
import com.example.blog.base.response.data.MyDataVO;
import com.example.blog.base.response.data.TotalDataVO;
import com.example.blog.base.util.FileUtils;
import com.example.blog.base.util.R;
import com.example.blog.common.service.inter.BlogService;
import com.example.blog.common.service.inter.CommentService;
import com.example.blog.common.service.inter.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogController {
    @Resource
    private BlogService blogService;

    @GetMapping("/getBlogs")
    public R getBlogs(GetBlogForm getBlogForm) {
        return R.ok(blogService.getBlogs(getBlogForm));
    }

    @GetMapping("/getBlogsCount")
    public R getBlogsCount(GetBlogForm getBlogForm) {
        return R.ok(blogService.searchBlogCount(getBlogForm));
    }

    @GetMapping("/getBlogContent/{id}")
    public R getBlogContent(@PathVariable int id) throws BlogException {
        return R.ok(blogService.loadBlogContent(id));
    }

    @GetMapping("/getBlogDetail/{id}")
    public R getBlogDetail(@PathVariable int id) throws BlogException {
        return R.ok(blogService.loadBlogDetail(id));
    }

    @GetMapping("/getLikeStatus/{blogId}/{userId}")
    public R getLikeStatus(@PathVariable int blogId, @PathVariable int userId) throws BlogException {
        return R.ok(blogService.loadLikeStatus(userId, blogId));
    }

    @GetMapping("/getBlogLikeCount/{blogId}")
    public R getBlogLikeCount(@PathVariable int blogId) throws BlogException {
        return R.ok(blogService.loadLikeCount(blogId));
    }

    @PutMapping("/updateLike")
    public R updateLike(@RequestBody UpdateLikeForm updateLikeForm) throws BlogException {
        blogService.updateLike(updateLikeForm);
        return R.ok();
    }

    @PostMapping("/addOrUpdate")
    public R addOrUpdate(@RequestBody Blog blog) throws BlogException {
        blogService.addOrUpdateBlog(blog);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable int id) {
        blogService.delete(id);
        return R.ok();
    }

}
