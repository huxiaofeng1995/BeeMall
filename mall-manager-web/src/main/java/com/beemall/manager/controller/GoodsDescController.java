package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbGoodsDesc;
import com.beemall.sellergoods.service.GoodsDescService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goodsDesc")
public class GoodsDescController {

	@Reference
	private GoodsDescService goodsDescService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoodsDesc> findAll(){			
		return goodsDescService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return goodsDescService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param goodsDesc
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbGoodsDesc goodsDesc){

		return goodsDescService.add(goodsDesc);
		
	}
	
	/**
	 * 修改
	 * @param goodsDesc
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbGoodsDesc goodsDesc){
		return goodsDescService.update(goodsDesc);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbGoodsDesc findOne(Long id){
		return goodsDescService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return goodsDescService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param goodsDesc
	 * @param page
	 * @param goodsDesc
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbGoodsDesc goodsDesc, int page, int size  ){
		return goodsDescService.findPageByExample(goodsDesc, page, size);		
	}
	
}
