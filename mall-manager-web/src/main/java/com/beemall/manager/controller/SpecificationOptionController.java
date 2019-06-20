package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbSpecificationOption;
import com.beemall.sellergoods.service.SpecificationOptionService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/specificationOption")
public class SpecificationOptionController {

	@Reference
	private SpecificationOptionService specificationOptionService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecificationOption> findAll(){			
		return specificationOptionService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData  findPage(int page,int size){			
		return specificationOptionService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param specificationOption
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbSpecificationOption specificationOption){

		return specificationOptionService.add(specificationOption);
		
	}
	
	/**
	 * 修改
	 * @param specificationOption
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbSpecificationOption specificationOption){
		return specificationOptionService.update(specificationOption);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbSpecificationOption findOne(Long id){
		return specificationOptionService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return specificationOptionService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param specificationOption
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbSpecificationOption specificationOption, int page, int size  ){
		return specificationOptionService.findPageByExample(specificationOption, page, size);		
	}
	
}
