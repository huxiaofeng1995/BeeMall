package com.beemall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.pojo.TbItem;
import com.beemall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;

import java.util.ArrayList;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.data.solr.core}")
    private String solrCore;

    @Override
    public ResponseData search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        map.put("rows",searchItemList(searchMap));
        List<String> catList = searchCateList(searchMap);
        map.put("categoryList", catList);
        if(catList.size() > 0){
            map.putAll(searchSpecAndBrandList(catList.get(0)));
        }
        return ResponseDataUtil.buildSuccess(map);
    }

    public List<TbItem> searchItemList(Map searchMap){
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
        return page.getContent();
    }

    public List<String> searchCateList(Map searchMap){
        String keyword = (String) searchMap.get("keywords");
        List<String> catelist = new ArrayList();
        Query query=new SimpleQuery();
        //按照关键字查询
        Criteria criteria=new Criteria("item_keywords").is(keyword);
        query.addCriteria(criteria);
        //设置分组选项
        GroupOptions options = new GroupOptions();
        options.addGroupByField("item_category");//可以有多个选项
        options.setOffset(0);//设置查询多少结果，不设置会报错
        options.setLimit(-1);

        query.setGroupOptions(options);
        //设置分组选项
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage(solrCore, query, TbItem.class);
        //设置分组选项
        GroupResult<TbItem> result = page.getGroupResult("item_category");
        //得到分组结果入口页
        Page<GroupEntry<TbItem>> groupEntries = result.getGroupEntries();
        //得到分组入口集合
        List<GroupEntry<TbItem>> content = groupEntries.getContent();
        for(GroupEntry<TbItem> entry:content){
            catelist.add(entry.getGroupValue());//将分组结果的名称封装到返回值中
        }
        return catelist;
    }

    public Map searchSpecAndBrandList(String category){
        Map<String, Object> map = new HashMap<>();
        Integer tmpId = (Integer) redisTemplate.boundHashOps("itemCat").get(category);//获取到模板id         Redis存入long类型的id取出来变成了integer类型，这里强转为long会报错
        if(tmpId != null){
            List<Map> brandList = (List<Map>) redisTemplate.boundHashOps("brandList").get(tmpId);
            List<Map> specList = (List<Map>) redisTemplate.boundHashOps("specList").get(tmpId);
            map.put("brandList", brandList);
            map.put("specList", specList);
        }
        return map;
    }
}
