package com.techshop.admin.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techshop.admin.user.services.ProductService;

@RestController
public class ProductRestController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/products/check_unique")
	public String checkDuplicateProdcut(@Param("id") Integer id, @Param("name") String name) {
		return productService.checkUnique(id, name);
	}
}
