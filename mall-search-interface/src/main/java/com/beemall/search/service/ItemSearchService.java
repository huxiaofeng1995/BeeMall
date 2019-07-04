package com.beemall.search.service;

import com.beemall.entity.ResponseData;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    /**
     * 搜索
     * @param searchMap 查询条件
     * @return
     */
    public ResponseData search(Map searchMap);

    /**
     * 导入数据
     * @param list
     */
    public void importList(List list);


    /**
     * 删除数据
     * @param goodsIdList
     */
    public void deleteByGoodsIds(List goodsIdList);

}
