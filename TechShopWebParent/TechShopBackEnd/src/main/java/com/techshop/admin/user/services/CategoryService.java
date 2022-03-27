package com.techshop.admin.user.services;

import java.util.List;

import com.techshop.common.entity.Category;

public interface CategoryService {
	List<Category> listAll();
	
	List<Category> listCategoriesUsedInForm();
	
	Category save(Category category);
	
}
