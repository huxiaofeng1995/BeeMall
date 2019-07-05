package com.beemall.manager.controller;
import java.util.Arrays;
import java.util.List;

import com.beemall.entity.ResponseData;
import com.beemall.page.service.ItemPageService;
import com.beemall.pojo.TbItem;
import com.beemall.pojogroup.Goods;
import com.beemall.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.beemall.pojo.TbGoods;
import com.beemall.sellergoods.service.GoodsService;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@GetMapping("/findPage")
	public ResponseData findPage(int page, int size){
		return goodsService.findPage(page, size);
	}
	

	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@PostMapping("/update")
	public ResponseData update(@RequestBody Goods goods){
		return goodsService.update(goods);
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@GetMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseData delete(Long [] ids){
		itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
		return goodsService.delete(ids);
	}
	
		/**
	 * 查询+分页
	 * @param goods
	 * @param page
	 * @param size
	 * @returnids
	 */
	@PostMapping("/search")
	public ResponseData search(@RequestBody TbGoods goods, int page, int size  ){
		return goodsService.findPageByExample(goods, page, size);		
	}

	@Reference
	private ItemSearchService itemSearchService;


	/**
	 * 更新状态
	 * @param ids
	 * @param status
	 */
	@PostMapping("/updateStatus")
	public ResponseData updateStatus(Long[] ids, String status){
		if(status.equals("1")){//审核通过
			List<TbItem> itemList = goodsService.findItemListByGoodsIdandStatus(ids, status);
			//调用搜索接口实现数据批量导入
			if(itemList.size()>0){
				itemSearchService.importList(itemList);
			}else{
				System.out.println("没有明细数据");
			}
		}

		return 	goodsService.updateStatus(ids, status);
	}

	@Reference(timeout=40000)
	private ItemPageService itemPageService;
	/**
	 * 生成静态页（测试）
	 * @param goodsId
	 */
	@GetMapping("/genHtml")
	public String genHtml(Long goodsId){
		itemPageService.genItemHtml(goodsId);
		return "success";
	}

}
