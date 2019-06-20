package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbItem;
import com.beemall.sellergoods.service.ItemService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/item")
public class ItemController {

	@Reference
	private ItemService itemService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItem> findAll(){			
		return itemService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return itemService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param item
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbItem item){

		return itemService.add(item);
		
	}
	
	/**
	 * 修改
	 * @param item
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbItem item){
		return itemService.update(item);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbItem findOne(Long id){
		return itemService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return itemService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param item
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbItem item, int page, int size  ){
		return itemService.findPageByExample(item, page, size);		
	}
	
}
