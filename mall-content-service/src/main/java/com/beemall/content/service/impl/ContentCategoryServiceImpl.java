package com.beemall.content.service.impl;
import java.util.List;

import com.beemall.content.service.ContentCategoryService;
import com.beemall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbContentCategoryMapper;
import com.beemall.pojo.TbContentCategory;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbContentCategory> findAll() {
		return contentCategoryMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public ResponseData findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		PageInfo<TbContentCategory> pageInfo=   new PageInfo<>( contentCategoryMapper.selectByExample(null));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	/**
	 * 增加
	 */
	@Override
	public ResponseData add(TbContentCategory contentCategory) {
		contentCategoryMapper.insert(contentCategory);	
		return ResponseDataUtil.buildSuccess();		
	}

	
	/**
	 * 修改
	 */
	@Override
	public ResponseData update(TbContentCategory contentCategory){
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		return ResponseDataUtil.buildSuccess();
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContentCategory findOne(Long id){
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public ResponseData delete(Long[] ids) {
		for(Long id:ids){
			contentCategoryMapper.deleteByPrimaryKey(id);
		}	
		return ResponseDataUtil.buildSuccess();
	}
	
	
		@Override
	public ResponseData findPageByExample(TbContentCategory contentCategory, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentCategoryExample example=new TbContentCategoryExample();
		TbContentCategoryExample.Criteria criteria = example.createCriteria();
		
		if(contentCategory!=null){			
						if(contentCategory.getName()!=null && contentCategory.getName().length()>0){
				criteria.andNameLike("%"+contentCategory.getName()+"%");
			}
	
		}
		
		PageInfo<TbContentCategory> pageInfo=   new PageInfo<>( contentCategoryMapper.selectByExample(example));	
		return ResponseDataUtil.buildSuccess(pageInfo);
	}
	
}
