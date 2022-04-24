package com.techshop.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techshop.admin.user.services.BrandService;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.common.entity.Brand;
import com.techshop.common.entity.Category;

@Controller
@RequestMapping("/brands")
public class BrandController {
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public String findAll(Model model) {
		List<Brand> brandList = brandService.findAll();
		
		model.addAttribute("brand", brandList);
		return "brands/brands";
	}
	
	@GetMapping("/new")
	public String newForm(Model model) {
		List<Category> category = categoryService.listCategoriesUsedInForm();
		Brand brand = new Brand();
		
		model.addAttribute("category", category);
		model.addAttribute("brand", brand);
		model.addAttribute("pageTitle", "New Brands");
		
		return "brands/brands_form";
	}
}
