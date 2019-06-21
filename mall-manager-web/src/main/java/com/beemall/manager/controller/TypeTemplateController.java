package com.beemall.manager.controller;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbTypeTemplate;
import com.beemall.sellergoods.service.TypeTemplateService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll(){			
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return typeTemplateService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param typeTemplateJson
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody String typeTemplateJson){
		JSONObject object = JSON.parseObject(typeTemplateJson);
		TbTypeTemplate typeTemplate = new TbTypeTemplate();
		typeTemplate.setName(object.getString("name"));
		typeTemplate.setBrandIds(JSON.toJSONString(object.get("brandIds")));
		typeTemplate.setSpecIds(JSON.toJSONString(object.get("specIds")));
		typeTemplate.setCustomAttributeItems(JSON.toJSONString(object.get("customAttributeItems")));
		return typeTemplateService.add(typeTemplate);
		
	}
	
	/**
	 * 修改
	 * @param typeTemplateJson
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody String typeTemplateJson){
		JSONObject object = JSON.parseObject(typeTemplateJson);
		TbTypeTemplate typeTemplate = new TbTypeTemplate();
		typeTemplate.setId(object.getLong("id"));
		typeTemplate.setName(object.getString("name"));
		typeTemplate.setBrandIds(JSON.toJSONString(object.get("brandIds")));
		typeTemplate.setSpecIds(JSON.toJSONString(object.get("specIds")));
		typeTemplate.setCustomAttributeItems(JSON.toJSONString(object.get("customAttributeItems")));
		return typeTemplateService.update(typeTemplate);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbTypeTemplate findOne(Long id){
		return typeTemplateService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return typeTemplateService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param typeTemplate
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbTypeTemplate typeTemplate, int page, int size  ){
		return typeTemplateService.findPageByExample(typeTemplate, page, size);		
	}
	
}
