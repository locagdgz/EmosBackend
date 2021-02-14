package com.emos.wx.controller;

import com.emos.wx.common.util.R;
import com.emos.wx.config.shiro.JwtUtil;
import com.emos.wx.controller.vo.LoginForm;
import com.emos.wx.controller.vo.RegisterForm;
import com.emos.wx.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public R register(@Valid @RequestBody RegisterForm form) {

        int id = userService.registerUser(
                form.getRegisterCode(),
                form.getCode(),
                form.getNickname(),
                form.getPhoto());

        String token = jwtUtil.createToken(id);
        Set<String> permissions = userService.searchUserPermissions(id);

        saveCacheToken(token, id);

        return R.ok("注册成功").put("token", token).put("permission", permissions);
    }

    @PostMapping("/login")
    @ApiOperation("用户登陆接口")
    public R login(@Valid @RequestBody LoginForm form) {
        Integer id = userService.login(form.getCode());

        String token = jwtUtil.createToken(id);
        Set<String> permissions = userService.searchUserPermissions(id);
        saveCacheToken(token, id);

        return R.ok("登陆成功").put("token", token).put("permission", permissions);

    }

    private void saveCacheToken(String token, int userId) {
        redisTemplate.opsForValue().set(token, userId+"", cacheExpire, TimeUnit.DAYS);
    }

}
