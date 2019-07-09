package com.beemall.user.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author ：bee
 * @date ：Created in 2019/7/9 14:33
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.beemall.mapper")
@EnableJms
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
