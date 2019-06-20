package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbItemCat;
import com.beemall.sellergoods.service.ItemCatService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){			
		return itemCatService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData  findPage(int page,int size){			
		return itemCatService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param itemCat
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbItemCat itemCat){

		return itemCatService.add(itemCat);
		
	}
	
	/**
	 * 修改
	 * @param itemCat
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbItemCat itemCat){
		return itemCatService.update(itemCat);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbItemCat findOne(Long id){
		return itemCatService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return itemCatService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param itemCat
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbItemCat itemCat, int page, int size  ){
		return itemCatService.findPageByExample(itemCat, page, size);		
	}
	
}
