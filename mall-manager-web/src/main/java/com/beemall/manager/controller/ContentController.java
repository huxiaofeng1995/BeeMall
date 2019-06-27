package com.beemall.manager.controller;
import java.util.List;

import com.beemall.content.service.ContentService;
import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbContent;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference
	private ContentService contentService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbContent> findAll(){			
		return contentService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return contentService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param content
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbContent content){

		return contentService.add(content);
		
	}
	
	/**
	 * 修改
	 * @param content
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbContent content){
		return contentService.update(content);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbContent findOne(Long id){
		return contentService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return contentService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param content
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbContent content, int page, int size  ){
		return contentService.findPageByExample(content, page, size);		
	}
	
}
