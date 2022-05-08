package com.techshop.admin.user.services;

import java.util.List;

import com.techshop.common.entity.Product;

public interface ProductService {
	List<Product> findAll();
	
	Product save(Product product);
	
	String checkUnique(Integer id, String name);
	
	void updateProductEnabledStatus(Integer id, boolean enabled);
}
