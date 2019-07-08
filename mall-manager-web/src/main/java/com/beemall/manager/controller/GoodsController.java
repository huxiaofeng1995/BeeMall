package com.beemall.manager.controller;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beemall.entity.MQMessage;
import com.beemall.entity.ResponseData;
import com.beemall.page.service.ItemPageService;
import com.beemall.pojo.TbItem;
import com.beemall.pojogroup.Goods;
import com.beemall.search.service.ItemSearchService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbGoods;
import com.beemall.sellergoods.service.GoodsService;

import javax.jms.Destination;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;

	@Reference(timeout=40000)
	private ItemPageService itemPageService;

	@Value("${spring.activemq.queue}")
	private String queue;

	@Autowired
	private JmsMessagingTemplate jmsTemplate;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return goodsService.findPage(page, size);
	}
	

	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody Goods goods){
		return goodsService.update(goods);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		MQMessage mqMessage = new MQMessage();//自定义消息格式
		mqMessage.setMethod("deleteItems");//消费者端可根据这个字段类型来进行不同的操作
		mqMessage.setData(ids);
		Destination destination = new ActiveMQQueue(queue);
		jmsTemplate.convertAndSend(destination, JSON.toJSONString(mqMessage));
		return goodsService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param goods
	 * @param page
	 * @param size
	 * @returnids
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbGoods goods, int page, int size  ){
		return goodsService.findPageByExample(goods, page, size);		
	}


	/**
	 * 更新状态
	 * @param ids
	 * @param status
	 */
	@PostMapping("/updateStatus")
	public ResponseData updateStatus(Long[] ids, String status){
		if(status.equals("1")){//审核通过
			//1.更新solr索引库
			List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
			//调用搜索接口实现数据批量导入
			MQMessage mqMessage = new MQMessage();//自定义消息格式
			mqMessage.setMethod("importItems");//消费者端可根据这个字段类型来进行不同的操作
			mqMessage.setData(itemList);
			if(itemList.size()>0){
				Destination destination = new ActiveMQQueue(queue);
				jmsTemplate.convertAndSend(destination, JSON.toJSONString(mqMessage));
			}else{
				System.out.println("没有明细数据");
			}
			//2.生成静态商品详情页
//			for(Long id: ids){
//				itemPageService.genItemHtml(id);
//			}
		}

		return 	goodsService.updateStatus(ids, status);
	}

}
