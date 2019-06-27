package com.beemall.manager.controller;
import java.util.List;

import com.beemall.content.service.ContentCategoryService;
import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbContentCategory;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {

	@Reference
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbContentCategory> findAll(){			
		return contentCategoryService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return contentCategoryService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param contentCategory
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbContentCategory contentCategory){

		return contentCategoryService.add(contentCategory);
		
	}
	
	/**
	 * 修改
	 * @param contentCategory
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbContentCategory contentCategory){
		return contentCategoryService.update(contentCategory);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbContentCategory findOne(Long id){
		return contentCategoryService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return contentCategoryService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param contentCategory
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbContentCategory contentCategory, int page, int size  ){
		return contentCategoryService.findPageByExample(contentCategory, page, size);		
	}
	
}
