package com.techshop.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techshop.admin.user.services.BrandService;
import com.techshop.common.entity.Brand;

@Controller
@RequestMapping("/brands")
public class BrandController {
	@Autowired
	private BrandService brandService;
	
	@GetMapping
	public String fillAll(Model model) {
		List<Brand> brandList = brandService.findAll();
		
		model.addAttribute("brand", brandList);
		return "brands/brands";
	}
	
}
