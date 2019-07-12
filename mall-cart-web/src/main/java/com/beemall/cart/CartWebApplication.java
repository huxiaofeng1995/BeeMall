package com.beemall.cart;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：bee
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
public class CartWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartWebApplication.class, args);
    }
}
