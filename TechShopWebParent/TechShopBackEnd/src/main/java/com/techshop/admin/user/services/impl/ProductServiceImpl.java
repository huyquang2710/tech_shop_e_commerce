package com.techshop.admin.user.services.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techshop.admin.exception.ProductNotFoundException2;
import com.techshop.admin.user.repositories.ProductRepository;
import com.techshop.admin.user.services.ProductService;
import com.techshop.common.entity.Product;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		
		return (List<Product>) productRepository.findAll();
	}

	@Override
	public Product save(Product product) {
		if(product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "_");
			product.setAlias(defaultAlias);
		} else {
			product.setAlias(product.getAlias().replaceAll(" ", "_"));
		}
		product.setUpdatedTime(new Date());
		return productRepository.save(product);
	}

	@Override
	public String checkUnique(Integer id, String name) {
		boolean isCreatignNew = (id == null || id == 0);
		Product productByName = productRepository.findByName(name);
		if(isCreatignNew) {
			if(productByName != null) return "Duplicate";
		} else {
			if(productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		return "OK";
	}

	@Override
	public void updateProductEnabledStatus(Integer id, boolean enabled) {
		productRepository.updateEnabledStatus(id, enabled);
	}

	@Override
	public void delete(Integer id) throws ProductNotFoundException2{
		Long countById = productRepository.countById(id);
		
		if(countById == null || countById == 0) {
			throw new ProductNotFoundException2("Count not find any product with ID " + id);
		}
		productRepository.deleteById(id);
	}

}
