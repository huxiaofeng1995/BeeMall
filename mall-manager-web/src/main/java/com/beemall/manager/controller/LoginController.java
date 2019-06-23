package com.beemall.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：bee
 * @date ：Created in 2019/6/21 17:15
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("name")
    public Map name(){
        String name= SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Map map=new HashMap();
        map.put("loginName", name);
        return map ;
    }
}
