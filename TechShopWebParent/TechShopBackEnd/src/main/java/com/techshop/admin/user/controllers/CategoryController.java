package com.techshop.admin.user.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.techshop.admin.exception.CategoryNotFoundException;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.admin.utils.FileUploadUtil;
import com.techshop.common.entity.Category;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public String listAll(Model model, @Param("sortDir") String sortDir) {

		List<Category> listCategories = categoryService.listAll(sortDir);

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
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes attributes) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);

			Category saveCategory = categoryService.save(category);
			String uploadDir = "category-images/" + saveCategory.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		} else {
			categoryService.save(category);
		}

		attributes.addFlashAttribute("message", "The category has been saved successfully");
		return "redirect:/categories";
	}

	// update
	@GetMapping("/edit/{id}")
	public String editCategory(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
		try {
			Category category = categoryService.findById(id);
			List<Category> listCategories = categoryService.listCategoriesUsedInForm();

			model.addAttribute("category", category);
			model.addAttribute("categoriesList", listCategories);
			model.addAttribute("pageTitle", "Edit Category (Name: " + category.getName() + ")");
   
			return "categories/categories_form";
		} catch (CategoryNotFoundException e) {
			attributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/categories";
		}
	}
	
	//update enabled stasus
	@GetMapping("/{id}/enabled/{status}")
	public String updateEnabledStatus(@PathVariable("id") Integer id, 
			@PathVariable("status") boolean enabled, RedirectAttributes attributes) {
		categoryService.udpateEnaledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The category has been updated " + status;
		attributes.addFlashAttribute("message", message);
		
		return "redirect:/categories";
	}
	
	//delete
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
		try {
			categoryService.delete(id);
			String dirCategory = "category-images/" + id;
			FileUploadUtil.deleteDir(dirCategory);
			attributes.addFlashAttribute("message", "The category has been deleted");
			
		} catch (CategoryNotFoundException e) {
			attributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/categories";
	}
}
