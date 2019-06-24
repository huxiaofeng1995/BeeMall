package com.beemall.shop.security;

/**
 * @author ：bee
 * @date ：Created in 2019/6/21 16:25
 * @description：
 * @modified By：
 */
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbSeller;
import com.beemall.sellergoods.service.SellerService;
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

   @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> grantAuths = new ArrayList<>();
        grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));//由于改项目数据库表暂时没有 用户角色 这个字段，这里先写死

        //得到商家对象
        TbSeller seller = sellerService.findOne(username);
        if(seller != null) {
            if(seller.getStatus().equals("1")) {//1--代表审核通过，只有审核通过的商家才给登录
                return new User(username, seller.getPassword(), grantAuths);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}