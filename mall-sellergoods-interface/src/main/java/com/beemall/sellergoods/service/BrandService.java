package com.beemall.sellergoods.service;

import com.beemall.entity.ResponseData;
import com.beemall.pojo.TbBrand;

import java.util.List;

/**
 * @author ：bee
 * @date ：Created in 2019/6/18 15:25
 * @description：
 * @modified By：
 */
public interface BrandService {
    public List<TbBrand> findAll();

    /**
     * 品牌分页
     * @param pageSize 每页记录数
     * @param pageNum   当前页码
     * @return
     */
    public ResponseData findPage(int pageNum, int pageSize);

    /**
     * 添加品牌
     * @param tbBrand
     * @return
     */
    public ResponseData add(TbBrand tbBrand);

    public ResponseData update(TbBrand tbBrand);

    public ResponseData delete(Long[] ids);

    /**
     * 条件查询
     * @param brand
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ResponseData findPageByExample(TbBrand brand, int pageNum,int pageSize);
}
