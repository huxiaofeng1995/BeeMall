package com.beemall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.beemall.cart.service.CartService;
import com.beemall.cart.util.CookieUtil;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.pojogroup.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登录用户：" + username);
        Map<String, Object> map = new HashMap<>();
        map.put("username" , username);
        if(username.equals("anonymousUser")) {//用户未登陆,从cookie中读取
            String cartListString = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
            if (cartListString == null || cartListString.equals("")) {
                cartListString = "[]";
            }
            List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);
            map.put("list", cartList_cookie);
        }else {//用户已登录，从redis中读取
            map.put("list", cartService.findCartListFromRedis(username));
        }
        return ResponseDataUtil.buildSuccess(map);
    }

    /**
     * 添加商品到购物车
     * @param itemId
     * @param num
     * @return
     */
    @RequestMapping("/addGoodsToCartList")
    public ResponseData addGoodsToCartList(Long itemId, Integer num){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("当前登录用户：" + username);
        List<Cart> cartList = (List<Cart>) ((Map) findCartList().getData()).get("list");//获取购物车列表
        cartList = cartService.addGoodsToCartList(cartList, itemId, num);
        if(username.equals("anonymousUser")) {
            CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList), 3600 * 24, "UTF-8");

        }else {
            cartService.saveCartListToRedis(username, cartList);
        }
        return ResponseDataUtil.buildSuccess("添加成功");
    }
}

