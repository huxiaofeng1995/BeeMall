package com.beemall.search.service;

import java.util.Map;

public interface ItemSearchService {
    /**
     * 搜索
     * @param searchMap 查询条件
     * @return
     */
    public Map<String,Object> search(Map searchMap);

}
