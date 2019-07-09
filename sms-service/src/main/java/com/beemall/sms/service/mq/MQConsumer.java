package com.beemall.sms.service.mq;

import com.beemall.entity.MQMessage;
import com.beemall.sms.service.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * @author ：bee
 * @date ：Created in 2019/7/8 10:26
 * @description：
 * @modified By：
 */
@Component
public class MQConsumer {

    private static final String REGISTRER_MSG = "sendRegisterMsg";

    private static final String REGISTRER_SUCCESS = "registerSuccess";

    @Autowired
    private SmsUtil smsUtil;

    @JmsListener(destination = "${spring.activemq.queue}")
    public void handle(Map msg){
        System.out.println("收到队列消息：" + msg);
        if(msg.get("method").equals(REGISTRER_MSG)) {
            sendRegisterMsg(msg);
        }else if(msg.get("method").equals(REGISTRER_SUCCESS)){
            //registerSuccess(msg);
        }
    }

    private void sendRegisterMsg(Map msg) {
        String signName = (String) msg.get("signName");
        String tmplatecode = (String) msg.get("regTemplateCode");
        String mobile = (String) msg.get("mobile");
        String param = (String) msg.get("param");
        smsUtil.sendRegisterMsg(mobile, tmplatecode, signName, param);
    }


}
