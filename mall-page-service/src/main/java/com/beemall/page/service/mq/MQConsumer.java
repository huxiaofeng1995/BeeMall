package com.beemall.page.service.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.beemall.page.service.ItemPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/7/8 10:26
 * @description：
 * @modified By：
 */
@Component
public class MQConsumer {

    @Autowired
    private ItemPageService itemPageService;

    private static final String GENERATE_PAGES = "generatePages";

    private static final String DELETE_PAGES= "deletePages";

    @JmsListener(destination = "${spring.activemq.topic}")
    public void handle(String msg){
        System.out.println("收到队列消息：" + msg);
        String method = JSON.parseObject(msg).getString("method");
        if(method.equals(GENERATE_PAGES)) {
            generatePages(msg);
        }else if(method.equals(DELETE_PAGES)){
            deletePages(msg);
        }
    }

    private void deletePages(String msg) {
        JSONArray jsonArray = JSON.parseObject(msg).getJSONArray("data");
        List<Long> ids = jsonArray.toJavaList(Long.class);
        itemPageService.deleteHtml(ids.toArray(new Long[ids.size()]));
    }

    private void generatePages(String msg) {
        JSONArray jsonArray = JSON.parseObject(msg).getJSONArray("data");
        List<Long> ids = jsonArray.toJavaList(Long.class);
        for(Long id : ids) {
            itemPageService.genItemHtml(id);
        }
    }
}
