package com.beemall.order.service;
import java.util.List;
import com.beemall.pojo.TbOrder;
import com.beemall.entity.ResponseData;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface OrderService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbOrder> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResponseData findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public ResponseData add(TbOrder order);
	
	
	/**
	 * 修改
	 */
	public ResponseData update(TbOrder order);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbOrder findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public ResponseData delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public ResponseData findPageByExample(TbOrder order, int pageNum, int pageSize);
	
}
