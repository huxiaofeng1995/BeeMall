package com.beemall.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.beemall.mapper.TbBrandMapper;
import com.beemall.pojo.TbBrand;
import com.beemall.sellergoods.service.BrandService;
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

}
