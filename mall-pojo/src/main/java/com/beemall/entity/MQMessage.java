package com.beemall.entity;

/**
 * @author ：bee
 * @date ：Created in 2019/7/8 10:55
 * @description：
 * @modified By：
 */
public class MQMessage {

    private String method;

    private Object data;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
