package com.beemall.sellergoods.service;
import java.util.List;
import com.beemall.pojo.TbSpecification;
import com.beemall.entity.ResponseData;
import com.beemall.pojogroup.Specification;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResponseData findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public ResponseData add(Specification specification);
	
	
	/**
	 * 修改
	 */
	public ResponseData update(Specification specification);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public ResponseData findOne(Long id);
	
	
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
	public ResponseData findPageByExample(TbSpecification specification, int pageNum, int pageSize);

	/**
	 * 规格下拉框数据
	 */
	public ResponseData selectOptionList();
	
}
