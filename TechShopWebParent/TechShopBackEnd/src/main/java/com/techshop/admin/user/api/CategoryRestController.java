package com.techshop.admin.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techshop.admin.user.services.CategoryService;

@RestController
public class CategoryRestController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/categories/check_unique")
	public String checkUniqueCategory(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
		
		return categoryService.checkUnique(id, name, alias);
	}
}
