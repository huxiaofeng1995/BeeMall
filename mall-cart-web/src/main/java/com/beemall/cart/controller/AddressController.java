package com.beemall.cart.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.user.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbAddress;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/address")
public class AddressController {

	@Reference
	private AddressService addressService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbAddress> findAll(){			
		return addressService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData  findPage(int page,int size){			
		return addressService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param address
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbAddress address){
		String userId = SecurityContextHolder.getContext().getAuthentication().getName();
		address.setUserId(userId);
		return addressService.add(address);
		
	}
	
	/**
	 * 修改
	 * @param address
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbAddress address){
		return addressService.update(address);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbAddress findOne(Long id){
		return addressService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return addressService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param address
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbAddress address, int page, int size  ){
		return addressService.findPageByExample(address, page, size);		
	}

	@GetMapping("/findListByLoginUser")
	public ResponseData findListByLoginUser(){
		try {
			String userId = SecurityContextHolder.getContext().getAuthentication().getName();
			return ResponseDataUtil.buildSuccess(addressService.findListByUserId(userId));
		}catch (Exception e){
			e.printStackTrace();
			return ResponseDataUtil.buildError();
		}
	}

}
