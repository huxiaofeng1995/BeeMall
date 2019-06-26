package com.beemall.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.beemall.mapper.TbSpecificationOptionMapper;
import com.beemall.pojo.TbSpecificationOption;
import com.beemall.pojo.TbSpecificationOptionExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageHelper;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbTypeTemplateMapper;
import com.beemall.pojo.TbTypeTemplate;
import com.beemall.pojo.TbTypeTemplateExample;
import com.beemall.pojo.TbTypeTemplateExample.Criteria;
import com.beemall.sellergoods.service.TypeTemplateService;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;

	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;


	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public ResponseData findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		PageInfo<TbTypeTemplate> pageInfo=   new PageInfo<>( typeTemplateMapper.selectByExample(null));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	/**
	 * 增加
	 */
	@Override
	public ResponseData add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);	
		return ResponseDataUtil.buildSuccess();		
	}

	
	/**
	 * 修改
	 */
	@Override
	public ResponseData update(TbTypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
		return ResponseDataUtil.buildSuccess();
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public ResponseData delete(Long[] ids) {
		for(Long id:ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}	
		return ResponseDataUtil.buildSuccess();
	}
	
	
		@Override
	public ResponseData findPageByExample(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbTypeTemplateExample example=new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){			
						if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
		
		PageInfo<TbTypeTemplate> pageInfo=   new PageInfo<>( typeTemplateMapper.selectByExample(example));	
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	@Override
	public ResponseData selectOptionList() {
		return ResponseDataUtil.buildSuccess(typeTemplateMapper.selectOptionList());
	}

	@Override
	public ResponseData findSpecList(Long id) {
		TbTypeTemplate template = typeTemplateMapper.selectByPrimaryKey(id);
		List<Map> list = JSON.parseArray(template.getSpecIds(), Map.class);//例：[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]

		for(Map map : list){
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(new Long( (Integer)map.get("id") ));
			List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
			map.put("options", options);
		}
		return ResponseDataUtil.buildSuccess(list);
	}

}
