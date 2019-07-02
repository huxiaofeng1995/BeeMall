package com.beemall.sellergoods.service.util;

import com.alibaba.fastjson.JSON;
import com.beemall.mapper.TbItemMapper;
import com.beemall.pojo.TbItem;
import com.beemall.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ：bee
 * @date ：Created in 2019/7/2 14:39
 * @description：
 * @modified By：
 */
@Component
public class SolrUtil {
    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TbItemMapper itemMapper;

    public void importItemData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//已审核
        List<TbItem> items =  itemMapper.selectByExample(example);

        for(TbItem item : items){
            item.setSolrPrice(item.getPrice().doubleValue());//solr不支持BigDecimal类型字段的存储
            item.setSpecMap(JSON.parseObject(item.getSpec(),Map.class));
        }
        solrTemplate.saveBeans("mall_core",items);
        solrTemplate.commit("mall_core");//提交才会生效
    }
}
