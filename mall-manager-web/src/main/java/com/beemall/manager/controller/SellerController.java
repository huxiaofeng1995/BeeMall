package com.beemall.manager.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbSeller;
import com.beemall.sellergoods.service.SellerService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return sellerService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbSeller seller){

		return sellerService.add(seller);
		
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbSeller seller){
		return sellerService.update(seller);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(String [] ids){
		return sellerService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param seller
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbSeller seller, int page, int size  ){
		return sellerService.findPageByExample(seller, page, size);		
	}
	
}
