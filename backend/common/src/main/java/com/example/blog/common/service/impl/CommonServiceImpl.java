package com.example.blog.common.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.blog.base.enums.LikeStatusEnum;
import com.example.blog.base.enums.TopicEnum;
import com.example.blog.base.exception.BlogException;
import com.example.blog.base.exception.CodeAndMsg;
import com.example.blog.base.pojo.Blog;
import com.example.blog.base.pojo.Like;
import com.example.blog.base.request.blog.GetBlogForm;
import com.example.blog.base.request.blog.UpdateLikeForm;
import com.example.blog.base.response.blog.*;
import com.example.blog.base.response.data.Top5TopicDVO;
import com.example.blog.base.util.DateUtil;
import com.example.blog.base.util.FileUtils;
import com.example.blog.common.dao.BlogDao;
import com.example.blog.common.dao.LikeDao;
import com.example.blog.common.service.inter.BlogService;
import com.example.blog.common.service.inter.CommentService;
import com.example.blog.common.service.inter.CommonService;
import com.example.blog.common.service.inter.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Override
    public void uploadPhoto(String filePath, String key) throws BlogException {
        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(key)) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        PutObjectResult putObjectResult = FileUtils.uploadFile(key, filePath);
        if (putObjectResult == null) {
            throw new BlogException(CodeAndMsg.FILE_UPLOAD_FAIL);
        }
        File file = new File(filePath);
        boolean delete = file.delete();
        log.info("delete file: " + delete + ": " + filePath);
    }

    @Override
    public byte[] getPhoto(String key) throws BlogException, IOException {
        if (StringUtils.isEmpty(key)) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        return FileUtils.downloadPhoto(key);
    }

}
