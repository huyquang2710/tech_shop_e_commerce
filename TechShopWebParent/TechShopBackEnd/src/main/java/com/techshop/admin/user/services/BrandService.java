package com.techshop.admin.user.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.techshop.admin.exception.BrandNotFoundException;
import com.techshop.common.entity.Brand;

public interface BrandService {
	List<Brand> findAll();
	Brand save(Brand brand);
	Brand findById(Integer id) throws BrandNotFoundException;
	void delete(Integer id) throws BrandNotFoundException;
	String checkUnique(Integer id, String name);
	
	Page<Brand> lítByPage(int pageNum, String sortField, String sortDir, String keyword);
	
}
