package com.techshop.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.techshop.admin.user.repositories.CategoryRepository;
import com.techshop.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test 
	public void testCreateCategory() {
		Category category = new Category("Electronics");
		Category savedCategory = categoryRepository.save(category);
		
		assertThat(savedCategory.getId()).isGreaterThan(0);
	}
	@Test
	public void testCreateSubCategory() {
		Category parent = new Category(2);
		Category laptops = new Category("Laptops", parent);
		Category components = new Category("Computer Components", parent);
		
		categoryRepository.saveAll(List.of(laptops, components));
	}
	
	@Test
	public void testPrintHierarchicalCategories() {
		Iterable<Category> categories = categoryRepository.findAll();
		
		for(Category category: categories) {
			if(category.getParent() == null) {
				System.out.println(category.getName());
				
				Set<Category> children = category.getChildren();
				
				for(Category subCategory : children) {
					System.out.println("--" + subCategory.getName());
					printChildren(subCategory, 1);
				}
			}
		}
	}
	private void printChildren(Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren();
		
		for(Category subCategory : children) {
			for(int i = 0; i < newSubLevel; i++) {
				System.out.println("--");
			}
			System.out.println(subCategory.getName());
			
			printChildren(subCategory, newSubLevel);
		}
	}
	
	/*
	 * @Test public void testListRootCategories() { List<Category> rootCategories =
	 * categoryRepository.listRootCategories(); rootCategories.forEach(cat ->
	 * System.out.println("CAT: " + cat.getName())); }
	 */
	
	// find by name
	@Test
	public void testFindByAlias() {
		String alias = "Computers";
		Category category = categoryRepository.findByName(alias);
		
		assertThat(category).isNotNull();
		assertThat(category.getAlias()).isEqualTo(alias);
	}
}
