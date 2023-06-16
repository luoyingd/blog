package com.example.blog.api.controller;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.request.comment.AddCommentForm;
import com.example.blog.base.request.comment.GetCommentForm;
import com.example.blog.base.util.R;
import com.example.blog.common.service.inter.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping()
    public R getComments(GetCommentForm getCommentForm) {
        return R.ok(commentService.getComments(getCommentForm));
    }

    @PostMapping()
    public R addComment(@RequestBody AddCommentForm addCommentForm) throws BlogException {
        commentService.add(addCommentForm);
        return R.ok();
    }

    @DeleteMapping("/{blogId}/{commentId}")
    public R deleteComment(@PathVariable int blogId, @PathVariable int commentId) throws BlogException {
        commentService.delete(blogId, commentId);
        return R.ok();
    }


}
