package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbSpecification;
import com.beemall.sellergoods.service.SpecificationService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

	@Reference
	private SpecificationService specificationService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll(){			
		return specificationService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return specificationService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param specification
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbSpecification specification){

		return specificationService.add(specification);
		
	}
	
	/**
	 * 修改
	 * @param specification
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbSpecification specification){
		return specificationService.update(specification);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbSpecification findOne(Long id){
		return specificationService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return specificationService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param specification
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbSpecification specification, int page, int size  ){
		return specificationService.findPageByExample(specification, page, size);		
	}
	
}
