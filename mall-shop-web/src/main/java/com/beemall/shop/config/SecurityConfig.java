package com.beemall.shop.config;

import com.beemall.shop.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ：bee
 * @date ：Created in 2019/6/21 15:39
 * @description：
 * @modified By：
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws  Exception{
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 设置用户密码的加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//自动加盐

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //super.configure(http);
        http
                .authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers( "/*.html","/css/**","/img/**","/js/**","/plugins/**","/seller/add").permitAll()//permitAll()表示这个不需要验证
                // 任何尚未匹配的URL只需要验证用户角色即可访问
                .anyRequest().hasRole("SELLER")//在后台校验是会自动加上 "ROLE_"前缀
                .and()
                .formLogin().loginPage("/shoplogin.html").loginProcessingUrl("/login").defaultSuccessUrl("/admin/index.html",true).failureUrl("/shoplogin.html").permitAll()
                .and()
                .headers().frameOptions().sameOrigin()//不拦截iframe
                .and()
                .logout()
                .and()
                .csrf().disable();

    }

}

