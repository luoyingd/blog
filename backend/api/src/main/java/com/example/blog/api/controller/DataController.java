package com.example.blog.api.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.response.data.MyDataVO;
import com.example.blog.base.response.data.TotalDataVO;
import com.example.blog.base.util.R;
import com.example.blog.common.service.inter.BlogService;
import com.example.blog.common.service.inter.CommentService;
import com.example.blog.common.service.inter.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/data")
@Slf4j
public class DataController {
    @Resource
    private BlogService blogService;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;

    @GetMapping("/getTotal")
    public R getTotal() {
        int userCount = userService.searchUserCount();
        int blogCount = blogService.searchBlogCount(new GetBlogForm());
        return R.ok(TotalDataVO.builder()
                .totalUser(userCount)
                .totalBlog(blogCount).build());
    }

    @GetMapping("/getMy")
    public R getMy() {
        int userId = StpUtil.getLoginIdAsInt();
        int blogCountByUserId = blogService.searchBlogCountByUserId(userId);
        int commentCountByUserId = commentService.searchCommentCountByUserId(userId);
        return R.ok(MyDataVO.builder()
                .totalComment(commentCountByUserId)
                .totalBlog(blogCountByUserId).build());
    }

    @GetMapping("/getTOP5Topic")
    public R getTOP5Topic() {
        return R.ok(blogService.getTOP5TopicThisMonth());
    }

    @GetMapping("/getTOP5Author")
    public R getTOP5Author() {
        return R.ok(userService.getTOP5AuthorThisMonth());
    }


}
