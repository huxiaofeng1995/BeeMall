package com.beemall.shop;

//import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 16:52
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
public class ShopWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopWebApplication.class, args);
    }
}
