package com.beemall.search.service;

import com.beemall.entity.ResponseData;

import java.util.Map;

public interface ItemSearchService {
    /**
     * 搜索
     * @param searchMap 查询条件
     * @return
     */
    public ResponseData search(Map searchMap);

}
