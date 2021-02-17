package com.emos.wx.config.shiro;

import com.emos.wx.db.dao.TbUserDao;
import com.emos.wx.db.pojo.TbUser;
import com.emos.wx.exception.EmosException;
import com.emos.wx.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证流程：
 *  小程序->XssFilter->OAuth2Filter->[OAuth2Realm认证]->OAuth2Realm授权->Controller
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }
    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 查询用户的权限列表
        /**
         * @see #doGetAuthenticationInfo(AuthenticationToken) 中可以看到在info对象中添加了user对象，所以这里可以获取到
         */
        TbUser user = (TbUser) principals.getPrimaryPrincipal();
        Integer id = user.getId();
        // 把权限列表添加到info对象中
        Set<String> permissions = userService.searchUserPermissions(id);
        info.setStringPermissions(permissions);
        return info;
    }
    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从令牌中获取userId，然后检测该账户是否被冻结。
        String accessToken = (String) token.getPrincipal();
        int userId = jwtUtil.getUserId(accessToken);
        TbUser tbUser = userService.searchUserById(userId);
        if (tbUser != null) {
            // 往info对象中添加用户信息、Token字符串
            return new SimpleAuthenticationInfo(tbUser, accessToken, getName());
        } else {
            throw new EmosException("用户不存在或已冻结，请联系管理员");
        }
    }
}