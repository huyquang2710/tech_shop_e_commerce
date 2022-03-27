package com.techshop.admin.user.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techshop.admin.user.repositories.CategoryRepository;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.common.entity.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> listAll() {
		return (List<Category>) categoryRepository.findAll();
	}

	// show category theo cap bac
	// code ao? qua.
	@Override
	public List<Category> listCategoriesUsedInForm() {

		List<Category> categoriesUserdInForm = new ArrayList<>();

		Iterable<Category> categoriesInDB = categoryRepository.findAll();

		for (Category category : categoriesInDB) {
			if (category.getParent() == null) {
				categoriesUserdInForm.add(new Category(category.getName()));

				Set<Category> children = category.getChildren();

				for (Category subCategory : children) {
					String name = "--" + subCategory.getName();
					categoriesUserdInForm.add(new Category(name));

					listChildren(categoriesUserdInForm, subCategory, 1);
				}
			}
		}

		return categoriesUserdInForm;
	}
	
	private void listChildren(List<Category> categoriesUserdInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			String name = "";
			for(int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			categoriesUserdInForm.add(new Category(name));
			
			listChildren(categoriesUserdInForm, subCategory, newSubLevel);
		}
	}

}
