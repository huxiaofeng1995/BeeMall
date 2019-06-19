package com.beemall.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbBrandMapper;
import com.beemall.pojo.TbBrand;
import com.beemall.pojo.TbBrandExample;
import com.beemall.sellergoods.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}

	@Override
	public ResponseData findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TbBrand> pageInfo = new PageInfo<>(brandMapper.selectByExample(null));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

	@Override
	public ResponseData add(TbBrand tbBrand) {
		brandMapper.insert(tbBrand);
		return ResponseDataUtil.buildSuccess();
	}

	@Override
	public ResponseData update(TbBrand tbBrand) {
		brandMapper.updateByPrimaryKey(tbBrand);
		return ResponseDataUtil.buildSuccess();
	}

	@Override
	public ResponseData delete(Long[] ids) {
		for(Long id : ids){
			brandMapper.deleteByPrimaryKey(id);
		}
		return ResponseDataUtil.buildSuccess();
	}

	@Override
	public ResponseData findPageByExample(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TbBrandExample example = new TbBrandExample();
		TbBrandExample.Criteria criteria =  example.createCriteria();
		TbBrandExample.Criteria criteria2 =  example.createCriteria();
		if(brand!=null){
			if(brand.getName()!=null && brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");
				example.or(criteria);
			}
			if(brand.getFirstChar()!=null && brand.getFirstChar().length()>0){//or条件查询
				criteria2.andFirstCharEqualTo(brand.getFirstChar());
				example.or(criteria2);
			}
		}

		PageInfo<TbBrand> pageInfo = new PageInfo<>(brandMapper.selectByExample(example));
		return ResponseDataUtil.buildSuccess(pageInfo);
	}

}
