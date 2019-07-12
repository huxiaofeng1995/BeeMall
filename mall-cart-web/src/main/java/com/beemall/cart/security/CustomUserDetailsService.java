package com.beemall.cart.security;

/**
 * @author ：bee
 * @date ：Created in 2019/7/12 11:25
 * @description：
 * @modified By：
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证和授权
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> grantAuths = new ArrayList<>();
        grantAuths.add(new SimpleGrantedAuthority("ROLE_USER"));//由于改项目数据库表暂时没有 用户角色 这个字段，这里先写死

        return new User(username, "", grantAuths);
    }
}