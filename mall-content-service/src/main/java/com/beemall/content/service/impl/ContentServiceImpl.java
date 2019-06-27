package com.beemall.content.service.impl;
import java.util.List;

import com.beemall.content.service.ContentService;
import com.beemall.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbContentMapper;
import com.beemall.pojo.TbContent;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public ResponseData findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		PageInfo<TbContent> pageInfo=   new PageInfo<>( contentMapper.selectByExample(null));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	/**
	 * 增加
	 */
	@Override
	public ResponseData add(TbContent content) {
		contentMapper.insert(content);	
		return ResponseDataUtil.buildSuccess();		
	}

	
	/**
	 * 修改
	 */
	@Override
	public ResponseData update(TbContent content){
		contentMapper.updateByPrimaryKey(content);
		return ResponseDataUtil.buildSuccess();
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbContent findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public ResponseData delete(Long[] ids) {
		for(Long id:ids){
			contentMapper.deleteByPrimaryKey(id);
		}	
		return ResponseDataUtil.buildSuccess();
	}
	
	
		@Override
	public ResponseData findPageByExample(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example=new TbContentExample();
		TbContentExample.Criteria criteria = example.createCriteria();
		
		if(content!=null){			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}
		
		PageInfo<TbContent> pageInfo=   new PageInfo<>( contentMapper.selectByExample(example));	
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	@Override
	public ResponseData findByCategoryId(Long categoryId) {
		//根据广告分类ID查询广告列表
		TbContentExample contentExample=new TbContentExample();
		TbContentExample.Criteria criteria2 = contentExample.createCriteria();
		criteria2.andCategoryIdEqualTo(categoryId);
		criteria2.andStatusEqualTo("1");//开启状态
		contentExample.setOrderByClause("sort_order");//排序
		return  ResponseDataUtil.buildSuccess(contentMapper.selectByExample(contentExample));

	}

}
