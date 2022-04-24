package com.techshop.admin.user.services;

import java.util.List;

import com.techshop.admin.exception.BrandNotFoundException;
import com.techshop.common.entity.Brand;

public interface BrandService {
	List<Brand> findAll();
	Brand save(Brand brand);
	Brand findById(Integer id) throws BrandNotFoundException;
	void delete(Integer id) throws BrandNotFoundException;
	
}
