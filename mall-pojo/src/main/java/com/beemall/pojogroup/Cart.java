package com.beemall.pojogroup;

import com.beemall.pojo.TbOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/7/12 11:21
 * @description：
 * @modified By：
 */
public class Cart implements Serializable {
    private String sellerId;//商家ID

    private String sellerName;//商家名称

    private List<TbOrderItem> orderItemList;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public List<TbOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<TbOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
