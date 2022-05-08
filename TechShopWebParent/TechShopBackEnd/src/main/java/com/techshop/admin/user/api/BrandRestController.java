package com.techshop.admin.user.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techshop.admin.exception.BrandNotFoundException;
import com.techshop.admin.user.services.BrandService;
import com.techshop.common.dto.CategoryDTO;
import com.techshop.common.entity.Brand;
import com.techshop.common.entity.Category;

@RestController
public class BrandRestController {
	@Autowired
	private BrandService brandService;
	
	@PostMapping("/brands/check_unique")
	public String checkUnique( @Param("id") Integer id, @Param("name") String name) {
		return brandService.checkUnique(id, name);
	}
	
	@GetMapping("/brands/{id}/categories")
	public List<CategoryDTO> listCategoryByBrand(@PathVariable("id") Integer brandId) throws BrandNotFoundException {
		List<CategoryDTO> categoryList = new ArrayList<CategoryDTO>();
		
		try {
			Brand brand = brandService.findById(brandId);
			Set<Category> categories = brand.getCategories();
			
			for(Category category : categories) {
				CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName());
				categoryList.add(categoryDTO);
			}
			return categoryList;
		} catch (BrandNotFoundException e) {
			throw new BrandNotFoundException(e.getMessage());
		}
	}
 }
