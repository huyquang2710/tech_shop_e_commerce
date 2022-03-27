package com.techshop.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techshop.admin.user.services.CategoryService;
import com.techshop.common.entity.Category;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public String listAll(Model model) {
		List<Category> listCategories = categoryService.listAll();
		
		model.addAttribute("category", listCategories);
		return "categories/categories";
	}
	
	@GetMapping("/new")
	public String categoryForm(Model model) {
		List<Category> categoriesList = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("categoriesList", categoriesList);
		model.addAttribute("category", new Category());
		model.addAttribute("pageTitle", "New Categories");
		return "categories/categories_form";
	}
}
