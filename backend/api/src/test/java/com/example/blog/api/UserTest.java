package com.example.blog.api;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.User;
import com.example.blog.base.request.user.AddUserForm;
import com.example.blog.base.request.user.LoginForm;
import com.example.blog.base.request.user.UserIdForm;
import com.example.blog.base.response.user.LoadUserInfoVO;
import com.example.blog.base.response.user.LoadUserVO;
import com.example.blog.base.response.user.LoginVO;
import com.example.blog.common.service.inter.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Resource
    private UserService userService;
    private static int userId;
    private static final String username = UUID.randomUUID().toString();
    private static final AddUserForm addUserForm = new AddUserForm();
    private static final LoginForm loginForm = new LoginForm();

    static {
        addUserForm.setEmail(username + "@1.com");
        addUserForm.setUsername(username);
        addUserForm.setPassword("000000");
        loginForm.setUsername(username);
        loginForm.setPassword("000000");
    }

    @Test
    @Order(1)
    public void testAdd() throws BlogException {
        userService.add(addUserForm);
        LoginVO login = userService.login(loginForm);
        Assert.assertTrue(StringUtils.isNotEmpty(login.getToken()));
        userId = login.getUserId();
    }

    @Test
    @Order(2)
    public void testLogout() throws BlogException {
        UserIdForm userIdForm = new UserIdForm();
        userIdForm.setUserId(userId);
        userService.logout(userIdForm);
    }

    @Test
    @Order(3)
    public void testInfo() {
        LoadUserInfoVO userInfo = userService.getUserInfo(userId);
        Assert.assertEquals(userInfo.getUsername(), username);
    }

    @Test
    @Order(4)
    public void testSearchUserCount() {
        int userCount = userService.searchUserCount();
        Assert.assertTrue(userCount >= 1);
    }

    @Test
    @Order(5)
    public void testLoadAllUserName() {
        List<LoadUserVO> loadUserVOS = userService.loadAllUserName();
        Assert.assertTrue(loadUserVOS.size() >= 1);
    }

    @Test
    @Order(6)
    public void testUpdate() {
        User user = new User();
        user.setId(userId);
        user.setUsername(username + "1");
        userService.update(user);
        LoadUserInfoVO userInfo = userService.getUserInfo(userId);
        Assert.assertEquals(username + "1", userInfo.getUsername());
    }
}
