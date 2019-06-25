package com.beemall.manager.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beemall.entity.ResponseData;
import com.beemall.pojo.TbTypeTemplate;
import com.beemall.sellergoods.service.TypeTemplateService;
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

	@Reference
	private TypeTemplateService typeTemplateService;
	
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
	public Map<String, Object> findOne(Long id){
		//自定义返回结果
		Map<String, Object> map = new HashMap<>();
		TbItemCat cat = itemCatService.findOne(id);
		map.put("id", cat.getId());
		map.put("name", cat.getName());
		map.put("parentId", cat.getParentId());
		Map<String, Object> typeMap = new HashMap<>();
		TbTypeTemplate template = typeTemplateService.findOne(cat.getTypeId());
		typeMap.put("id", template.getId());
		typeMap.put("text", template.getName());
		map.put("typeId",typeMap);
		return map;
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

	/**
	 * 根据上级ID查询列表
	 * @param parentId
	 * @return
	 */
	@GetMapping("/findByParentId")
	public ResponseData findByParentId(Long parentId){
		return itemCatService.findByParentId(parentId);
	}

}
