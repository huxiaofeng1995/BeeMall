package com.beemall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbBrand;
import com.beemall.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 16:54
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }
}
