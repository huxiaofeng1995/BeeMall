package com.beemall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.cart.service.CartService;
import com.beemall.mapper.TbItemMapper;
import com.beemall.pojo.TbItem;
import com.beemall.pojo.TbOrderItem;
import com.beemall.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/7/12 15:03
 * @description：
 * @modified By：
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
        //1.根据商品SKU ID查询SKU商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        if(item==null){
            throw new RuntimeException("商品不存在");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException("商品状态无效");
        }
        //2.获取商家ID
        String sellerId = item.getSellerId();
        //3.根据商家ID判断购物车列表中是否存在该商家的购物车
        Cart cart = findCartBySellerId(cartList, sellerId);
        if(cart == null){
            //4.如果购物车列表中不存在该商家的购物车
            //4.1 新建购物车对象
            Cart c = new Cart();
            c.setSellerId(sellerId);
            c.setSellerName(item.getSeller());
            List<TbOrderItem> orderItemList = new ArrayList<>();
            TbOrderItem orderItem = createOrderItem(item, num);
            orderItemList.add(orderItem);
            //4.2 将新建的购物车对象添加到购物车列表
            c.setOrderItemList(orderItemList);
            cartList.add(c);
        }else {
            //5.如果购物车列表中存在该商家的购物车
            // 查询购物车明细列表中是否存在该商品
            TbOrderItem orderItem = findOrderItemByItemId(cart, itemId);
            if(orderItem == null){
                //5.1. 如果没有，新增购物车明细
                orderItem = createOrderItem(item, num);
                cart.getOrderItemList().add(orderItem);
            }else {
                //5.2. 如果有，在原购物车明细上添加数量，更改金额
                orderItem.setNum(orderItem.getNum() + num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getNum()*orderItem.getPrice().doubleValue()));
                //如果数量操作后小于等于0，则移除
                if(orderItem.getNum()<=0){
                    cart.getOrderItemList().remove(orderItem);//移除购物车明细
                }
                //如果移除后cart的明细数量为0，则将cart移除
                if(cart.getOrderItemList().size()==0){
                    cartList.remove(cart);
                }
            }
        }

        return cartList;
    }

    private TbOrderItem findOrderItemByItemId(Cart cart, Long itemId) {
        for(TbOrderItem item : cart.getOrderItemList()){
            if(item.getItemId().longValue() == itemId.longValue()){//Long类型比较数据需要先转为long
                return item;
            }
        }
        return null;
    }

    private Cart findCartBySellerId(List<Cart> cartList, String sellerId) {
        for(Cart cart : cartList){
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

    /**
     * 创建订单明细
     * @param item
     * @param num
     * @return
     */
    private TbOrderItem createOrderItem(TbItem item, Integer num){
        if(num<=0){
            throw new RuntimeException("数量非法");
        }
        TbOrderItem orderItem=new TbOrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*num));
        return orderItem;
    }

    @Override
    public List<Cart> findCartListFromRedis(String username) {
        return null;
    }

    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {

    }

    @Override
    public List<Cart> mergeCartList(List<Cart> cartList1, List<Cart> cartList2) {
        return null;
    }
}
