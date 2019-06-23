package com.beemall.manager.config;

import com.beemall.manager.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
    private AuthenticationProvider provider;  //注入我们自己的AuthenticationProvider

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义认证方式从数据库获取用户信息并进行验证
        auth.authenticationProvider(provider);
        //使用写死的方式进行验证
//                auth
//                .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                    .withUser("admin").password("123456").roles("USER")
//                    .and()
//                    .withUser("test").password("test123").roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        //super.configure(http);
        http
                .authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers( "/login.html","/css/**","/img/**","/js/**","/plugins/**","/login-error.html").permitAll()//permitAll()表示这个不需要验证
                // 任何尚未匹配的URL只需要验证用户即可访问
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.html").loginProcessingUrl("/login").defaultSuccessUrl("/admin/index.html").failureUrl("/login-error.html").permitAll()
//                .successHandler(myAuthenticationSuccessHandler).failureUrl("/login-error.html")
//                .failureHandler(myAuthenticationFailHander).permitAll() //表单登录， 登录页面，登录失败页面
                .and()
                .headers().frameOptions().sameOrigin()//不拦截iframe
                .and()
                .logout()
                .and()
                .csrf().disable();

    }

}

