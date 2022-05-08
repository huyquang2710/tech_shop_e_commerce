package com.techshop.admin.user.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techshop.admin.user.repositories.ProductRepository;
import com.techshop.admin.user.services.ProductService;
import com.techshop.common.entity.Product;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		
		return (List<Product>) productRepository.findAll();
	}

}
