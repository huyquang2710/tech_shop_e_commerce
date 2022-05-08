package com.techshop.admin.user.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techshop.admin.user.services.BrandService;
import com.techshop.admin.user.services.ProductService;
import com.techshop.common.entity.Brand;
import com.techshop.common.entity.Product;

@Controller
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	@GetMapping
	public String products(Model model) {
		List<Product> products = productService.findAll();
		
		model.addAttribute("product", products);
		return "products/products";
	}
	
	@GetMapping("/new")
	public String productForm(Model model) {
		List<Brand> brandList = brandService.findAll();
		Product product = new Product();
		product.setEnabled(true);
		product.setInStock(true);
		
		model.addAttribute("product", product);
		model.addAttribute("brandList", brandList);
		model.addAttribute("pageTitle", "New Product");
		
		return "products/product_form";
	}
	@PostMapping("/save")
	public String saveProduct(Product product) {
		System.out.println("Product: " + product.getName());
		System.out.println("Brand: " + product.getBrand().getId());
		System.out.println("Category: " + product.getCategory().getId());
		
		return "redirect:/products";
	}
}
