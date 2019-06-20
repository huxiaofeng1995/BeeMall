package com.beemall.sellergoods.service;
import java.util.List;
import com.beemall.pojo.TbSpecificationOption;
import com.beemall.entity.ResponseData;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationOptionService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecificationOption> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResponseData findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public ResponseData add(TbSpecificationOption specificationOption);
	
	
	/**
	 * 修改
	 */
	public ResponseData update(TbSpecificationOption specificationOption);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbSpecificationOption findOne(Long id);
	
	
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
	public ResponseData findPageByExample(TbSpecificationOption specificationOption, int pageNum, int pageSize);
	
}
