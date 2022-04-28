package com.techshop.admin.user.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.techshop.admin.exception.BrandNotFoundException;
import com.techshop.admin.user.services.BrandService;
import com.techshop.admin.user.services.CategoryService;
import com.techshop.admin.user.services.impl.BrandServiceImpl;
import com.techshop.admin.utils.FileUploadUtil;
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
//	@GetMapping
//	public String findAll(Model model) {
//		
//		return getByPage(1, "name", "asc", null, model);
//	}
	
	public String getByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword, Model model) {
		Page<Brand> page = brandService.lítByPage(pageNum, sortField, sortDir, keyword);
		List<Brand> list = page.getContent();
		
		long startCount = (pageNum - 1) * BrandServiceImpl.BRAND_PER_PAGE + 1;
		long endCount = startCount + BrandServiceImpl.BRAND_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("brand", list);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
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
	@PostMapping("/save")
	public String saveBrand(Brand brand, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes attributes) throws IOException {
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			brand.setLogo(fileName);
			
			Brand saveBrand = brandService.save(brand);
			String uploadDir = "../brand-logos/" + saveBrand.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			brandService.save(brand);
		}
		
		attributes.addFlashAttribute("message", "The brand has been saved successfully");
		return "redirect:/brands";
	}
	
	@GetMapping("/edit/{id}")
	public String editBrand(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
		try {
			Brand brand = brandService.findById(id);
			List<Category> categoriesList = categoryService.listCategoriesUsedInForm();
			
			model.addAttribute("brand", brand);
			model.addAttribute("category", categoriesList);
			model.addAttribute("pageTitle", "Edit Brand");
			
			return "brands/brands_form";
		} catch (BrandNotFoundException e) {
			attributes.addAttribute("message", e.getMessage());
			return "redirect:/brands";
		}
	}
	@GetMapping("/delete/{id}")
	public String deleteBrand(@PathVariable("id") Integer id, RedirectAttributes attributes, Model model) {
		try {
			brandService.delete(id);
			String brandDir = "../brand-logos/" + id;
			FileUploadUtil.deleteDir(brandDir);
			attributes.addFlashAttribute("message", "Brand has been xoa");
		} catch (BrandNotFoundException e) {
			attributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/brands";
	}
}	
