package com.beemall.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.entity.ResponseData;
import com.beemall.pojo.TbBrand;
import com.beemall.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<TbBrand> findAll(){
        return brandService.findAll();
    }

    @GetMapping("/findPage")
    public ResponseData findPage(int page, int size){
        return brandService.findPage(page, size);
    }
}
