package com.techshop.admin.user.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techshop.admin.user.repositories.CategoryRepository;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.common.entity.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> listAll() {
		return (List<Category>) categoryRepository.findAll();
	}

}
