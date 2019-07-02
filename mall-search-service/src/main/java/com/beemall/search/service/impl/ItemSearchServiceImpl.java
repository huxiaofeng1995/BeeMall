package com.beemall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.pojo.TbItem;
import com.beemall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：bee
 * @date ：Created in 2019/7/2 14:30
 * @description：
 * @modified By：
 */
@Service(timeout = 5000)//设置5秒调用超时时间
public class ItemSearchServiceImpl implements ItemSearchService{

    @Autowired
    private SolrTemplate solrTemplate;

    @Value("${spring.data.solr.core}")
    private String solrCore;

    @Override
    public ResponseData search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        String keyword = (String) searchMap.get("keywords");
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_keywords").is(keyword);
        query.addCriteria(criteria);
        ScoredPage<TbItem> page = solrTemplate.queryForPage(solrCore, query, TbItem.class);
        map.put("rows", page.getContent());
        return ResponseDataUtil.buildSuccess(map);
    }
}
