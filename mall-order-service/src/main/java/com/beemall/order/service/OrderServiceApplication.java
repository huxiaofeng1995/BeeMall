package com.beemall.order.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.beemall.order.service.util.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 16:33
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.beemall.mapper")
public class OrderServiceApplication {

    @Bean
    public IdWorker IdWorker(){
        return new IdWorker(0,0);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
