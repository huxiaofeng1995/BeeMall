package com.beemall.manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author ：bee
 * @date ：Created in 2019/6/21 16:51
 * @description：
 * @modified By：
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    /**
     * 注入我们自己定义的用户信息获取对象
     */
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
        // 这里构建来判断用户是否存在和密码是否正确
        UserInfo userInfo = (UserInfo) userDetailService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
        if (userInfo == null) {
            throw new BadCredentialsException("用户名不存在");
        }
        //根据用户名从数据库获取加密密码 dapassword

        // //这里判断密码正确与否
        //String encodePwd = new BCryptPasswordEncoder().encode(password);
        // if(!userInfo.getPassword().equals(encodePwd))
        // {
        // throw new BadCredentialsException("密码不正确");
        // }
        // //这里还可以加一些其他信息的判断，比如用户账号已停用等判断，这里为了方便我接下去的判断，我就不用加密了。
        //
        //
        if (!userInfo.getPassword().equals("123456")) {
            throw new BadCredentialsException("密码不正确");
        }
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        // 这里直接改成retrun true;表示是支持这个执行
        return true;
    }
}
