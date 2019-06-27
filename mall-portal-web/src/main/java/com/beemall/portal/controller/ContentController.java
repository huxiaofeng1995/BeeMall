package com.beemall.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.content.service.ContentService;
import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：bee
 * @date ：Created in 2019/6/27 11:09
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Reference
    private ContentService contentService;

    /**
     * 根据广告分类ID查询广告列表
     * @param categoryId
     * @return
     */
    @GetMapping("/findByCategoryId")
    public ResponseData findByCategoryId(Long categoryId) {
        return contentService.findByCategoryId(categoryId);
    }
}

