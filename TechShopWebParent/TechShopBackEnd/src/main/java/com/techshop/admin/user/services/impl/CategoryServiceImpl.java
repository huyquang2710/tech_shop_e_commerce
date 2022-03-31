package com.techshop.admin.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techshop.admin.exception.CategoryNotFoundException;
import com.techshop.admin.user.repositories.CategoryRepository;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.common.entity.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> listAll() {
		 List<Category> rootCategories = categoryRepository.findRootCategories();
		 
		 return listHierarchicalCategories(rootCategories);
	}
	
	private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
		List<Category> hierarchicalCategories = new ArrayList<>();
		
		for(Category rootCategory : rootCategories ) {
			hierarchicalCategories.add(Category.copyAll(rootCategory));
			
			Set<Category> children = rootCategory.getChildren();
			
			for(Category subCategory : children) {
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyAll(subCategory, name));
				
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		}
		return hierarchicalCategories;
	}
	
	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel) {
		Set<Category> children = parent.getChildren();
		int newSubLevel = subLevel + 1;
		
		for(Category subCategory : children) {
			String name = "";
			for(int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			hierarchicalCategories.add(Category.copyAll(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
	}

	// show category theo cap bac
	// code ao? qua.
	@Override
	public List<Category> listCategoriesUsedInForm() {

		List<Category> categoriesUserdInForm = new ArrayList<>();

		Iterable<Category> categoriesInDB = categoryRepository.findAll();

		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUserdInForm.add(Category.copyIdAndName(category));

				Set<Category> children = category.getChildren();

				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUserdInForm.add(Category.copyIdAndName(subCategory.getId(), name));

					listSubCategoriesUsedInForm(categoriesUserdInForm, subCategory, 1);
				}
			}
		}

		return categoriesUserdInForm;
	}
	
	private void listSubCategoriesUsedInForm(List<Category> categoriesUserdInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			String name = "";
			for(int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			categoriesUserdInForm.add(Category.copyIdAndName(subCategory.getId(), name));
			
			listSubCategoriesUsedInForm(categoriesUserdInForm, subCategory, newSubLevel);
		}
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category findById(Integer id) throws CategoryNotFoundException{
		Category category = new Category();
		
		try {
			return categoryRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("Could not find any category with Category: " + category.getName());
		}
		
	}

	//check unique alias
	@Override
	public String checkUnique(Integer id, String name, String alias) {
		boolean isCreatingNew = (id == null || id == 0);
		
		Category categoryByName = categoryRepository.findByName(name);
		
		if(isCreatingNew) {
			if(categoryByName != null) {
				return "Duplicate Name";
			} else {
				Category categoryByAlias = categoryRepository.findByName(alias);
				if(categoryByAlias != null) {
					return "Duplicate Alias";
				}
			}
		} else {
			
		}
		return "OK";
	}
	
}
