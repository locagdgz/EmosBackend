package com.emos.wx.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.emos.wx.common.constants.UserConstants;
import com.emos.wx.db.dao.TbUserDao;
import com.emos.wx.db.pojo.TbUser;
import com.emos.wx.exception.EmosException;
import com.emos.wx.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Value("${wx.open-id-url}")
    private String openIdUrl;

    @Value("${wx.grant-type}")
    private String grantType;

    @Autowired
    private TbUserDao userDao;

    private String getOpenId(String jsCode) {
        Map map = new HashMap();

        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", jsCode);
        map.put("grant_type", grantType);

        System.out.println(appId);
        System.out.println(appSecret);
        System.out.println(openIdUrl);
        System.out.println(grantType);

        String response = HttpUtil.post(openIdUrl, map);

        JSONObject json = JSONUtil.parseObj(response);

        System.out.println(json);
        String openId = json.getStr("openid");

        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("临时登陆凭证错误");
        }

        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        if (UserConstants.SUPER_ADMIN_REGISTER_CODE.equals(registerCode)) {
            // 超级管理员注册
            if (!userDao.haveRootUser()) {
                String openId = getOpenId(code);
                TbUser insertUser = new TbUser()
                        .setOpenId(openId)
                        .setNickname(nickname)
                        .setPhoto(photo)
                        .setRole(UserConstants.SUPER_ADMIN_ROLE)
                        .setStatus(1)
                        .setCreateTime(new Date())
                        .setRoot(true);

                userDao.insert(insertUser);
                int id = userDao.searchIdByOpenId(openId);
                return id;
            } else {
                throw new EmosException("无法绑定超级管理员账号");
            }
        } else {

        }
        return 0;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = userDao.searchUserPermissions(userId, UserConstants.STATUS_ON);
        return permissions;
    }

    @Override
    public Integer login(String code) {
        String openId = getOpenId(code);
        Integer id = userDao.searchIdByOpenId(openId);
        if (id == null) {
            throw new EmosException("账户不存在");
        }
        // TODO 获取消息
        return id;
    }
}
