package com.example.blog.api.controller;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.pojo.User;
import com.example.blog.base.request.user.*;
import com.example.blog.base.util.R;
import com.example.blog.common.service.inter.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public R register(@RequestBody AddUserForm addUserForm) throws BlogException {
        userService.add(addUserForm);
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody LoginForm loginForm) throws BlogException {
        return R.ok(userService.login(loginForm));
    }

    @PostMapping("/logout")
    public R logout(@RequestBody UserIdForm userIdForm) throws BlogException {
        userService.logout(userIdForm);
        return R.ok();
    }

    @PostMapping("/resetPwdSendMail")
    public R resetPwdSendMail(@RequestBody EmailForm emailForm) throws BlogException {
        userService.sendMailForReset(emailForm);
        return R.ok();
    }

    @PostMapping("/resetPwd")
    public R resetPwdSend(@RequestBody ResetPwdForm resetPwdForm) throws BlogException {
        userService.resetPwd(resetPwdForm);
        return R.ok();
    }

    @PutMapping("/update")
    public R update(@RequestBody User user) throws BlogException {
        userService.update(user);
        return R.ok();
    }

    @GetMapping("/getInfo")
    public R getInfo( UserIdForm userIdForm) {
        return R.ok(userService.getUserInfo(userIdForm.getUserId()));
    }

    @GetMapping("/loadAll")
    public R loadAll() throws BlogException {
        return R.ok(userService.loadAllUserName());
    }

}
