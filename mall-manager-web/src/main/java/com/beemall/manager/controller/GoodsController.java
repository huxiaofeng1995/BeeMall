package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbGoods;
import com.beemall.sellergoods.service.GoodsService;

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
	 * 修改
	 * @param goods
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbGoods goods){
		return goodsService.update(goods);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbGoods findOne(Long id){
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
		return goodsService.findPageByExample(goods, page, size);		
	}
	
}
