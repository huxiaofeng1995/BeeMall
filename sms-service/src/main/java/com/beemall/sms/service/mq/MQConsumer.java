package com.beemall.sms.service.mq;

import com.beemall.entity.MQMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


/**
 * @author ：bee
 * @date ：Created in 2019/7/8 10:26
 * @description：
 * @modified By：
 */
@Component
public class MQConsumer {


    @Value("${spring.data.solr.core}")
    private String solrCore;

    private static final String REGISTRER_MSG = "sendRegisterMsg";

    private static final String REGISTRER_SUCCESS = "registerSuccess";

    @JmsListener(destination = "${spring.activemq.queue}")
    public void handle(MQMessage msg){
        System.out.println("收到队列消息：" + msg);
        if(msg.getMethod().equals(REGISTRER_MSG)) {
            sendRegisterMsg(msg);
        }else if(msg.getMethod().equals(REGISTRER_SUCCESS)){
            //registerSuccess(msg);
        }
    }

    private void sendRegisterMsg(MQMessage msg) {

    }


}
