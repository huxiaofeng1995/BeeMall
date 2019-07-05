package com.beemall.page.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.beemall.mapper")
public class PageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PageServiceApplication.class, args);
    }
}
