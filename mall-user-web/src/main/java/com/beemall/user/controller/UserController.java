package com.beemall.user.controller;
import java.util.List;

import com.beemall.entity.ResponseData;
import com.beemall.pojo.TbUser;
import com.beemall.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Reference
	private UserService userService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbUser> findAll(){
		return userService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return userService.findPage(page, size);
	}
	
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@PostMapping("/add")
	public ResponseData add(@RequestBody TbUser user){

		return userService.add(user);
		
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody TbUser user){
		return userService.update(user);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public TbUser findOne(Long id){
		return userService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		return userService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbUser user, int page, int size  ){
		return userService.findPageByExample(user, page, size);		
	}
	
}
