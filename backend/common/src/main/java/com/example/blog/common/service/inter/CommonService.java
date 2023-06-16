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
public interface CommonService {
    void uploadPhoto(String filePath, String key) throws BlogException;

    byte[] getPhoto(String key) throws BlogException, IOException;
}
