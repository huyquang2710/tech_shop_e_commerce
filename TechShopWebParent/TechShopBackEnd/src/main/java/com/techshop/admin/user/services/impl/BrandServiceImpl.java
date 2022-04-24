package com.techshop.admin.user.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techshop.admin.user.repositories.BrandRepository;
import com.techshop.admin.user.services.BrandService;
import com.techshop.common.entity.Brand;

@Service
public class BrandServiceImpl implements BrandService{
	
	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public List<Brand> findAll() {
		return (List<Brand>) brandRepository.findAll();
	}
	
}
