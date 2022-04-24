package com.techshop.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.techshop.admin.user.repositories.BrandRepository;
import com.techshop.common.entity.Brand;
import com.techshop.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {
	@Autowired
	private BrandRepository brandRepository;
	
	@Test
	public void testCreateBrand() {
		Category laptops = new Category(6);
		Brand acer = new Brand("Acer");
		acer.getCategories().add(laptops);
		
		Brand saveBrand = brandRepository.save(acer);
		
		assertThat(saveBrand).isNotNull();
		assertThat(saveBrand.getId()).isGreaterThan(0);
	}
	@Test
	public void testMultiCateCreateBrand() {
		Category telephones = new Category(4);
		Category tablet = new Category(7);
		
		Brand apple = new Brand("Apple");
		apple.getCategories().add(telephones);
		apple.getCategories().add(tablet);
		
		Brand saveBrand = brandRepository.save(apple);
		
		assertThat(saveBrand).isNotNull();
		assertThat(saveBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testMultiCateCreateBrand1() {
		Brand samsung = new Brand("Sam Sung");
		
		samsung.getCategories().add(new Category(29));
		samsung.getCategories().add(new Category(24));
		
		Brand saveBrand = brandRepository.save(samsung);
		
		assertThat(saveBrand).isNotNull();
		assertThat(saveBrand.getId()).isGreaterThan(0);
	}
	@Test
	public void testFindAll() {
		Iterable<Brand> findAll = brandRepository.findAll();
		
		findAll.forEach(System.out::println);
		
		assertThat(findAll).isNotNull();
	}
	@Test
	public void testFindById() {
		Brand brand = brandRepository.findById(1).get();
		
		assertThat(brand.getName()).isEqualTo("Acer");
	}
	@Test
	public void updateBrand() {
		String newBrand = "Sam Sung Electronics";
		
		Brand find = brandRepository.findById(3).get();
		find.setName(newBrand);
		
		Brand save = brandRepository.save(find);
		
		assertThat(save.getName()).isEqualTo(newBrand);
	}

}
