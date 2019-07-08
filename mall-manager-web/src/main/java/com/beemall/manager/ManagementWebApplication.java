package com.beemall.manager;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 16:52
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
@EnableJms
public class ManagementWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementWebApplication.class, args);
    }
}
