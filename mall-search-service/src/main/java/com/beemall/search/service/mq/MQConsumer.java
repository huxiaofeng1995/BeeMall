package com.beemall.search.service.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.beemall.entity.MQMessage;
import com.beemall.mapper.TbSpecificationMapper;
import com.beemall.pojo.TbItem;
import com.beemall.pojo.TbSpecification;
import com.beemall.pojo.TbSpecificationExample;
import com.beemall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ：bee
 * @date ：Created in 2019/7/8 10:26
 * @description：
 * @modified By：
 */
@Component
public class MQConsumer {

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private TbSpecificationMapper specificationMapper;

    @Autowired
    private ItemSearchService itemSearchService;

    @Value("${spring.data.solr.core}")
    private String solrCore;

    private static final String IMPORT_ITEMS = "importItems";

    private static final String DELETE_ITEMS = "deleteItems";

    @JmsListener(destination = "${spring.activemq.queue-solr}")
    public void handle(String msg){
        System.out.println("收到队列消息：" + msg);
        String method = JSON.parseObject(msg).getString("method");
        if(method.equals(IMPORT_ITEMS)) {
            solrImport(msg);
        }else if(method.equals(DELETE_ITEMS)){
            solrDelete(msg);
        }
    }

    private void solrDelete(String msg) {
        JSONArray jsonArray = JSON.parseObject(msg).getJSONArray("data");
        List<Long> ids = jsonArray.toJavaList(Long.class);
        itemSearchService.deleteByGoodsIds(ids);
    }

    private void solrImport(String msg){
        JSONArray jsonArray = JSON.parseObject(msg).getJSONArray("data");
        List<TbItem> itemList = jsonArray.toJavaList(TbItem.class);
        //字段处理
        for (TbItem item : itemList) {
            item.setSolrPrice(item.getPrice().doubleValue());//solr不支持BigDecimal类型字段的存储
            Map<String, String> specMap = new HashMap<>();
            Map map = JSON.parseObject(item.getSpec(), Map.class);
            Set<String> keys = map.keySet();
            for (String key : keys) {
                TbSpecificationExample example1 = new TbSpecificationExample();
                TbSpecificationExample.Criteria c = example1.createCriteria();
                c.andSpecNameEqualTo(key);
                TbSpecification spec = specificationMapper.selectByExample(example1).get(0);
                specMap.put(String.valueOf(spec.getId()), (String) map.get(key));
            }
            item.setSpecMap(specMap);
        }
        solrTemplate.saveBeans(solrCore, itemList);
        solrTemplate.commit(solrCore);
    }
}
