package com.techshop.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.techshop.admin.user.repositories.ProductRepository;
import com.techshop.common.entity.Brand;
import com.techshop.common.entity.Category;
import com.techshop.common.entity.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		Brand brand = entityManager.find(Brand.class, 6);
		Category category = entityManager.find(Category.class, 3);
		
		Product product = new Product();
		product.setName("Sam Sung Galaxy A4");
		product.setAlias("sangsung_galaxy_a4");
		product.setShortDescription("This is a smart phone");
		product.setFullDescription("full description");
		
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setPrice(445);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		product.setEnabled(true);
		
		Product saveProduct = productRepository.save(product);
		
		assertThat(saveProduct).isNotNull();
		assertThat(saveProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testSaveProductWithImages() {
		Integer productInteger = 1;
		Product product = productRepository.findById(productInteger).get();
		
		product.setMainImage("main image.jpg");
		product.addExtraImage("extra image 1.png");
		product.addExtraImage("extra_image 2.png");
		product.addExtraImage("extra-image 3.png");
		
		Product saveProduct = productRepository.save(product);
		
		assertThat(saveProduct.getImages().size()).isEqualTo(3);
		
	}
	
	@Test
	public void testSaveProductWithDetail() {
		Integer productId = 1;
		Product product = productRepository.findById(productId).get();
		
		product.addDetail("Device Memory", "128GB");
		product.addDetail("Device Memory1", "128GB1");
		product.addDetail("Device Memory2", "128GB2");
		
		Product saveProduct = productRepository.save(product);
		assertThat(saveProduct.getDetails()).isNotEmpty();
	}
}
