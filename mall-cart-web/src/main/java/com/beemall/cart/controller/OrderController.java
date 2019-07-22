package com.beemall.cart.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import com.beemall.order.service.OrderService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbOrder;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference
	private OrderService orderService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){			
		return orderService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return orderService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param order
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbOrder order){
		//获取当前登录人账号
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setUserId(username);
		order.setSourceType("2");//订单来源  PC

		return orderService.add(order);
		
	}
	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbOrder order){
		return orderService.update(order);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbOrder findOne(Long id){
		return orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return orderService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param order
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbOrder order, int page, int size  ){
		return orderService.findPageByExample(order, page, size);		
	}
	
}
