package com.techshop.admin.user.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techshop.admin.user.services.CategoryService;
import com.techshop.admin.utils.FileUploadUtil;
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
	
	@PostMapping("/save")
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes attributes) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(fileName);
		
		Category saveCategory = categoryService.save(category);
		String uploadDir = "../category-images" + saveCategory.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		
		attributes.addFlashAttribute("message", "The category has been saved successfully");
		return "redirect:/categories";
	}
}
