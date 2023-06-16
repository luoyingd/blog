package com.example.blog.common.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.example.blog.base.constant.Constant;
import com.example.blog.base.exception.BlogException;
import com.example.blog.base.exception.CodeAndMsg;
import com.example.blog.base.pojo.User;
import com.example.blog.base.request.user.*;
import com.example.blog.base.response.data.Top5AuthorDVO;
import com.example.blog.base.response.user.LoadUserInfoVO;
import com.example.blog.base.response.user.LoadUserVO;
import com.example.blog.base.response.user.LoginVO;
import com.example.blog.base.util.DateUtil;
import com.example.blog.base.util.FileUtils;
import com.example.blog.base.util.MailUtil;
import com.example.blog.common.dao.UserDao;
import com.example.blog.common.service.inter.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private MailUtil mailUtil;

    @Override
    public void add(AddUserForm addUserForm) throws BlogException {
        if (StringUtils.isEmpty(addUserForm.getEmail())
                || StringUtils.isEmpty(addUserForm.getPassword())
                || StringUtils.isEmpty(addUserForm.getUsername())) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        User usernameOrEmail = userDao.searchByUsernameOrEmail(addUserForm.getUsername(), addUserForm.getEmail());
        if (usernameOrEmail != null) {
            throw new BlogException(CodeAndMsg.EXIST_USER);
        }
        User user = User.builder()
                .password(addUserForm.getPassword())
                .username(addUserForm.getUsername())
                .email(addUserForm.getEmail())
                .build();
        user.setPhoto(Constant.PHOTO_KEY);
        userDao.add(user);
    }

    @Override
    public LoginVO login(LoginForm loginForm) throws BlogException {
        if (StringUtils.isEmpty(loginForm.getUsername())
                || StringUtils.isEmpty(loginForm.getPassword())) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        User user = userDao.searchByUsernameOrEmail(loginForm.getUsername(), loginForm.getUsername());
        if (user == null) {
            throw new BlogException(CodeAndMsg.WRONG_USER_NAME);
        }
        if (!loginForm.getPassword().equals(user.getPassword())) {
            throw new BlogException(CodeAndMsg.WRONG_PASSWORD);
        }
        StpUtil.login(user.getId());
        return LoginVO.builder()
                .userId(user.getId())
                .token(StpUtil.getTokenValueByLoginId(user.getId()))
                .build();
    }

    @Override
    public void logout(UserIdForm userIdForm) throws BlogException {
        if (userIdForm.getUserId() == 0) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        StpUtil.logoutByLoginId(userIdForm.getUserId());
    }

    @Override
    public void sendMailForReset(EmailForm emailForm) throws BlogException {
        if (StringUtils.isEmpty(emailForm.getEmail())) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        User user = userDao.searchByUsernameOrEmail(null, emailForm.getEmail());
        if (user == null) {
            throw new BlogException(CodeAndMsg.WRONG_USER_NAME);
        }
        // check if the request is within 5 min
        if (Boolean.FALSE.equals(redisTemplate.hasKey(user.getEmail())) ||
                System.currentTimeMillis() - Long.parseLong(redisTemplate.opsForHash()
                        .entries(user.getEmail()).get("timestamp").toString()) > 5 * 60 * 1000L
        ) {
            // write to redis and send mail
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForHash().put(user.getEmail(), "token", token);
            redisTemplate.opsForHash().put(user.getEmail(), "timestamp", System.currentTimeMillis());
            redisTemplate.expire(user.getEmail(), 10, TimeUnit.MINUTES);
            mailUtil.sendSimpleMail(emailForm.getEmail(), Constant.EMAIL_TITLE_RESET_PWD,
                    Constant.EMAIL_CONTENT_RESET_PWD + "\n" + Constant.RESET_PWD_URL
                            + "?token=" + token + "&email=" + emailForm.getEmail());
        } else {
            throw new BlogException(CodeAndMsg.DUPLICATE_RESET);
        }
    }

    @Override
    public void resetPwd(ResetPwdForm resetPwdForm) throws BlogException {
        if (StringUtils.isEmpty(resetPwdForm.getEmail())
                || StringUtils.isEmpty(resetPwdForm.getToken())
                || StringUtils.isEmpty(resetPwdForm.getPassword())) {
            throw new BlogException(CodeAndMsg.PARAM_VERIFICATION_FAIL);
        }
        // check token
        if (Boolean.FALSE.equals(redisTemplate.hasKey(resetPwdForm.getEmail()))) {
            throw new BlogException(CodeAndMsg.EXPIRED_RESET_TOKEN);
        }
        Object token = redisTemplate.opsForHash().get(resetPwdForm.getEmail(), "token");
        if (token == null || !resetPwdForm.getToken().equals(token.toString())) {
            throw new BlogException(CodeAndMsg.EXPIRED_RESET_TOKEN);
        }

        User searchByUsernameOrEmail = userDao.searchByUsernameOrEmail(null, resetPwdForm.getEmail());
        User user = new User();
        user.setId(searchByUsernameOrEmail.getId());
        user.setPassword(resetPwdForm.getPassword());
        userDao.update(user);
    }

    @Override
    public LoadUserInfoVO getUserInfo(int id) {
        return userDao.searchUserInfoById(id);
    }

    @Override
    public int searchUserCount() {
        return userDao.searchUserCount();
    }

    @Override
    public List<Top5AuthorDVO> getTOP5AuthorThisMonth() {
        int year = DateUtil.calculateYearFromDate(new Date());
        int month = DateUtil.calculateMonthFromDate(new Date());
        Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(String.valueOf(year), String.valueOf(month));
        Date lastDayOfMonth = DateUtil.getLastDayOfMonth(String.valueOf(year), String.valueOf(month));
        return userDao.getTOP5Author(firstDayOfMonth, lastDayOfMonth);
    }

    @Override
    public List<LoadUserVO> loadAllUserName() {
        return userDao.loadAllUserName();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
