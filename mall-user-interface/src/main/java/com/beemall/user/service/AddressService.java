package com.beemall.user.service;
import java.util.List;
import com.beemall.pojo.TbAddress;
import com.beemall.entity.ResponseData;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface AddressService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbAddress> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResponseData findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public ResponseData add(TbAddress address);
	
	
	/**
	 * 修改
	 */
	public ResponseData update(TbAddress address);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbAddress findOne(Long id);
	
	
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
	public ResponseData findPageByExample(TbAddress address, int pageNum, int pageSize);

	/**
	 * 根据用户查询地址
	 * @param userId
	 * @return
	 */
	public List<TbAddress> findListByUserId(String userId );

}
