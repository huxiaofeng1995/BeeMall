package com.beemall.content.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 16:33
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.beemall.mapper")
public class ContentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }
}
