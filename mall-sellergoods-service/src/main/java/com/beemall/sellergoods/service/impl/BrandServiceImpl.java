package com.beemall.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.entity.ResponseData;
import com.beemall.entity.ResponseDataUtil;
import com.beemall.mapper.TbBrandMapper;
import com.beemall.pojo.TbBrand;
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

}
