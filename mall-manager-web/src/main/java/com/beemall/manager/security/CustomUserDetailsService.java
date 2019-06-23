package com.beemall.manager.security;

/**
 * @author ：bee
 * @date ：Created in 2019/6/21 16:25
 * @description：
 * @modified By：
 */
import com.beemall.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 认证和授权
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

//    @Reference
//    private TbUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //--------------------认证账号 数据库查询改用户名（账号）是否存在记录
        //        TbUser user = userService.loadUserByUsername(s);
        //这里可以可以通过username（登录时输入的用户名）然后到数据库中找到对应的用户信息，并构建成我们自己的UserInfo来返回。

        UserInfo userInfo= null;
        if(username.equals("admin"))
        {
            //假设返回的用户信息如下;
            userInfo = new UserInfo("admin", "123456", "ROLE_ADMIN", true,true,true, true);
            return userInfo;

        }
        return userInfo;
    }
}