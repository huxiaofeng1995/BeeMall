package com.beemall.sellergoods.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.beemall.sellergoods.service.util.SolrUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 16:33
 * @description：
 * @modified By：
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.beemall.mapper")
public class SellerServiceApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SellerServiceApplication.class, args);
//        SolrUtil solrUtil = context.getBean(SolrUtil.class);
//        solrUtil.importItemData();
    }
}
