package com.beemall.user.service;
import java.util.List;
import com.beemall.pojo.TbUser;
import com.beemall.entity.ResponseData;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public ResponseData findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public ResponseData add(TbUser user);
	
	
	/**
	 * 修改
	 */
	public ResponseData update(TbUser user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbUser findOne(Long id);
	
	
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
	public ResponseData findPageByExample(TbUser user, int pageNum, int pageSize);

	/**
	 * 生成短信验证码
	 * @return
	 */
	public void createSmsCode(String phone);

	/**
	 * 检验验证码
	 * @param phone
	 * @param code
	 * @return
	 */
	public boolean checkSmsCode(String phone, String code);
}
