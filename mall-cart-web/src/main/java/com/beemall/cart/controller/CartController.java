package com.beemall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.beemall.cart.service.CartService;
import com.beemall.cart.util.CookieUtil;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/7/12 15:29
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Reference
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    /**
     * 购物车列表
     * @return
     */
    @GetMapping("/findCartList")
    public ResponseData findCartList(){
        String cartListString = CookieUtil.getCookieValue(request, "cartList","UTF-8");
        if(cartListString==null || cartListString.equals("")){
            cartListString="[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
        return ResponseDataUtil.buildSuccess(cartList_cookie);
    }

    /**
     * 添加商品到购物车
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    public ResponseData addGoodsToCartList(Long itemId, Integer num){
        List<Cart> cartList = (List<Cart>) findCartList().getData();//获取购物车列表
        cartList = cartService.addGoodsToCartList(cartList, itemId, num);
        CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList),3600*24,"UTF-8");
        return ResponseDataUtil.buildSuccess("添加成功");
    }
}

