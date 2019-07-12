package com.beemall.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：bee
 * @date ：Created in 2019/7/12 11:15
 * @description：
 * @modified By：
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "forward:/cart.html";
    }
}
