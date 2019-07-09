package com.beemall.sms.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * @author ：bee
 * @date ：Created in 2019/7/9 12:52
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableJms
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }
}
