package com.beemall.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.mapper.TbGoodsDescMapper;
import com.beemall.mapper.TbGoodsMapper;
import com.beemall.mapper.TbItemCatMapper;
import com.beemall.page.service.ItemPageService;
import com.beemall.pojo.TbGoods;
import com.beemall.pojo.TbGoodsDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：bee
 * @date ：Created in 2019/7/5 10:24
 * @description：
 * @modified By：
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {
    @Value("${pagedir}")
    private String pagedir;

    @Autowired
    private Configuration configuration;

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public boolean genItemHtml(Long goodsId){
        try {
            Template template = configuration.getTemplate("item.ftl");
            Map dataModel=new HashMap<>();
            //1.加载商品表数据
            TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", goods);
            //2.加载商品扩展表数据
            TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", goodsDesc);
            //3.商品分类信息
            String cate1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String cate2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String cate3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("cate1", cate1);
            dataModel.put("cate2", cate2);
            dataModel.put("cate3", cate3);


            Writer out=new FileWriter(pagedir+goodsId+".html");
            template.process(dataModel, out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
