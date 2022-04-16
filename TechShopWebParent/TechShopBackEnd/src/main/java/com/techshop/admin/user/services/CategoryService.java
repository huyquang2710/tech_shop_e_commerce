package com.techshop.admin.user.services;

import java.util.List;

import com.techshop.admin.exception.CategoryNotFoundException;
import com.techshop.common.entity.Category;

public interface CategoryService {
	List<Category> listAll(String sortDir);
	
	List<Category> listCategoriesUsedInForm();
	
	Category save(Category category);
	
	Category findById(Integer id) throws CategoryNotFoundException;
	
	String checkUnique(Integer id, String name, String alias);
	
	void udpateEnaledStatus(Integer id, boolean enabled);
	
	void delete(Integer id) throws CategoryNotFoundException;
}
