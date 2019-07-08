package com.beemall.page.service;

/**
 * @author ：bee
 * @date ：Created in 2019/7/5 10:16
 * @description：商品详细页接口
 * @modified By：
 */
public interface ItemPageService {
    /**
     * 生成商品详细页
     * @param goodsId
     */
    public boolean genItemHtml(Long goodsId);

    /**
     * 删除商品详情页
     * @param goodsId
     */
    public boolean deleteHtml(Long[] goodsIds);
}

