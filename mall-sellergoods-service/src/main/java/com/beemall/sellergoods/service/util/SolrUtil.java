package com.beemall.sellergoods.service.util;

import com.alibaba.fastjson.JSON;
import com.beemall.mapper.TbItemMapper;
import com.beemall.mapper.TbSpecificationMapper;
import com.beemall.pojo.TbItem;
import com.beemall.pojo.TbItemExample;
import com.beemall.pojo.TbSpecification;
import com.beemall.pojo.TbSpecificationExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @Autowired
    private TbSpecificationMapper specificationMapper;

    public void importItemData(){
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//已审核
        List<TbItem> items =  itemMapper.selectByExample(example);

        for(TbItem item : items){
            item.setSolrPrice(item.getPrice().doubleValue());//solr不支持BigDecimal类型字段的存储
            Map<String,String> specMap = new HashMap<>();
            Map map = JSON.parseObject(item.getSpec(),Map.class);
            Set<String> keys = map.keySet();
            for(String key : keys){
                TbSpecificationExample example1 = new TbSpecificationExample();
                TbSpecificationExample.Criteria c = example1.createCriteria();
                c.andSpecNameEqualTo(key);
                TbSpecification spec = specificationMapper.selectByExample(example1).get(0);
                specMap.put(String.valueOf(spec.getId()),(String) map.get(key));
            }
            item.setSpecMap(specMap);
        }
        solrTemplate.saveBeans("mall_core",items);
        solrTemplate.commit("mall_core");//提交才会生效
    }
}
