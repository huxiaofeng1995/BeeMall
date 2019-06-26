package com.beemall.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.pojo.TbGoods;
import com.beemall.pojogroup.Goods;
import com.beemall.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	 * 增加
	 * @param goods
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody Goods goods){
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();//获取商家ID
		goods.getGoods().setSellerId(sellerId);
		return goodsService.add(goods);
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody Goods goods){
		//校验是否是当前商家的id
		Goods goods2 = goodsService.findOne(goods.getGoods().getId());
		//获取当前登录的商家ID
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//如果传递过来的商家ID并不是当前登录的用户的ID,则属于非法操作
		if(!goods2.getGoods().getSellerId().equals(sellerId) ||  !goods.getGoods().getSellerId().equals(sellerId) ){
			return ResponseDataUtil.buildError("操作非法");
		}
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
		//获取商家ID
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//添加查询条件
		goods.setSellerId(sellerId);

		return goodsService.findPageByExample(goods, page, size);		
	}
	
}
