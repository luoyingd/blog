package com.example.blog.common.dao;
import com.example.blog.base.pojo.User;
import com.example.blog.base.response.data.Top5AuthorDVO;
import com.example.blog.base.response.user.LoadUserInfoVO;
import com.example.blog.base.response.user.LoadUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface UserDao {
    void add(User user);

    User searchByUsernameOrEmail(String username, String email);

    void update(User user);

    LoadUserInfoVO searchUserInfoById(int userId);

    String searchPhotoById(int userId);

    int searchUserCount();

    List<Top5AuthorDVO> getTOP5Author(Date start, Date end);
    List<LoadUserVO> loadAllUserName();
}
