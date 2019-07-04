package com.beemall.search.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author ：bee
 * @date ：Created in 2019/7/2 17:01
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.beemall.mapper")
public class SearchServiceApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SearchServiceApplication.class, args);
    }
}
