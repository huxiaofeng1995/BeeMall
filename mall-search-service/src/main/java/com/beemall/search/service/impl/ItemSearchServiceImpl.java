package com.beemall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbSpecificationMapper;
import com.beemall.pojo.TbItem;
import com.beemall.pojo.TbSpecification;
import com.beemall.pojo.TbSpecificationExample;
import com.beemall.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private TbSpecificationMapper specificationMapper;

    @Override
    public ResponseData search(Map searchMap) {
        Map<String, Object> map = new HashMap<>();
        //关键字空格处理 不去空格可能搜不出结果
        String keywords = (String) searchMap.get("keywords");
        searchMap.put("keywords", keywords.replace(" ", ""));

        map.putAll(searchItemList(searchMap));
        List<String> catList = searchCateList(searchMap);
        map.put("categoryList", catList);
        //查询品牌和规格列表
        String categoryName=(String)searchMap.get("category");
        if(!"".equals(categoryName)){//如果有分类名称
            map.putAll(searchSpecAndBrandList(categoryName));
        }else{//如果没有分类名称，按照第一个查询
            if(catList.size()>0){
                map.putAll(searchSpecAndBrandList(catList.get(0)));
            }
        }

        return ResponseDataUtil.buildSuccess(map);
    }

    public Map searchItemList(Map searchMap){
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


        //1.1关键字查询
        Criteria criteria = new Criteria("item_keywords").is(keyword);
        query.addCriteria(criteria);
        //1.2按分类筛选
        if(!"".equals(searchMap.get("category"))){
            Criteria filterCriteria=new Criteria("item_category").is(searchMap.get("category"));
            FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }
        //1.3按品牌筛选
        if(!"".equals(searchMap.get("brand"))){
            Criteria filterCriteria=new Criteria("item_brand").is(searchMap.get("brand"));
            FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
            query.addFilterQuery(filterQuery);
        }
        //1.4过滤规格
        //这种规则目前存在问题，原因是高版本的solr在使用‘动态域’时，会将含中文的key值的中文部分转化为 _ .
        //解决方法个人想到的有两种：
        //      ①降低solr版本；
        //      ②solr表中的字段修改，将规格名称转成英文或者用规格id代替规格名称 例：{"机身内存":"16G","网络":"移动4G"}  --> {id1: "16G",id2:"移动4G"}
        if(searchMap.get("spec")!=null){
            Map<String,String> specMap= (Map) searchMap.get("spec");
            for(String key:specMap.keySet() ){
                //采用第二种方式解决bug
                TbSpecificationExample example1 = new TbSpecificationExample();
                TbSpecificationExample.Criteria c = example1.createCriteria();
                c.andSpecNameEqualTo(key);
                TbSpecification spec = specificationMapper.selectByExample(example1).get(0);

                Criteria filterCriteria = new Criteria("item_spec_"+ spec.getId()).is( specMap.get(key) );
                FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
        //1.5过滤价格
        if(!"".equals(searchMap.get("price"))){
            String[] price = ((String) searchMap.get("price")).split("-");
            if(!price[0].equals("0")){//如果区间起点不等于0
                Criteria filterCriteria=new Criteria("item_price").greaterThanEqual(price[0]);
                FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if(!price[1].equals("*")){//如果区间终点不等于*
                Criteria filterCriteria=new  Criteria("item_price").lessThanEqual(price[1]);
                FilterQuery filterQuery=new SimpleFilterQuery(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }
        //1.6 分页查询
        Integer pageNo= (Integer) searchMap.get("pageNo");//提取页码
        if(pageNo==null){
            pageNo = 1;//默认第一页
        }
        Integer pageSize=(Integer) searchMap.get("pageSize");//每页记录数
        if(pageSize==null){
            pageSize=20;//默认20
        }
        Long offset = Long.valueOf((pageNo-1)*pageSize);
        query.setOffset(offset);//从第几条记录查询
        query.setRows(pageSize);

        //1.7排序
        String sort = (String) searchMap.get("sort");
        String field = (String) searchMap.get("sortField");
        if(sort != null && !"".equals(sort)){
            if(sort.equals("ASC")){
                Sort s = new Sort(Sort.Direction.ASC, "item_" + field);
                query.addSort(s);
            }
            if(sort.equals("DESC")){
                Sort s = new Sort(Sort.Direction.DESC, "item_" + field);
                query.addSort(s);
            }
        }

        //高亮显示处理
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
        map.put("rows", page.getContent());
        map.put("totalPages", page.getTotalPages());//返回总页数
        map.put("total", page.getTotalElements());//返回总记录数
        map.put("curPage", pageNo);
        return map;
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

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(solrCore,list);
        solrTemplate.commit(solrCore);
    }

    @Override
    public void deleteByGoodsIds(List goodsIdList) {
        System.out.println("删除商品ID"+goodsIdList);
        Query query=new SimpleQuery();
        Criteria criteria=new Criteria("item_goodsid").in(goodsIdList);
        query.addCriteria(criteria);
        solrTemplate.delete(solrCore, query);
        solrTemplate.commit(solrCore);
    }

}
