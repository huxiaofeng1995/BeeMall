package com.beemall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.pojo.TbItem;
import com.beemall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.HashMap;
import java.util.List;
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
//        Query query = new SimpleQuery();
//        Criteria criteria = new Criteria("item_keywords").is(keyword);
//        query.addCriteria(criteria);
//        ScoredPage<TbItem> page = solrTemplate.queryForPage(solrCore, query, TbItem.class);
//        map.put("rows", page.getContent());
        HighlightQuery query = new SimpleHighlightQuery();
        HighlightOptions options = new HighlightOptions();
        options.addField("item_title");//设置高亮域
        options.setSimplePrefix("<em style='color:red'>");//高亮前缀
        options.setSimplePostfix("</em>");
        query.setHighlightOptions(options);
        Criteria criteria = new Criteria("item_keywords").is(keyword);
        query.addCriteria(criteria);
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(solrCore, query, TbItem.class);

        //需要从返回高亮对象中获取高亮结果再返回，即在高亮入口集合中遍历
        List<HighlightEntry<TbItem>> entryList = page.getHighlighted();
        for (HighlightEntry<TbItem> entry : entryList) {
            //得到一个高亮列表
            List<HighlightEntry.Highlight> highlightsList = entry.getHighlights();
            //是否还需要遍历取决于高亮列的个数
			/*for (Highlight highlightEntry : highlightsList) {
				List<String> snippletsList = highlightEntry.getSnipplets();
			}*/
            if(highlightsList.size() > 0 && highlightsList.get(0).getSnipplets().size() > 0) {
                //与page.getContent()是同一个
                TbItem item = entry.getEntity();
                //确定只有一列的情况下
                item.setTitle(highlightsList.get(0).getSnipplets().get(0));
            }

        }
        map.put("rows",page.getContent());
        return ResponseDataUtil.buildSuccess(map);
    }
}
