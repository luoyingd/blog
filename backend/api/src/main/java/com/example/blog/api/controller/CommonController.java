package com.example.blog.api.controller;

import com.example.blog.base.constant.Constant;
import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.Blog;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.request.blog.UpdateLikeForm;
import com.example.blog.base.util.FileUtils;
import com.example.blog.base.util.R;
import com.example.blog.common.service.inter.BlogService;
import com.example.blog.common.service.inter.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Resource
    private CommonService commonService;

    @PostMapping("/uploadPhoto")
    public R uploadPhoto(@RequestParam(value = "multipartFile") MultipartFile multipartFile) throws IOException, BlogException {
        String filePath = FileUtils.saveFile(Constant.BASE_DIR, multipartFile);
        log.info("file path is " + filePath);
        String key = UUID.randomUUID() + filePath.substring(filePath.lastIndexOf("."));
        commonService.uploadPhoto(filePath, key);
        return R.ok(key);
    }

    @GetMapping("/photo/{key}")
    public void photo(@PathVariable String key,
                      HttpServletResponse httpServletResponse) throws BlogException {
        httpServletResponse.setContentType(MediaType.IMAGE_PNG_VALUE);
        try {
            OutputStream out = httpServletResponse.getOutputStream();
            out.write(commonService.getPhoto(key));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
