package com.example.blog.common.service.inter;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.User;
import com.example.blog.base.request.user.*;
import com.example.blog.base.response.data.Top5AuthorDVO;
import com.example.blog.base.response.user.LoadUserInfoVO;
import com.example.blog.base.response.user.LoadUserVO;
import com.example.blog.base.response.user.LoginVO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    void add(AddUserForm addUserForm) throws BlogException;

    LoginVO login(LoginForm loginForm) throws BlogException;

    void logout(UserIdForm userIdForm) throws BlogException;

    void sendMailForReset(EmailForm emailForm) throws BlogException;

    void resetPwd(ResetPwdForm resetPwdForm) throws BlogException;

    LoadUserInfoVO getUserInfo(int id);

    int searchUserCount();

    List<Top5AuthorDVO> getTOP5AuthorThisMonth();

    List<LoadUserVO> loadAllUserName();

    void update(User user);
}
