package com.beemall.sellergoods.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbItemCatMapper;
import com.beemall.pojo.TbItemCat;
import com.beemall.pojo.TbItemCatExample;
import com.beemall.pojo.TbItemCatExample.Criteria;
import com.beemall.sellergoods.service.ItemCatService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return itemCatMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public ResponseData findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		PageInfo<TbItemCat> pageInfo=   new PageInfo<>( itemCatMapper.selectByExample(null));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	/**
	 * 增加
	 */
	@Override
	public ResponseData add(TbItemCat itemCat) {
		itemCatMapper.insert(itemCat);	
		return ResponseDataUtil.buildSuccess();		
	}

	
	/**
	 * 修改
	 */
	@Override
	public ResponseData update(TbItemCat itemCat){
		itemCatMapper.updateByPrimaryKey(itemCat);
		return ResponseDataUtil.buildSuccess();
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return itemCatMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public ResponseData delete(Long[] ids) {
		boolean delFlag = true;//所有要删的分类中，若有任意其中一个分类含有子分类，则拒绝这次删除请求
		for(Long id:ids){
			TbItemCatExample example=new TbItemCatExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(id);
			if(itemCatMapper.selectByExample(example).size() > 0){
				delFlag = false;
				return ResponseDataUtil.buildError("选中的分类中部分含有子分类，无法执行删除操作");
			}else {
				continue;
			}
		}
		if(delFlag){
			for(Long id:ids){
				itemCatMapper.deleteByPrimaryKey(id);
			}
		}
		return ResponseDataUtil.buildSuccess();
	}
	
	
		@Override
	public ResponseData findPageByExample(TbItemCat itemCat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(itemCat!=null){			
						if(itemCat.getName()!=null && itemCat.getName().length()>0){
				criteria.andNameLike("%"+itemCat.getName()+"%");
			}
	
		}
		
		PageInfo<TbItemCat> pageInfo=   new PageInfo<>( itemCatMapper.selectByExample(example));	
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	@Override
	public ResponseData findByParentId(Long parentId) {
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		//每次执行查询的时候，一次性读取缓存进行存储 (因为每次增删改都要执行此方法)
		List<TbItemCat> list = findAll();
		for(TbItemCat cat : list) {
			redisTemplate.boundHashOps("itemCat").put(cat.getName(), cat.getTypeId());//将类型名称与模板id存入缓存中
		}
		return ResponseDataUtil.buildSuccess(itemCatMapper.selectByExample(example));
	}

}
