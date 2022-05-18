package com.techshop.admin.user.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.techshop.admin.exception.ProductNotFoundException2;
import com.techshop.admin.user.services.BrandService;
import com.techshop.admin.user.services.ProductService;
import com.techshop.admin.utils.FileUploadUtil;
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
	public String saveProduct(Product product, RedirectAttributes attributes, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		System.out.println("Product: " + product.getName());
		System.out.println("Brand: " + product.getBrand().getId());
		System.out.println("Category: " + product.getCategory().getId());
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setMainImage(fileName);
			
			Product saveProduct = productService.save(product);
			String uploadDir = "../product-images/" + saveProduct.getId();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
 		} else {
 			productService.save(product);
 		}
		
		attributes.addFlashAttribute("message", "The product has been successfully");
		
		return "redirect:/products";
	}
	@GetMapping("/{id}/enabled/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes attributes) {
		productService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Product ID " + id + "has been "  + status;
		attributes.addFlashAttribute("message", message);
		return "redirect:/products";
	}
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
		try {
			productService.delete(id);
			attributes.addFlashAttribute("message", "The product has been xoa");
		} catch (ProductNotFoundException2 e) {
			attributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/products";
	}
}
