package com.beemall.content.service;
import java.util.List;
import com.beemall.pojo.TbContent;
import com.beemall.entity.ResponseData;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface ContentService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbContent> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResponseData findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public ResponseData add(TbContent content);
	
	
	/**
	 * 修改
	 */
	public ResponseData update(TbContent content);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbContent findOne(Long id);
	
	
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
	public ResponseData findPageByExample(TbContent content, int pageNum, int pageSize);

	/**
	 * 根据广告类型ID查询列表
	 * @param categoryId
	 * @return
	 */
	public ResponseData findByCategoryId(Long categoryId);


}
